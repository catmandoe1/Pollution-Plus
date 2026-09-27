package s11.mod.objects.tileEntities;

import com.endertech.minecraft.forge.api.IPollutant;
import com.endertech.minecraft.mods.adpother.blocks.Pollutant;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import s11.mod.Main;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.BlockPollutionDeleter;
import s11.mod.objects.blocks.pump.BlockPollutionPump;

public class TilePollutionDeleter extends TileEntity implements ITickable {
	private long lastWork;
	private final EnergyStorage energy = new EnergyStorage(PollutionPlusConfig.Machines.pollutionDeleter.maxCapacity);
	
	public BlockPos deletionAreaAA;
	public BlockPos deletionAreaBB;
	
	public BlockPos currentDeletionPos;
	private BlockPos deletionPoint1; // bottom left
	private BlockPos deletionPoint2; // top right
	private boolean deletionAreaAALoaded = false;
	private boolean deletionAreaBBLoaded = false;
	
	public TilePollutionDeleter() {
		// this.pos is the world origin here!!!!!
		this.deletionAreaAA = this.pos;
		this.deletionAreaBB = this.pos;
		this.currentDeletionPos = this.pos;
		
		
		this.resetCurrentDeletionPosition();
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		
		// if there no data was loaded from nbt for the deletion area points, set them to the deleters pos
		if (!this.deletionAreaAALoaded) {
			this.deletionAreaAA = this.getPos();
		}
		
		if (!this.deletionAreaBBLoaded) {
			this.deletionAreaBB = this.getPos();
		}
		
		if (!(this.deletionAreaAALoaded || this.deletionAreaBBLoaded)) {
			this.resetCurrentDeletionPosition();
		}
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		
		
		boolean powered = this.canWork();
		
		if (powered) {
			this.useRf();
			this.markDirty();
			
			if (this.hasCooledOff() && this.isDeletionAreaValid()) {
				this.lastWork = world.getTotalWorldTime();
				
				this.deletePollution();
				this.advanceCurrentDeletionPosition();
			}
		}
		
		if (world.getBlockState(this.pos).getValue(BlockPollutionDeleter.POWERED) != powered) {
			this.updateState(powered);
		}
	}
	
	private void deletePollution() {
		Block block = world.getBlockState(this.currentDeletionPos).getBlock();

		// delete if block at position is pollution (carbon, dust & sulphur)
		if (block instanceof Pollutant && ((IPollutant) block).getPollutantType() == IPollutant.Type.AIR) {
			world.setBlockToAir(this.currentDeletionPos);
		}
	}
	
	private void advanceCurrentDeletionPosition() {
		int x = this.currentDeletionPos.getX();
		int y = this.currentDeletionPos.getY();
		int z = this.currentDeletionPos.getZ();
		x++;

		// next row
		if (x > deletionPoint2.getX()) {
			z++;
			x = this.deletionPoint1.getX();
		}

		// next layer
		if (z > deletionPoint2.getZ()){
			y++;
			z = this.deletionPoint1.getZ();
		}

		// loop back to start
		if (y > deletionPoint2.getY()) {
			this.currentDeletionPos = this.deletionPoint1;
			return;
		}

		this.currentDeletionPos = new BlockPos(x, y, z);
	}
	
	private void resetCurrentDeletionPosition() {
		if (this.deletionAreaAA == null || this.deletionAreaBB == null) {
			return;
		}

		//pX1, int pY1, int pZ1, int pX2, int pY2, int pZ2
		int x1 = Math.min(deletionAreaAA.getX(), deletionAreaBB.getX());
		int y1 = Math.min(deletionAreaAA.getY(), deletionAreaBB.getY());
		int z1 = Math.min(deletionAreaAA.getZ(), deletionAreaBB.getZ());
		this.deletionPoint1 = new BlockPos(x1, y1, z1);

		int x2 = Math.max(deletionAreaAA.getX(), deletionAreaBB.getX());
		int y2 = Math.max(deletionAreaAA.getY(), deletionAreaBB.getY());
		int z2 = Math.max(deletionAreaAA.getZ(), deletionAreaBB.getZ());
		this.deletionPoint2 = new BlockPos(x2, y2, z2);

		this.currentDeletionPos = this.deletionPoint1;
	}
	
	public static boolean isDeletionAreaValid(BlockPos deletionAreaAA, BlockPos deletionAreaBB) {
		int maxRange = PollutionPlusConfig.Machines.pollutionDeleter.horizontalLimit;
		return Math.abs(deletionAreaBB.getX() - deletionAreaAA.getX()) <= maxRange &&
				Math.abs(deletionAreaBB.getZ() - deletionAreaAA.getZ()) <= maxRange;
	}

	public boolean isDeletionAreaValid() {
		return isDeletionAreaValid(this.deletionAreaAA, deletionAreaBB);
	}
	
