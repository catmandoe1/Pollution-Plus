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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.config.PollutionPlusConfig.Machines.Discharger;
import s11.mod.objects.blocks.unique.BlockDischarger;
import s11.mod.recipes.DischargerRecipes;
import s11.mod.util.PollutionSounds;
import s11.mod.util.TextHelper;

public class TileDischarger extends TileEntity implements ITickable {
	private final int maxCapacity = PollutionPlusConfig.Machines.discharger.maxCapacity;
	private final int maxExtract = PollutionPlusConfig.Machines.discharger.energyOutput;
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, Integer.MAX_VALUE, maxExtract);
	
	private ItemStackHandler handler = new ItemStackHandler(2);
	private String customName;
	
	private int progress = 0;
    private boolean firstStart = false;
    private boolean processing = false;
    
    //item bits
    private int progressLimit;
    private ItemStack inputCopy = ItemStack.EMPTY;

	@Override
	public void update() {
		if (world.isRemote) {
			//return; breaks progress bar and time in gui
			//TODO find a way to only have the server run the logic of the block but the client still be able to access progress and stuff
		}
		
		if (firstStart) {
			firstStart = false;
			markDirty();
		}
		
		ItemStack input = handler.getStackInSlot(0);
		ItemStack output = handler.getStackInSlot(1);
		
		// checking for valid item, after deleting that item and storing properties
		if (canStart() && !processing) {
			if (canDischarge()) {
				markDirty();
				processing = true;
				progressLimit = getRecipeTime(input);
				inputCopy = input.copy();
				input.shrink(1);
			}
			updateState();
		} else {
			if (processing && canStart()) {
				//super easy and simple way of playing sounds with lots of problems!
				if (progress % 40 == 0) {
					playRunningSound();
				}
				progress++;
				tickEnergy();
				updateState();
				
				if (progress >= progressLimit) {
					markDirty();
					
					// checks if the last energy addition will statisfy the total amount of energy that the item should give
					if (progress * getEnergyAddition() != getRecipePower(inputCopy)) {
						addOrRemoveEnergy(getRecipePower(inputCopy) - (progress * getEnergyAddition()));
					}
					
					progress = 0;
					processing = false;
					playRecipeCompleteSound();
					if (output.getCount() > 0) {
						output.grow(1);
					} else {
						handler.insertItem(1, getRecipe(inputCopy).copy(), false);
					}
				}
			}
		}
	}
	
	public int getTimeRemaining() {
		//int time = getMaxProgress() - progress;
		return Math.max(0, getMaxProgress() - progress);
	}
	
	public boolean canStart() {
		return getEnergyStored() + getEnergyAddition() <= getMaxEnergyStored();
	}
	
	public boolean canDischarge() {
		if (((ItemStack)this.handler.getStackInSlot(0)).isEmpty()) { // if input slot is empty
			return false;
		} else {
			ItemStack result = getRecipe(handler.getStackInSlot(0));
			if (result.isEmpty()) { // if not valid recipe
				return false;
			} else {
				ItemStack output = (ItemStack)this.handler.getStackInSlot(1);
				if (output.isEmpty()) { // if output is empty
					return true;
				}
				if (!output.isItemEqual(result)) { // if result and output item are not the same
					return false;
				}
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}
	
	private void playRunningSound() {
		if (!PollutionPlusConfig.GeneralConfig.machines.dischargerSound) {
			return;
		}
		world.playSound(null, pos, PollutionSounds.BLOCK_DISCHARGER_WORK, SoundCategory.BLOCKS, 0.25F, 1.0F);
	}
	
	private void playRecipeCompleteSound() {
		if (!PollutionPlusConfig.GeneralConfig.machines.dischargerSound) {
			return;
		}
		world.playSound(null, pos, PollutionSounds.BLOCK_DISCHARGER_RECIPE_COMPLETE, SoundCategory.BLOCKS, 1F, 1.0F);
	}
	
	private ItemStack getRecipe(ItemStack input) {
		return DischargerRecipes.getInstance().getRecipeResult(input);
	}
	
	/**
	 * the amount of ticks for the item to de infuse
	 * @param input
	 * @return
	 */
	public int getRecipeTime(ItemStack input) {
		return (int)Math.ceil(getRecipePower(input) / getEnergyAddition());
	}
	
	private int getRecipePower(ItemStack input) {
		return DischargerRecipes.getInstance().getPowerProduction(input);
	}
	
	public void tickEnergy() {
		addOrRemoveEnergy(getEnergyAddition());
	}
	
	/**
	 * the configs value - the efficiency
	 * @return
	 */
	public int getEnergyAddition() {
		float addition = PollutionPlusConfig.Machines.discharger.energyExtraction;
		float efficiency = PollutionPlusConfig.Machines.discharger.extractionEfficiency;
		return Math.round((addition * (efficiency * 0.01F)));
		
	}
	
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
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.discharger");
	}

	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("progress", this.progress);
		compound.setBoolean("processing", this.processing);
		compound.setInteger("progress_limit", this.progressLimit);
		
		if (inputCopy != null) {
			compound.setTag("inputCopy", this.inputCopy.serializeNBT());
		}
		
		compound.setTag("inventory", this.handler.serializeNBT());
		compound.setInteger("energy", this.energy.getEnergyStored());
		
		if(this.hasCustomName()) {
			compound.setString("customName", this.customName);
		}
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		progress = compound.getInteger("progress");
		processing = compound.getBoolean("processing");
		progressLimit = compound.getInteger("progress_limit");
		this.handler.deserializeNBT(compound.getCompoundTag("inventory"));
		setEnergy(compound.getInteger("energy"));
		this.inputCopy.deserializeNBT(compound.getCompoundTag("inputCopy"));
		
		if (compound.hasKey("customName", 8)) {
			setCustomName(compound.getString("customName"));
		}
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
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
	
	/**
	 * negative numbers remove energy
	 * @param amount
	 */
	public void addOrRemoveEnergy(int amount) {
		if (amount > 0) {
			this.energy.receiveEnergy(amount, false);
		} else if (amount < 0) {
			this.energy.extractEnergy(Math.abs(amount), false);
		}
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
			return getMaxProgress();
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
			setProgress(value);
			break;
		case 1:
			progressLimit = value;
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
		if (energy >= 1) {
			int diff = energy - getEnergyStored();
			addOrRemoveEnergy(diff);
		}
		
		if (energy == 0) {
			addOrRemoveEnergy(getEnergyStored() * -1);
		}
	}
	
	public void setProgress(int amount) {
		if (amount <= getMaxProgress()) {
			progress = amount;
		}
	}
	
	public int getProgress() {
		return progress;
	}
	
	public int getMaxProgress() {
		if (this.inputCopy == null || this.inputCopy.isEmpty()) {
			//TextHelper.print("null");
			return 0;
		}
		//TextHelper.print("server - working");
		return getRecipeTime(this.inputCopy);	
	}
	
	public int getEnergyStored() {
		return energy.getEnergyStored();
	}
	
	public int getMaxEnergyStored() {
		return maxCapacity;
	}
	
	private void updateState() {
		if (world.getBlockState(pos) != null) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockDischarger.ACTIVE, processing));
		}
	}
}
