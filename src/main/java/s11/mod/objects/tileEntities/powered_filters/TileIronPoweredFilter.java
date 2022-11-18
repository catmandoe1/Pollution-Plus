package s11.mod.objects.tileEntities.powered_filters;

import java.util.List;

import com.endertech.minecraft.mods.adpother.entities.EntityPollutant;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.unique.powered_filters.BlockIronPoweredFilter;

public class TileIronPoweredFilter extends TileEntity implements ITickable {
	private final int maxCapacity = PollutionPlusConfig.PoweredFilters.iron.filterMaxCapacity;
	private final int maxTransfer = Integer.MAX_VALUE;
	private final int maxExtract = Integer.MAX_VALUE;
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, maxTransfer, maxExtract);
	
	private int counter = 0;
	private boolean isRunning = false;
	private boolean firstStart = true;
	private boolean justUpdated = false;
	private boolean isCooldown = false;
	
	@Override
	public void update() {
		if (getWorld().isRemote) { // kicks out client
			return;
		}
		
		if (firstStart) {
			firstStart = false;
			updateState(isRunning);
		}
		
		// deletes pollution within the filter when powered
		if (canRun()) {
			useTickPower();
			isRunning = true;
			
			if (isCooldown) {
				counter++;
				if (counter >= PollutionPlusConfig.PoweredFilters.iron.filterSpeed) {
					counter = 0;
					isCooldown = false;
				}
			}
			
			if (!isCooldown) {
				List pollutants = getWorld().getEntitiesWithinAABB(EntityPollutant.class, getFilterBB());
				if (pollutants.size() > 0) {
					isCooldown = true;
					counter = 1;
					
					// kills all the pollution in the filter
					for (int i = 0; i < pollutants.size(); i++) {
						((EntityPollutant) pollutants.get(i)).setDead();
					}
				}
			}
			
			if (justUpdated == false) {
				updateState(isRunning);
				justUpdated = true;
			}
			
		} else {
			isRunning = false;
			
			if(justUpdated) {
				updateState(isRunning);
			justUpdated = false;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		setEnergy(compound.getInteger("energy"));
		counter = compound.getInteger("counter");
		isCooldown = compound.getBoolean("isCooldown");
	}
	
	public boolean isRunning() {
		return this.isRunning;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("energy", this.energy.getEnergyStored());
		compound.setInteger("counter", this.counter);
		compound.setBoolean("isCooldown", this.isCooldown);
			
		return compound;
	}
	
	public boolean canRun() {
		return getEnergyStored() >= PollutionPlusConfig.PoweredFilters.iron.filterPowerUse;
	}
	
	/**
	 * removes per tick energy
	 */
	public void useTickPower() {
		addOrRemoveEnergy(PollutionPlusConfig.PoweredFilters.iron.filterPowerUse * -1);
	}
	
	public AxisAlignedBB getFilterBB() {
		return new AxisAlignedBB(getPos());
	}
	
	public void addOrRemoveEnergy(int amount) {
		if (amount > 0) {
			this.energy.receiveEnergy(amount, false);
		} else if (amount < 0) {
			this.energy.extractEnergy(Math.abs(amount), false);
		}
	}
	
	/**
     * Sets the energy to the value, NOT adds. Use {@link addOrRemoveEnergy} for that
     */
	public void setEnergy(int energy) {
		//TODO make this set the energy properly
		if (energy >= 1) {
			int diff = energy - getEnergyStored();
			addOrRemoveEnergy(diff);
		}
		
		if (energy == 0) {
			addOrRemoveEnergy(getEnergyStored() * -1);
		}
	}	
	
	public int getEnergyStored() {
		return energy.getEnergyStored();
	}
	
	public int getMaxEnergyStored() {
		return maxCapacity;
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
	
	private void updateState(boolean isPowered) {
		if (world.getBlockState(pos) != null) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockIronPoweredFilter.ACTIVE, isPowered));
		}
	}
}
