package com.catmandoe1.pollutionplus.tileentities.poweredFilters;

import com.catmandoe1.pollutionplus.block.poweredFilters.BlockPoweredFilter;
import com.catmandoe1.pollutionplus.tileentities.IFilter;
import com.endertech.minecraft.mods.adpother.entities.GasEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class TileEntityPoweredFilter extends BlockEntity implements IFilter {
	private final EnergyStorage energy = new EnergyStorage(this.getMaxEnergyCap());
	private final LazyOptional<IEnergyStorage> energyProxy = LazyOptional.of(() -> energy);

	private long lastWork;

	public TileEntityPoweredFilter(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	abstract int getMaxEnergyCap();
	abstract int getFilterSpeed();
	abstract int getFilterPowerUse();

	public static <T extends BlockEntity> void update(Level level, BlockPos blockPos, BlockState blockState, T t) {
		if (!(t instanceof TileEntityPoweredFilter)) {
			return;
		}

		TileEntityPoweredFilter tileEntity = (TileEntityPoweredFilter)t;

		boolean powered = tileEntity.canWork();

		if (powered) {
			tileEntity.useRf();
			tileEntity.setChanged();

			if (tileEntity.hasCooledOff(level)) {
				if (tileEntity.filterPollution(level, blockPos)) {
					tileEntity.lastWork = level.getGameTime();
				}
			}
		}


		// sync blockstate
		if (blockState.getValue(BlockPoweredFilter.POWERED) != powered) {
			tileEntity.updateState(blockState.setValue(BlockPoweredFilter.POWERED, powered));
		}
	}

	public boolean canWork() {
		return this.energy.getEnergyStored() >= this.getFilterPowerUse();
	}

	private void useRf() {
		this.energy.extractEnergy(this.getFilterPowerUse(), false); // use rf per tick
	}

	public boolean hasCooledOff(Level level) {
		return level.getGameTime() - this.lastWork >= this.getFilterSpeed();
	}

	public long getCooldownRemaining(Level world) {
		return Math.max(this.getFilterSpeed() - (world.getGameTime() - this.lastWork), 0);
	}

	// true if worked
	public boolean fakeUse(Level level) {
		if (this.hasCooledOff(level)) {
			this.lastWork = level.getGameTime();
			return true;
		}
		return false;
	}

	private void updateState(BlockState blockState) {
		if (level != null) {
			level.setBlock(worldPosition, blockState, 2); // 2 is the magic number (send block update to clients (?))
		}
	}

	private boolean filterPollution(Level level, BlockPos blockPos) {
		List<GasEntity> pollution =  level.getEntitiesOfClass(GasEntity.class, AABB.ofSize(blockPos.getCenter(), 1, 1, 1));
		boolean hasDeleted = false;

		for (GasEntity poll : pollution) {
			hasDeleted = true;
			poll.discard();
		}

		return hasDeleted;
	}


	//saving yap ==================
	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);

		// save energy
		pTag.putInt("energy", this.energy.getEnergyStored());
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);

		// load energy
		if (pTag.contains("energy")) {
			energy.receiveEnergy(pTag.getInt("energy"), false);
		}
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
}