	private BlockPos clampPosY(BlockPos pos) {
		if (world == null) {
			return pos;
		}

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (y > world.getHeight()) {
			y = world.getHeight();
		} else if (y < 0) {
			y = 0;
		}

		return new BlockPos(x, y, z);
	}
	
	public void updateDeletionArea(int id, int amount) {
		BlockPos prevAA = this.deletionAreaAA;
		BlockPos prevBB = this.deletionAreaBB;
		switch (id) {
			case 0: // x1 ++
				this.deletionAreaAA = this.deletionAreaAA.east(amount);
				break;

			case 1: // y1 ++
				this.deletionAreaAA = this.deletionAreaAA.up(amount);
				break;

			case 2: // z1 ++
				this.deletionAreaAA = this.deletionAreaAA.south(amount);
				break;

			case 3: // x1 --
				this.deletionAreaAA = this.deletionAreaAA.west(amount);
				break;

			case 4: // y1 --
				this.deletionAreaAA = this.deletionAreaAA.down(amount);
				break;

			case 5: // z1 --
				this.deletionAreaAA = this.deletionAreaAA.north(amount);
				break;

			case 6: // x2 ++
				this.deletionAreaBB = this.deletionAreaBB.east(amount);
				break;

			case 7: // y2 ++
				this.deletionAreaBB = this.deletionAreaBB.up(amount);
				break;

			case 8: // z2 ++
				this.deletionAreaBB = this.deletionAreaBB.south(amount);
				break;

			case 9: // x2 --
				this.deletionAreaBB = this.deletionAreaBB.west(amount);
				break;

			case 10: // y2 --
				this.deletionAreaBB = this.deletionAreaBB.down(amount);
				break;

			case 11: // z2 --
				this.deletionAreaBB = this.deletionAreaBB.north(amount);
				break;


			default:
				Main.logger.error("Unrecognised deletion area update action id: " + id);

		}

		// clamp y to build limits
		this.deletionAreaAA = this.clampPosY(this.deletionAreaAA);
		this.deletionAreaBB = this.clampPosY(this.deletionAreaBB);

		// undo change if invalid
		if (!this.isDeletionAreaValid()) {
			this.deletionAreaAA = prevAA;
			this.deletionAreaBB = prevBB;
		}

		markDirty();
		this.resetCurrentDeletionPosition();
	}
	
	public void updateDeletionArea(int id) {
		updateDeletionArea(id, 1);
	}

	public void setDeletionAreaAA(BlockPos newPosition) {
		this.deletionAreaAA = newPosition;
		this.markDirty();
		this.resetCurrentDeletionPosition();
		
		// sync change with clients
		if (!world.isRemote) {
			world.notifyBlockUpdate(this.pos, world.getBlockState(this.pos), world.getBlockState(this.pos), 3);
		}
	}

	public void setDeletionAreaBB(BlockPos newPosition) {
		this.deletionAreaBB = newPosition;
		this.markDirty();
		this.resetCurrentDeletionPosition();
		
		// sync change with clients
		if (!world.isRemote) {
			world.notifyBlockUpdate(this.pos, world.getBlockState(this.pos), world.getBlockState(this.pos), 3);
		}
	}
	
	private boolean canWork() {
		return this.energy.getEnergyStored() >= PollutionPlusConfig.Machines.pollutionDeleter.operationCost;
	}
	
	private boolean hasCooledOff() {
		return world.getTotalWorldTime() - this.lastWork >= PollutionPlusConfig.Machines.pollutionDeleter.workSpeed;
	}
	
	private void useRf() {
		this.energy.extractEnergy(PollutionPlusConfig.Machines.pollutionDeleter.operationCost, false);
	}
	
	protected void updateState(boolean isPowered) {
		if (world.getBlockState(pos) != null) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockPollutionDeleter.POWERED, isPowered));
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.energy.receiveEnergy(compound.getInteger("energy"), false);
		
		if (compound.hasKey("AA")) {
			this.deletionAreaAA = BlockPos.fromLong(compound.getLong("AA"));
			this.deletionAreaAALoaded = true; 
		}
		
		if (compound.hasKey("BB")) {
			this.deletionAreaBB = BlockPos.fromLong(compound.getLong("BB"));
			this.deletionAreaBBLoaded = true;
		}
		
		this.resetCurrentDeletionPosition();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("energy", this.energy.getEnergyStored());
		compound.setLong("AA", this.deletionAreaAA.toLong());
		compound.setLong("BB", this.deletionAreaBB.toLong());
		return compound;
	}
	

	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = new NBTTagCompound();
		return writeToNBT(tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readFromNBT(packet.getNbtCompound());
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) energy;
		}		
		return super.getCapability(capability, facing);
	}
}
