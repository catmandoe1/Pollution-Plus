package s11.mod.objects.tileEntities.machines.incinerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.endertech.minecraft.forge.api.IPollutant;
import com.endertech.minecraft.mods.adpother.blocks.Pollutant;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import s11.mod.config.IncineratorConfig;
import s11.mod.objects.blocks.unique.BlockIncinerator;

public class TileIncinerator extends TileEntity implements ITickable {
	private final int maxCapacity = IncineratorConfig.incineratorMaxCapacity;
	private final int maxTransfer = Integer.MAX_VALUE;
	private final int maxExtract = Integer.MAX_VALUE;
	
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, maxTransfer, maxExtract);
	private boolean isPowered = false; //is saved to nbt
	private final int machineUsePower = IncineratorConfig.incineratorPowerUse;
	private final int machineWorkSpeed = IncineratorConfig.incineratorWorkSpeed; //in game ticks (defualt 200)
	private final int machineRange = 5;
	
	private boolean firstStart = true; //isn't saved
	private int updateCounter = 0;
	private BlockPos tilePos;
	private long lastWork;
	private boolean usePower = false;
	private boolean justUpdated = false;
	private boolean hasRedstone;

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
		isPowered = energy.getEnergyStored() >= machineUsePower;
		
		//updates tile if just loaded
		if (firstStart == true) {
			updateState(isPowered, hasRedstone);
			firstStart = false;
		}
		
		//incinerator pollution deletion
		if (!hasRedstone && isPowered && world.getTotalWorldTime() - lastWork >= machineWorkSpeed) { //only runs if redstone is off, has power and workspeed delay is over
			if (!justUpdated) {
				updateState(isPowered, hasRedstone);
				justUpdated = true;
			}
			//System.out.println("passed");
			lastWork = world.getTotalWorldTime();
			List<BlockPos> positions = getPositionsAroundTile();
			
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
			if (usePower) {
				energy.extractEnergy(machineUsePower, false);
				usePower = false;
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
	
	private List<BlockPos> getPositionsAroundTile() {
		List<BlockPos> positions = new ArrayList<>();
		for (int x = pos.getX() - machineRange; x <= pos.getX() + machineRange; x++) {
			for (int y = pos.getY() - machineRange; y <= pos.getY() + machineRange; y++) {
				for (int z = pos.getZ() - machineRange; z <= pos.getZ() + machineRange; z++) {
					positions.add(new BlockPos(x, y, z));
				}
			}
		}
		Collections.shuffle(positions);
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
	public void markDirty() {
		super.markDirty();
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
