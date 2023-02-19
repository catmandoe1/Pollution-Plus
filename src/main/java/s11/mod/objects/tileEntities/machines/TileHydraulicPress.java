package s11.mod.objects.tileEntities.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.unique.BlockHydraulicPress;
import s11.mod.recipes.HydraulicPressRecipes;
import s11.mod.util.PollutionSounds;

public class TileHydraulicPress extends TileEntity implements ITickable {
	private final int maxCapacity = PollutionPlusConfig.Machines.hydraulicPress.maxCapacity;
	private final int maxTransfer = Integer.MAX_VALUE;
	private final int maxExtract = Integer.MAX_VALUE;
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, maxTransfer, maxExtract);
	
	private ItemStackHandler handler = new ItemStackHandler(2);
	private String customName;
	
	private int progress = 0;
    private final int MAXPROGRESS = 160;
    private boolean justUpdated = false;
    private boolean firstStart = false;
    private int INPUT = 0;
	private int OUTPUT = 1;
		
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.hydraulic_press");
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		progress = compound.getInteger("progress");
		addOrRemoveEnergy(compound.getInteger("energy"));
		this.handler.deserializeNBT(compound.getCompoundTag("inventory"));
		
		if (compound.hasKey("customName", 8)) {
			setCustomName(compound.getString("customName"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("progress", (short)this.progress);
		compound.setInteger("energy", this.energy.getEnergyStored());
		compound.setTag("inventory", this.handler.serializeNBT());
		
		if(this.hasCustomName()) compound.setString("customName", this.customName);
		return compound;
	}
	
	public boolean canCrush() {
		if (((ItemStack)this.handler.getStackInSlot(0)).isEmpty()) {
			return false;
		} else {
			ItemStack result = getRecipe(handler.getStackInSlot(0));
			if (result.isEmpty()) {
				return false;
			} else {
				ItemStack output = (ItemStack)this.handler.getStackInSlot(1);
				if (output.isEmpty()) {
					return true;
				}
				if (!output.isItemEqual(result)) {
					return false;
				}
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}
	
	public boolean canActivate() {
		//addOrRemoveEnergy(100);
		return energy.getEnergyStored() >= PollutionPlusConfig.Machines.hydraulicPress.operationCost;
	}
	
	public boolean isCrushing() {
		return progress > 0; 
	}
	
	public void playRunningSound() {
		if (!PollutionPlusConfig.GeneralConfig.machinesSounds.hydraulicPressSound) {
			return;
		}
		world.playSound(null, pos, PollutionSounds.BLOCK_HYDRAULIC_PRESS_RUNNING, SoundCategory.BLOCKS, 0.25F, 1.0F);
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		
		if (firstStart == true) {
			updateState(isCrushing());
			firstStart = false;
		}
		
		ItemStack input = handler.getStackInSlot(0);
		ItemStack output = handler.getStackInSlot(1);
		
				
		if(canActivate() && canCrush()) {
			markDirty();
			addOrRemoveEnergy(Math.abs(PollutionPlusConfig.Machines.hydraulicPress.operationCost) * -1); //removes energy per tick
			//super easy and simple way of playing sounds with lots of problems!
			if (progress % 40 == 0) {
				playRunningSound();
			}
			progress ++;
			
			if (progress >= MAXPROGRESS) {
				progress = 0;
				
				// redone item to a good way.
				if (output.getCount() > 0) {
					handler.insertItem(OUTPUT, new ItemStack(input.getItem()), false);
				} else {
					handler.insertItem(1, getRecipe(input).copy(), false);
				}
				handler.extractItem(INPUT, 1, false);
			}
			
			if(justUpdated == false) {
				updateState(true);
				justUpdated = true;
			}
			
		} else {
			if (!canCrush() || input.isEmpty()) {
				if (progress > 1) {
					progress = progress - 2;
				}
			}
			
			if(justUpdated) {
				updateState(false);
				justUpdated = false;
			}
		}
	}
	
	private ItemStack getRecipe(ItemStack input) {
		return HydraulicPressRecipes.getInstance().getRecipeResult(input);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return (T) energy;
		}
		
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) this.handler;
		}
		
		return super.getCapability(capability, facing);
	}
	
	public void addOrRemoveEnergy(int amount) {
		if (amount > 0) {
			this.energy.receiveEnergy(amount, false);
		} else if (amount < 0) {
			this.energy.extractEnergy(Math.abs(amount), false);
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
	
	/**
	 * Use the numerous get... s such as {@link getEnergy} instead.
	 * 
	 * 
	 * 
	 * @param {@code id} ranges from 0  -> 3
	 * @return progress = 0, maxProgress = 1, energy = 2, maxCapacity = 3
	 */
	public int getField(int id) {
		switch(id) {
		case 0:
			return progress;
		case 1:
			return MAXPROGRESS;
		case 2:
			return this.energy.getEnergyStored();
		case 3: 
			return maxCapacity;
		default:
			return 0;
		}
	}
	
	/**
	 * Use the numerous sets... s such as {@link setEnergy} instead.
	 * @param id
	 * @param value
	 */
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			progress = value;
			break;
		case 1:
			//max progress
			break;
		case 2:
			setEnergy(value);
			break;
		case 3:
			//max energy
			break;
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
	
	public void setProgress(int amount) {
		if (amount <= MAXPROGRESS) {
			progress = amount;
		}
	}
	
	public int getProgress() {
		return progress;
	}
	
	public int getMaxProgress() {
		return MAXPROGRESS;	
	}
	
	public int getEnergyStored() {
		return energy.getEnergyStored();
	}
	
	public int getMaxEnergyStored() {
		return maxCapacity;
	}
	
	private void updateState(boolean isPowered) {
		if (world.getBlockState(pos) != null) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockHydraulicPress.ACTIVE, isPowered));
		}
	}

	public ItemStackHandler getInventoryHandler() {
		return handler;
	}
}
