package com.catmandoe1.pollutionplus.tileentities;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.PPSounds;
import com.catmandoe1.pollutionplus.block.BlockIncinerator;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.endertech.minecraft.forge.blocks.IPollutant;
import com.endertech.minecraft.mods.adpother.blocks.Pollutant;
import com.endertech.minecraft.mods.adpother.entities.GasEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TileEntityIncinerator extends BlockEntity {
	private boolean hasRedstone = false;
	private long lastWork;
	private List<BlockPos> incinerationArea;
	private final EnergyStorage energy = new EnergyStorage(Config.incineratorMaxCapacity);

	private final LazyOptional<IEnergyStorage> energyProxy = LazyOptional.of(() -> energy);

	private int machineRange = Config.incineratorWorkRange;
	public TileEntityIncinerator(BlockPos pPos, BlockState pBlockState) {
		super(PPBlocks.INCINERATOR.getTileEntityType(), pPos, pBlockState);
	}

	public static <T extends BlockEntity> void update(Level level, BlockPos blockPos, BlockState blockState, T t) {
		if (!(t instanceof TileEntityIncinerator)) {
			return;
		}
		TileEntityIncinerator tileEntity = (TileEntityIncinerator)t;

		if (level.hasNeighborSignal(blockPos)) {
			if (!tileEntity.hasRedstone) {
				tileEntity.hasRedstone = true;
			}
		} else {
			if (tileEntity.hasRedstone) {
				tileEntity.hasRedstone = false;
			}
		}

		//tileEntity.energy.receiveEnergy(9000, false);
		boolean powered = tileEntity.canWork();

		if (powered && tileEntity.hasCooledOff(level)) {
			tileEntity.lastWork = level.getGameTime();

			if (tileEntity.deleteAllPollutionFromPositions(level, tileEntity.getPositionsAroundTile()) || tileEntity.deleteAllMovingPollutionInRange(level)) {
				tileEntity.setChanged();
				tileEntity.energy.extractEnergy(Config.incineratorPowerUse, false);

				tileEntity.playWorkSound(level);

				//Main.LOGGER.error("yum");
			}

//			blockState.setValue(BlockIncinerator.POWERED, tileEntity.canWork());
//			tileEntity.updateState(blockState);
//
//			System.out.println("powered baby is nopw : " + tileEntity.canWork());
		}
//		} else if (!tileEntity.canWork() && blockState.getValue(BlockIncinerator.POWERED)) {
//			// reset property to false
//			blockState.setValue(BlockIncinerator.POWERED, false);
//			tileEntity.updateState(blockState);
//		}

		if (blockState.getValue(BlockIncinerator.POWERED) != powered) {
			tileEntity.updateState(blockState.setValue(BlockIncinerator.POWERED, powered));
		}

	}

	// if incinerator can incinerate
	private boolean canWork() {
		return !this.hasRedstone && this.energy.getEnergyStored() >= Config.incineratorPowerUse;
	}

	private boolean hasCooledOff(Level level) {
		return level.getGameTime() - this.lastWork >= Config.incineratorWorkSpeed;
	}

	public long getCooldownRemaining(Level world) {
		return Math.max(Config.incineratorWorkSpeed - (world.getGameTime() - this.lastWork), 0);
	}

	private void playWorkSound(Level level) {
		level.playSound(null, worldPosition, PPSounds.BLOCK_INCINERATOR_WORK.get(), SoundSource.BLOCKS, 1f, 1f);
	}

	private void updateState(BlockState blockState) {
		if (level != null) {
			level.setBlock(worldPosition, blockState, 2); // 2 is the magic number (send block update to clients (?))
		}
	}

	private List<BlockPos> getPositionsAroundTile() {
		if (this.incinerationArea != null && !this.incinerationArea.isEmpty()) {
			return this.incinerationArea;
		}

		this.incinerationArea = new ArrayList<BlockPos>();

		// create incineration positions if theyre not already made
		for (int x = this.worldPosition.getX() - machineRange; x <= this.worldPosition.getX() + machineRange; x++) {
			for (int y = this.worldPosition.getY() - machineRange; y <= this.worldPosition.getY() + machineRange; y++) {
				for (int z = this.worldPosition.getZ() - machineRange; z <= this.worldPosition.getZ() + machineRange; z++) {
					this.incinerationArea.add(new BlockPos(x, y, z));
				}
			}
		}

		return this.incinerationArea;
	}

	// deletes all solid air type pollution from the given position and returns if any was deleted
	private boolean deleteAllPollutionFromPositions(Level level, List<BlockPos> positions) {
		boolean hasDeleted = false;

		for (BlockPos position : positions) {
			BlockState positionBlockState = level.getBlockState(position);
			Block positionBlock = positionBlockState.getBlock();

			// check if block at position is pollution (carbon, dust & sulphur)
			if (positionBlock instanceof Pollutant && ((IPollutant) positionBlock).getPollutantType() == IPollutant.Type.AIR) {
				level.removeBlock(position, false);
				hasDeleted = true;
			}
		}
		return hasDeleted;
	}

	// deletes all pollution entities in range and return if any was deleted
	private boolean deleteAllMovingPollutionInRange(Level world) {
		// skip if disabled in config
		if (!Config.incineratorDeleteMovingPollution) {
			return false;
		}

		List<GasEntity> pollution = world.getEntitiesOfClass(GasEntity.class, AABB.ofSize(worldPosition.getCenter(), machineRange * 2, machineRange * 2, machineRange * 2));

		if (pollution.size() < 1) {
			return false;
		}

		for (GasEntity poll : pollution) {
			poll.discard();
		}

		return true;
	}

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
