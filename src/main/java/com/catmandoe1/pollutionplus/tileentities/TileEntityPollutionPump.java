package com.catmandoe1.pollutionplus.tileentities;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.block.poweredFilters.BlockPoweredFilter;
import com.catmandoe1.pollutionplus.block.pump.BlockPollutionPipe;
import com.catmandoe1.pollutionplus.block.pump.BlockPollutionPump;
import com.catmandoe1.pollutionplus.tileentities.poweredFilters.TileEntityPoweredFilter;
import com.endertech.minecraft.forge.blocks.IPollutant;
import com.endertech.minecraft.mods.adpother.blocks.FilterFrame;
import com.endertech.minecraft.mods.adpother.blocks.Pollutant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.catmandoe1.pollutionplus.block.pump.BlockPollutionPipe.findTop;

public class TileEntityPollutionPump extends BlockEntity {
	private long lastWork;
	private BlockPos topPipe = null;
	private boolean checkTopPipe = true;
	private final EnergyStorage energy = new EnergyStorage(Config.pollutionPumpPowerCapacity);

	private final LazyOptional<IEnergyStorage> energyProxy = LazyOptional.of(() -> energy);
	private static final List<Direction> SIDES = Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

	public TileEntityPollutionPump(BlockPos pPos, BlockState pBlockState) {
		super(PPBlocks.POLLUTION_PUMP.getTileEntityType(), pPos, pBlockState);
	}

	public static <T extends BlockEntity> void update(Level level, BlockPos blockPos, BlockState blockState, T t) {
		if (!(t instanceof TileEntityPollutionPump)) {
			return;
		}

		TileEntityPollutionPump tileEntity = (TileEntityPollutionPump) t;

		boolean powered = tileEntity.canWork();

		// get top pipe
		if (tileEntity.checkTopPipe) {
			tileEntity.topPipe = BlockPollutionPipe.findTop(level, blockPos);
			tileEntity.checkTopPipe = false;
			//Main.LOGGER.info("NEW TOP AT " + tileEntity.topPipe + "!!!!!");
		}

		if (powered) {
			tileEntity.useRf();
			tileEntity.setChanged();

			if (tileEntity.hasCooledOff(level)) {
				tileEntity.lastWork = level.getGameTime();

				// find pollution
				outer: // cool tech from https://stackoverflow.com/questions/886955/
				for (int x = tileEntity.topPipe.getX() - Config.pollutionPumpRange; x < tileEntity.topPipe.getX() + Config.pollutionPumpRange + 1; x++) {
					for (int y = tileEntity.topPipe.getY() - Config.pollutionPumpRange; y < tileEntity.topPipe.getY() + Config.pollutionPumpRange + 1; y++) {
						for (int z = tileEntity.topPipe.getZ() - Config.pollutionPumpRange; z < tileEntity.topPipe.getZ() + Config.pollutionPumpRange + 1; z++) {
							BlockState pollutionBlockState = level.getBlockState(new BlockPos(x, y, z));
							Block block = pollutionBlockState.getBlock();

							// if block was pollution (carbon & sulfur)
							if (block instanceof Pollutant && ((IPollutant) block).getPollutantType() == IPollutant.Type.AIR) {
								Pollutant<?> pollutant = (Pollutant<?>) block;
								int pollutionStrength = pollutant.getCarriedPollutionAmount(pollutionBlockState);

								// try and move the pollution
								if (tileEntity.pumpPollution(level, pollutionBlockState, pollutant, pollutionStrength)) {
									//Main.LOGGER.info("PUMPED!");
									level.removeBlock(new BlockPos(x, y, z), false);
									break outer;
								}
							}
						}
					}
				}
			}
		}



		if (blockState.getValue(BlockPollutionPump.POWERED) != powered) {
			tileEntity.updateState(blockState.setValue(BlockPollutionPump.POWERED, powered));
		}
	}

	private boolean pumpPollution(Level world, BlockState pollutionState, Pollutant<?> pollutionBlock, int strength) {
		Collections.shuffle(SIDES);

		// try pump pollution into an adjacent filter
		for (Direction side : SIDES) {
			BlockPos adjacent = worldPosition.relative(side);

			Block sideBlock = world.getBlockState(adjacent).getBlock();

			// try pump into enders filter
			if (sideBlock instanceof FilterFrame filterFrame) {
				// some stupid complex way of getting the tile entity
				if (filterFrame.getTile(level, adjacent).isPresent()) {
					FilterFrame.BlockTile filter = filterFrame.getTile(level, adjacent).get();

					int freeSpace = filterFrame.getContent(filter).getFreeSpaceFor(pollutionBlock);
					if (freeSpace >= strength) {
						filterFrame.fill(filter, pollutionBlock, strength);
						return true;
					}
				}
			// try pump into my filters
			} else if (sideBlock instanceof BlockPoweredFilter blockPoweredFilter) {
				IFilter filter = (IFilter) world.getBlockEntity(adjacent);
				if (filter != null && filter.hasCooledOff(world) && filter.canWork()) {
					filter.fakeUse(world);
					return true;
				}
			}
		}


		// no filters so try place next to
		for (Direction side : SIDES) {
			BlockPos adjacent = worldPosition.relative(side);
			if (world.isEmptyBlock(adjacent)) {
				world.setBlock(adjacent, pollutionState, 3);
				return true;
			}
		}

		return false;
	}

	private boolean canWork() {
		return this.energy.getEnergyStored() >= Config.pollutionPumpPowerUse && this.topPipe != null;
	}

	private boolean hasCooledOff(Level level) {
		return level.getGameTime() - this.lastWork >= Config.pollutionPumpWorkSpeed;
	}

	private void useRf() {
		this.energy.extractEnergy(Config.incineratorPowerUse, false);
	}

	private void updateState(BlockState blockState) {
		if (level != null) {
			level.setBlock(worldPosition, blockState, 2); // 2 is the magic number (send block update to clients (?))
		}
	}

	public void onPipeChanged() {
		this.topPipe = null;
		this.checkTopPipe = true;
	}

	public boolean hasPipe() {
		return this.topPipe != null;
	}

	public BlockPos getTopPipe() {
		return this.topPipe;
	}

	//loading bittts
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
