package s11.mod.objects.tileEntities;

import java.util.Collections;
import java.util.List;

import com.endertech.minecraft.forge.api.IPollutant;
import com.endertech.minecraft.mods.adpother.blocks.Filter;
import com.endertech.minecraft.mods.adpother.blocks.Pollutant;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.IFilter;
import s11.mod.objects.blocks.poweredfilters.BlockPoweredFilterBase;
import s11.mod.objects.blocks.pump.BlockPollutionPipe;
import s11.mod.objects.blocks.pump.BlockPollutionPump;
import s11.mod.sounds.PollutionSounds;

public class TilePollutionPump extends TileEntity implements ITickable {
	private long lastWork;
	private BlockPos topPipe = null;
	private boolean checkTopPipe = true;
	private final EnergyStorage energy = new EnergyStorage(PollutionPlusConfig.Machines.pollutionPump.maxCapacity);
	private long soundDelay;
	
	
//	@Override
//	public void onLoad() {
//		Main.logger.info("loadddeded!!!!!" + this.canWorkClient());
//		if (world.isRemote) {
//			Main.proxy.handleTileSound(PollutionSounds.BLOCK_POLLUTION_PUMP_WORK, this, this.canWorkClient(), 1f, 1f); // do sound
//		}
//	}
	

	@Override
	public void update() {
		if (world.isRemote) {
			//Main.proxy.handleTileSound(PollutionSounds.BLOCK_POLLUTION_PUMP_WORK, this, this.canWorkClient(), 1f, 1f); // do sound
			return;
		}
		
		
		boolean powered = this.canWork();
		
		// get top pipe
		if (this.checkTopPipe) {
			this.topPipe = BlockPollutionPipe.findTop(world, this.pos);
			this.checkTopPipe = false;
			//Main.logger.info("NEW TOP AT " + this.topPipe + "!!!!!");
		}

		if (powered) {
			this.useRf();
			this.markDirty();
			
			if (PollutionPlusConfig.GeneralConfig.machinesSounds.pollutionPump && world.getTotalWorldTime() - this.soundDelay >= 30) {
				world.playSound(null, pos, PollutionSounds.BLOCK_POLLUTION_PUMP_WORK, SoundCategory.BLOCKS, 0.75f, 1f);
				this.soundDelay = world.getTotalWorldTime();
			}

			if (this.hasCooledOff()) {
				this.lastWork = world.getTotalWorldTime();
				int pumpRange = PollutionPlusConfig.Machines.pollutionPump.workRange;

				// find pollution
				outer: // cool tech from https://stackoverflow.com/questions/886955/
				for (int x = this.topPipe.getX() - pumpRange; x < this.topPipe.getX() + pumpRange + 1; x++) {
					for (int y = this.topPipe.getY() - pumpRange; y < this.topPipe.getY() + pumpRange + 1; y++) {
						for (int z = this.topPipe.getZ() - pumpRange; z < this.topPipe.getZ() + pumpRange + 1; z++) {
							IBlockState pollutionBlockState = world.getBlockState(new BlockPos(x, y, z));
							Block block = pollutionBlockState.getBlock();

							// if block was pollution (carbon & sulfur)
							if (block instanceof Pollutant && ((IPollutant) block).getPollutantType() == IPollutant.Type.AIR) {
								Pollutant<?> pollutant = (Pollutant<?>) block;
								int pollutionStrength = pollutant.getCarriedPollutionAmount(pollutionBlockState);

								// try and move the pollution
								if (this.pumpPollution(pollutionBlockState, pollutant, pollutionStrength)) {
									world.setBlockToAir(new BlockPos(x, y, z));
									break outer;
								}
							}
						}
					}
				}
			}
		}



		if (world.getBlockState(this.pos).getValue(BlockPollutionPump.ACTIVE) != powered) {
			this.updateState(powered);
		}
	}
	
	private boolean pumpPollution(IBlockState pollutionState, Pollutant<?> pollutionBlock, int strength) {
		List<EnumFacing> sides = Lists.newArrayList(EnumFacing.HORIZONTALS);
		//sides.addAll(EnumFacing.HORIZONTALS);
	
		Collections.shuffle(sides);

		// try pump pollution into an adjacent filter
		for (EnumFacing side : sides) {
			BlockPos adjacent = pos.offset(side);

			Block sideBlock = world.getBlockState(adjacent).getBlock();

			// try pump into enders filter
			if (sideBlock instanceof Filter) {
				Filter filterFrame = (Filter)sideBlock;
				
				// get filter tile entity
				Filter.BlockTile filterTileEntity = filterFrame.getBlockTile(world, adjacent);
				if (filterTileEntity != null) {
					int freeSpace = filterFrame.getContent(filterTileEntity).getFreeSpaceFor(pollutionBlock);
					
					if (freeSpace >= strength) {
						filterFrame.fill(filterTileEntity, pollutionBlock, strength);
						return true;
					}
				}
				// some stupid complex way of getting the tile entity
//				if (filterFrame.getTile(level, adjacent).isPresent()) {
//					FilterFrame.BlockTile filter = filterFrame.getTile(level, adjacent).get();
//
//					int freeSpace = filterFrame.getContent(filter).getFreeSpaceFor(pollutionBlock);
//					if (freeSpace >= strength) {
//						filterFrame.fill(filter, pollutionBlock, strength);
//						return true;
//					}
//				}
			// try pump into my filters
			} else if (sideBlock instanceof BlockPoweredFilterBase) {
				IFilter filter = (IFilter) world.getTileEntity(adjacent);
				if (filter != null && filter.hasCooledOff() && filter.canWork()) {
					filter.fakeUse();
					return true;
				}
			}
		}
		
		// no filters so try place next to
		for (EnumFacing side : sides) {
			BlockPos adjacent = this.pos.offset(side);
			if (world.isAirBlock(adjacent)) {
				world.setBlockState(adjacent, pollutionState, 3);
				return true;
			}
		}

		return false;
	}
	
	public boolean canWorkClient() {
		return world.getBlockState(this.pos).getValue(BlockPollutionPump.ACTIVE);
	}
	
	private boolean canWork() {
		return this.energy.getEnergyStored() >= PollutionPlusConfig.Machines.pollutionPump.operationCost && this.topPipe != null;
	}
	
	private boolean hasCooledOff() {
		//Main.logger.info("world: {}, last: {}", world.getTotalWorldTime(), this.lastWork);
		return world.getTotalWorldTime() - this.lastWork >= PollutionPlusConfig.Machines.pollutionPump.workSpeed;
	}
	
	private void useRf() {
		this.energy.extractEnergy(PollutionPlusConfig.Machines.pollutionPump.operationCost, false);
	}
	
	protected void updateState(boolean isPowered) {
		if (world.getBlockState(pos) != null) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockPollutionPump.ACTIVE, isPowered));
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
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
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.energy.receiveEnergy(compound.getInteger("energy"), false);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("energy", this.energy.getEnergyStored());			
		return compound;
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
