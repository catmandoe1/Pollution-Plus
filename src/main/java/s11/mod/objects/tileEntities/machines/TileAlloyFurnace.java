package s11.mod.objects.tileEntities.machines;

import java.util.List;

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
import s11.mod.objects.blocks.unique.BlockAlloyFurnace;
import s11.mod.recipes.AlloyFurnaceRecipes;
import s11.mod.util.PollutionSounds;

public class TileAlloyFurnace extends TileEntity implements ITickable{
	private final int maxCapacity = PollutionPlusConfig.Machines.alloyFurnace.maxCapacity;
	private final int maxTransfer = Integer.MAX_VALUE;
	private final int maxExtract = Integer.MAX_VALUE;
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, maxTransfer, maxExtract);
	
	private ItemStackHandler handler = new ItemStackHandler(3);
	private String customName;
	
	private int progress = 0;
    private final int MAXPROGRESS = 160;
    private boolean justUpdated = false;
    private boolean firstStart = false;
    private boolean runningSoundActive = false;
		
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	public ItemStackHandler getInventoryHandler() {
		return handler;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.alloy_furnace");
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
		
		if (this.hasCustomName()) {
			compound.setString("customName", this.customName);
		}
			
		return compound;
	}
	
	public boolean canAlloy() {
		if (((ItemStack)this.handler.getStackInSlot(0)).isEmpty() || ((ItemStack)this.handler.getStackInSlot(1)).isEmpty()) {
			return false;
		} else {
			ItemStack result = getRecipe(handler.getStackInSlot(0), handler.getStackInSlot(1));
			if (result.isEmpty()) {
				return false;
			} else {
				ItemStack output = (ItemStack)this.handler.getStackInSlot(2);
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
	
	/**
	 * if the machine as enough power to run
	 * @return
	 */
	public boolean canActivate() {
		return energy.getEnergyStored() >= PollutionPlusConfig.Machines.alloyFurnace.operationCost;
	}
	
	/**
	 * is the machine active
	 * @return
	 */
	public boolean isAlloying() {
		return progress > 0; 
	}

	public void playRecipeCompleteSound() {
		if (PollutionPlusConfig.GeneralConfig.machinesSounds.alloyFurnaceSound) {
			world.playSound(null, pos, PollutionSounds.BLOCK_ALLOY_FURNACE_RECIPE_COMPLETE, SoundCategory.BLOCKS, 0.1F, 1.0F);
		}
	}
		
	@Override
	public void update() {
		if (world.isRemote) {
			//updateRunningSound();
			return;
		}
		
		//initial load stuff
		if (firstStart == true) {
			updateState(isAlloying());
			firstStart = false;
		}
		
		ItemStack input = handler.getStackInSlot(0);
		ItemStack input2 = handler.getStackInSlot(1);
		ItemStack output = handler.getStackInSlot(2);
		
		if (canActivate() && canAlloy()) {
			markDirty();
			addOrRemoveEnergy((Math.abs(PollutionPlusConfig.Machines.alloyFurnace.operationCost) * -1)); //removes energy per tick also makes sure that it can only remove energy
			//super easy and simple way of playing sounds with lots of problems!
			if (progress % 40 == 0) {
				runningSoundActive = true;
			}
			
			progress ++;
			
			if (progress >= MAXPROGRESS) {
				progress = 0;
				
				List<ItemStack> result = AlloyFurnaceRecipes.getInstance().getFullRecipeResult(input, input2);
				
				if (output.getCount() > 0) {
					output.grow(result.get(2).getCount());
				} else {
					//handler.insertItem(2, getRecipeDouble(input, input2).copy(), false);
					handler.insertItem(2, result.get(2), false);
				}
				input.shrink(result.get(0).getCount());
				input2.shrink(result.get(1).getCount());
				playRecipeCompleteSound();
			}
			
			if(justUpdated == false) {
				updateState(true);
				justUpdated = true;
			}
			
		} else {
			if (input.isEmpty() || input2.isEmpty() || !canAlloy()) {
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
		
	/**
	 * @param value (0 <-> 16)
	 */
	private void setLightLevel(int value) {
		//BlockAlloyFurnace.setLightLevelManual(value);
	}
	
	private ItemStack getRecipe(ItemStack input, ItemStack input2) {
		return AlloyFurnaceRecipes.getInstance().getRecipeResult(input, input2);
	}
	
	/**
	 * A dodgy way of bypassing the {@code handler.getStackInSlot(y).grow(x)} not working
	 * @param input
	 * @param input2
	 * @return
	 * @deprecated
	 */
	private ItemStack getRecipeDouble(ItemStack input, ItemStack input2) {
		return AlloyFurnaceRecipes.getInstance().getRecipeResultDouble(input, input2);
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
			//max progress - dont change
			break;
		case 2:
			setEnergy(value);
			break;
		case 3:
			//max energy - dont change
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
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockAlloyFurnace.ACTIVE, isPowered));
		}
	}
}
