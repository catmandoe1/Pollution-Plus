package com.catmandoe1.pollutionplus.tileentities;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.block.BlockPollutionDeleter;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.gui.container.ContainerPollutionDeleter;
import com.endertech.minecraft.forge.blocks.IPollutant;
import com.endertech.minecraft.mods.adpother.blocks.Pollutant;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TileEntityPollutionDeleter extends BlockEntity implements MenuProvider {
	private long lastWork;
	private final EnergyStorage energy = new EnergyStorage(Config.pollutionDeleterPowerCapacity);
	private final LazyOptional<IEnergyStorage> energyProxy = LazyOptional.of(() -> energy);

	public BlockPos deletionAreaAA;
	public BlockPos deletionAreaBB;

	public BlockPos currentDeletionPos;
	private BlockPos deletionPoint1; // bottom left
	private BlockPos deletionPoint2; // top right


	public TileEntityPollutionDeleter(BlockPos pPos, BlockState pBlockState) {
		super(PPBlocks.POLLUTION_DELETER.getTileEntityType(), pPos, pBlockState);

		this.deletionAreaAA = pPos;
		this.deletionAreaBB = pPos;
		this.currentDeletionPos = pPos;

		this.resetCurrentDeletionPosition();
	}

	public static <T extends BlockEntity> void update(Level level, BlockPos blockPos, BlockState blockState, T t) {
		if (!(t instanceof TileEntityPollutionDeleter)) {
			return;
		}

		TileEntityPollutionDeleter tileEntity = (TileEntityPollutionDeleter) t;

		boolean powered = tileEntity.canWork();

		if (powered) {
			tileEntity.useRf();
			tileEntity.setChanged();

			if (tileEntity.hasCooledOff(level) && tileEntity.isDeletionAreaValid()) {
				tileEntity.lastWork = level.getGameTime();

				tileEntity.deletePollution(level);
				tileEntity.advanceCurrentDeletionPosition();
			}
		}

		if (blockState.getValue(BlockPollutionDeleter.POWERED) != powered) {
			tileEntity.updateState(blockState.setValue(BlockPollutionDeleter.POWERED, powered));
		}
	}

	private void deletePollution(Level world) {
		Block block = world.getBlockState(this.currentDeletionPos).getBlock();
		//Main.LOGGER.debug("deleting: " + this.currentDeletionPos + " " + block);

		// delete if block at position is pollution (carbon, dust & sulphur)
		if (block instanceof Pollutant && ((IPollutant) block).getPollutantType() == IPollutant.Type.AIR) {
			world.removeBlock(this.currentDeletionPos, false);
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
		int maxRange = Config.pollutionDeleterHorizontalLimit;
		return Math.abs(deletionAreaBB.getX() - deletionAreaAA.getX()) <= maxRange &&
				Math.abs(deletionAreaBB.getZ() - deletionAreaAA.getZ()) <= maxRange;
	}

	public boolean isDeletionAreaValid() {
		return TileEntityPollutionDeleter.isDeletionAreaValid(this.deletionAreaAA, deletionAreaBB);
	}

	public void hithere() {
		this.deletionAreaAA = this.deletionAreaAA.north();
		Main.LOGGER.info(this.deletionAreaAA.toString());
		setChanged();
	}

	private BlockPos clampPosY(BlockPos pos) {
		if (level == null) {
			return pos;
		}

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (y > level.getMaxBuildHeight()) {
			y = level.getMaxBuildHeight();
		} else if (y < level.getMinBuildHeight()) {
			y = level.getMinBuildHeight();
		}

		return new BlockPos(x, y, z);
	}

	/**
	 	AA++		BB++
		x1 = 0		x2 = 6
		y1 = 1 		y2 = 7
	 	z1 = 2		z2 = 8

	 	AA--		BB--
	 	x1 = 3		x2 = 9
	 	y1 = 4		y2 = 10
	 	z1 = 5		z2 = 11
	 */
	public void updateDeletionArea(int id, int amount) {
		BlockPos prevAA = this.deletionAreaAA;
		BlockPos prevBB = this.deletionAreaBB;
		switch (id) {
			case 0: // x1 ++
				this.deletionAreaAA = this.deletionAreaAA.east(amount);
				break;

			case 1: // y1 ++
				this.deletionAreaAA = this.deletionAreaAA.above(amount);
				break;

			case 2: // z1 ++
				this.deletionAreaAA = this.deletionAreaAA.south(amount);
				break;

			case 3: // x1 --
				this.deletionAreaAA = this.deletionAreaAA.west(amount);
				break;

			case 4: // y1 --
				this.deletionAreaAA = this.deletionAreaAA.below(amount);
				break;

			case 5: // z1 --
				this.deletionAreaAA = this.deletionAreaAA.north(amount);
				break;

			case 6: // x2 ++
				this.deletionAreaBB = this.deletionAreaBB.east(amount);
				break;

			case 7: // y2 ++
				this.deletionAreaBB = this.deletionAreaBB.above(amount);
				break;

			case 8: // z2 ++
				this.deletionAreaBB = this.deletionAreaBB.south(amount);
				break;

			case 9: // x2 --
				this.deletionAreaBB = this.deletionAreaBB.west(amount);
				break;

			case 10: // y2 --
				this.deletionAreaBB = this.deletionAreaBB.below(amount);
				break;

			case 11: // z2 --
				this.deletionAreaBB = this.deletionAreaBB.north(amount);
				break;


			default:
				Main.LOGGER.error("Unrecognised deletion area update action id: " + id);

		}

		// clamp y to build limits
		this.deletionAreaAA = this.clampPosY(this.deletionAreaAA);
		this.deletionAreaBB = this.clampPosY(this.deletionAreaBB);

		// undo change if invalid
		if (!this.isDeletionAreaValid()) {
			this.deletionAreaAA = prevAA;
			this.deletionAreaBB = prevBB;
		}

		setChanged();
		this.resetCurrentDeletionPosition();
	}

	public void updateDeletionArea(int id) {
		updateDeletionArea(id, 1);
	}

	public void setDeletionAreaAA(BlockPos newPosition) {
		this.deletionAreaAA = newPosition;
		this.setChanged();
		this.resetCurrentDeletionPosition();
	}

	public void setDeletionAreaBB(BlockPos newPosition) {
		this.deletionAreaBB = newPosition;
		this.setChanged();
		this.resetCurrentDeletionPosition();
	}

	boolean canWork() {
		return this.energy.getEnergyStored() >= Config.pollutionDeleterPowerUse;
	}

	boolean hasCooledOff(Level level) {
		return level.getGameTime() - this.lastWork >= Config.pollutionDeleterWorkSpeed;
	}

	private void useRf() {
		this.energy.extractEnergy(Config.pollutionDeleterPowerUse, false);
	}

	private void updateState(BlockState blockState) {
		if (level != null) {
			level.setBlock(worldPosition, blockState, 3); // 2 is the magic number (send block update to clients (?))
		}
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);

		// save energy
		pTag.putInt("energy", this.energy.getEnergyStored());
		pTag.putLong("AA",this.deletionAreaAA.asLong());
		pTag.putLong("BB",this.deletionAreaBB.asLong());
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);

		// load energy
		if (pTag.contains("energy")) {
			energy.receiveEnergy(pTag.getInt("energy"), false);
		}

		if (pTag.contains("AA")) {
			this.deletionAreaAA = BlockPos.of(pTag.getLong("AA"));
		}

		if (pTag.contains("BB")) {
			this.deletionAreaBB = BlockPos.of(pTag.getLong("BB"));
		}

		this.resetCurrentDeletionPosition();
	}

	// syncs data on chunk load (?)
	@Override
	public @NotNull CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		saveAdditional(tag);
		return tag;
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		if (level != null && !level.isClientSide) {
			level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), 2); // The flag 2 is equivalent to Block#UPDATE_CLIENTS
		}
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ENERGY) {
			return this.energyProxy.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable("block.pollutionplus.pollution_deleter");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
		// store block position in buffer
		return new ContainerPollutionDeleter(i, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(worldPosition));
	}
}
