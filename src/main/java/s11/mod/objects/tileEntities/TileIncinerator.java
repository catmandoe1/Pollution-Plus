package s11.mod.objects.tileEntities;

import java.util.ArrayList;
import java.util.List;

import com.endertech.minecraft.forge.api.IPollutant;
import com.endertech.minecraft.mods.adpother.blocks.Pollutant;
import com.endertech.minecraft.mods.adpother.entities.EntityPollutant;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.BlockIncinerator;
import s11.mod.sounds.PollutionSounds;

public class TileIncinerator extends TileEntity implements ITickable {
	private final int maxCapacity = PollutionPlusConfig.Machines.incinerator.maxCapacity;
	private final int maxTransfer = Integer.MAX_VALUE;
	private final int maxExtract = Integer.MAX_VALUE;
	
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, maxTransfer, maxExtract);
	private boolean isPowered = false; //is saved to nbt
	//private final int machineUsePower = PollutionPlusConfig.Machines.incinerator.powerUse;
	//private final int machineWorkSpeed = PollutionPlusConfig.Machines.incinerator.workSpeed; //in game ticks (defualt 200)
	private final int machineRange = PollutionPlusConfig.Machines.incinerator.workRange;
//	private final AxisAlignedBB deletionBB = new AxisAlignedBB(pos.getX() - this.machineRange, pos.getY() - this.machineRange, pos.getZ() - this.machineRange,
//			pos.getX() + this.machineRange, pos.getY() + this.machineRange, pos.getZ() + this.machineRange);
	
	//private final AxisAlignedBB deletionBB = new AxisAlignedBB(getPos()).grow(machineRange);
	
	private boolean firstStart = true; //isn't saved
	private int updateCounter = 0;
	private BlockPos tilePos;
	private long lastWork;
	private boolean usePower = false;
	private boolean justUpdated = false;
	private boolean hasRedstone;
	
	public void playWorkSound() {
		if (!PollutionPlusConfig.GeneralConfig.machinesSounds.incineratorSound) {
			return;
		}
		world.playSound(null, pos, PollutionSounds.BLOCK_INCINERATOR_WORK, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		hasRedstone = false;
		
		//checks all sides for redstone power
		for (EnumFacing dir : EnumFacing.VALUES) {
			int redstoneSide = world.getRedstonePower(pos, dir);
			if (redstoneSide > 0) {
				hasRedstone = true;
				break;
			}
		}
		
		//updateState(isPowered, hasRedstone);
		isPowered = energy.getEnergyStored() >= PollutionPlusConfig.Machines.incinerator.powerUse;
		
		//updates tile if just loaded
		if (firstStart == true) {
			updateState(isPowered, hasRedstone);
			firstStart = false;
		}
		
		//incinerator pollution deletion
		if (!hasRedstone && isPowered && world.getTotalWorldTime() - lastWork >= PollutionPlusConfig.Machines.incinerator.workSpeed) { //only runs if redstone is off, has power and workspeed delay is over
			if (!justUpdated) {
				updateState(isPowered, hasRedstone);
				justUpdated = true;
			}
			lastWork = world.getTotalWorldTime();
			List<BlockPos> positions = getPositionsAroundTile();
			this.markDirty();
						
			for (BlockPos position : positions) {
				IBlockState state = world.getBlockState(position);
				Block block = state.getBlock();
				
				if (block instanceof Pollutant && ((IPollutant) block).getPollutantType() == IPollutant.Type.AIR) {
					Pollutant<?> pollutant = (Pollutant<?>) block;
					int pollutantAmount = pollutant.getCarriedPollutionAmount(state);
						world.setBlockToAir(position);
						usePower = true;
				}
			}
			
			usePower = usePower || deleteAllPollutionEntitiesInRange(); // also use power if entites were deleted
			
			if (usePower) {
				energy.extractEnergy(PollutionPlusConfig.Machines.incinerator.powerUse, false);
				usePower = false;
				playWorkSound();
			}
		} else if(justUpdated) { 
			updateState(isPowered, hasRedstone);
			justUpdated = false;
		}
		
		//updates state every 20 ticks ~1 second
		if (updateCounter >= 20) {
			updateState(isPowered, hasRedstone);
			updateCounter = 0;
		} else {
			updateCounter ++;
		}
	}
	
	private boolean deleteAllPollutionEntitiesInRange() {
		if (!PollutionPlusConfig.Machines.incinerator.deleteMovingPollution) {
			return false;
		}
		
		List pollutants = getWorld().getEntitiesWithinAABB(EntityPollutant.class, this.getDeletionBB());

//		Print.print(String.format("%d %d %d %d %d %d", pos.getX() - this.machineRange, pos.getY() - this.machineRange, pos.getZ() - this.machineRange,
//			pos.getX() + this.machineRange, pos.getY() + this.machineRange, pos.getZ() + this.machineRange));
//		
//		Print.print(pollutants.size());
		if (pollutants.size() > 0) {
			
			// kills all the pollution in the filter
			for (int i = 0; i < pollutants.size(); i++) {
				((EntityPollutant) pollutants.get(i)).setDead();
			}
			return true;
		}
		return false;
	}
	
	private AxisAlignedBB getDeletionBB() {
		return new AxisAlignedBB(pos.getX() - this.machineRange, pos.getY() - this.machineRange, pos.getZ() - this.machineRange,
				pos.getX() + this.machineRange + 1, pos.getY() + this.machineRange + 1, pos.getZ() + this.machineRange + 1);
		
		// -x good
		// +z bad - fixed
		// +x bad - fixed
		// -z good
		// minY: 81, maxY: 91
	}
	
	private List<BlockPos> getPositionsAroundTile() {
		List<BlockPos> positions = new ArrayList<>();
		for (int x = pos.getX() - machineRange; x <= pos.getX() + machineRange; x++) {
			for (int y = pos.getY() - machineRange; y <= pos.getY() + machineRange; y++) {
				for (int z = pos.getZ() - machineRange; z <= pos.getZ() + machineRange; z++) {
					positions.add(new BlockPos(x, y, z));
				}
			}
		}
		// Collections.shuffle(positions); why was this here
		return positions;
	}
	
	private void updateState(boolean isPowered, boolean hasRedstone) {
		if (world.getBlockState(pos) != null) {
			if (hasRedstone) {
				this.isPowered = false;
			}
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockIncinerator.POWERED, this.isPowered));
		}
	}
	
	//adds power to the incinerator only if there is space (useless)
	private void addEnergy(int power) {
		if (energy.getEnergyStored() + power <= energy.getMaxEnergyStored()) {
			energy.receiveEnergy(power, false);
		}
	}
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("isActivated", isPowered);
		//compound.setBoolean("hasRedstone", hasRedstone);
		compound.setInteger("storedEnergy", energy.getEnergyStored());
        return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		isPowered = compound.getBoolean("isActivated");
		//hasRedstone = compound.getBoolean("hasRedstone");
		addEnergy(compound.getInteger("storedEnergy"));
		//updateState(isPowered);
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
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}
