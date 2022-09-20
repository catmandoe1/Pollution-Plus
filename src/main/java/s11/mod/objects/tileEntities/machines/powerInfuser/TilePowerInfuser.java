package s11.mod.objects.tileEntities.machines.powerInfuser;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
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
import s11.mod.config.IncineratorConfig;
import s11.mod.config.PowerInfuserConfig;
import s11.mod.objects.blocks.unique.BlockIncinerator;
import s11.mod.objects.blocks.unique.BlockPowerInfuser;
import s11.mod.recipes.PowerInfuserRecipes;

public class TilePowerInfuser extends TileEntity implements ITickable {
	private final int maxCapacity = PowerInfuserConfig.infuserMaxCapacity;
	private final int maxTransfer = Integer.MAX_VALUE;
	private final int maxExtract = Integer.MAX_VALUE;
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, maxTransfer, maxExtract);
	
	private ItemStackHandler handler = new ItemStackHandler(2);
	private String customName;
	    
    private int progress = 0;
    private final int MAXPROGRESS = 40;
    //private boolean active = false;
    private boolean justUpdated = false;
    private boolean firstStart = false;
    
//    public TilePowerInfuser() {
//    	System.out.println("-------------------");
//    	System.out.println("	  made");
//    	System.out.println("-------------------");
//    }
    
    public boolean hasCustomName() 
	{
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.power_infuser");
	}

	public boolean isUsableByPlayer(EntityPlayer player) 
	{
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
		compound.setInteger("energy", (short)this.energy.getEnergyStored());
		compound.setTag("inventory", this.handler.serializeNBT());
		
		if(this.hasCustomName()) compound.setString("customName", this.customName);
		return compound;
	}
	
	public boolean canInfuse() {
		//TODO double check if works
		if (((ItemStack)this.handler.getStackInSlot(0)).isEmpty()) {
			return false;
		} else {
			ItemStack result = getRecipe(handler.getStackInSlot(0));
			//System.out.println("result = " + result);
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
		//addOrRemoveEnergy(1);
		return energy.getEnergyStored() > 0;
	}
	
	public boolean isInfusing() {
		//boolean test = progress > 0;
		//System.out.println("can infuse = " + test);
		return progress > 0; 
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
	}
	
	@Override
	public void update() {
		// TODO finish
		if (world.isRemote) {
			return;
		}
		
		if (firstStart == true) {
			updateState(isInfusing());
			firstStart = false;
		}
		
		ItemStack input = handler.getStackInSlot(0);
		ItemStack output = handler.getStackInSlot(1);
		
		//System.out.println(canInfuse() + " can infuse");
		
		if(canActivate() && canInfuse()) {
			setField(2, (~(Math.abs(PowerInfuserConfig.infuserOperationCost - 1)))); //removes energy per tick
			progress ++;
			System.out.println("actual energy = " + energy);
			
			if (progress >= MAXPROGRESS) {
				progress = 0;
				
				//System.out.println("finished");
				if (handler.getStackInSlot(1).getCount() > 0) {
					handler.getStackInSlot(1).grow(1);
				} else {
					handler.insertItem(1, getRecipe(input).copy(), false);
					//System.out.println(getRecipe(input));
				}
				handler.getStackInSlot(0).shrink(1);
			}
			
			if(justUpdated == false) {
				updateState(true);
				justUpdated = true;
			}
			
		} else if(justUpdated) {
			updateState(false);
			justUpdated = false;
		}
	}
	
	private ItemStack getRecipe(ItemStack input) {
		return PowerInfuserRecipes.getInstance().getRecipeResult(input);
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
	 * Use the numerous get... s such as {@link getEnergy} instead now.
	 * 
	 * 
	 * 
	 * @param {@code id} ranges from 0  -> 3
	 * @return progress = 0, maxProgress = 1, energy = 2, maxCapacity = 3
	 */
	@Deprecated
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
	 * Use the numerous sets... s such as {@link setEnergy} instead now.
	 * @param id
	 * @param value
	 */
	@Deprecated
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			this.progress = value;
			break;
		case 1:
			break;
		case 2:
			addOrRemoveEnergy(value);
			break;
		}
	}
	
	/**
     * Renamed version of {@code addOrRemoveEnergy}
     */
	public void setEnergy(int energy) {
		addOrRemoveEnergy(energy);
	}
	
	public void setProgress(int amount) {
		if (amount <= MAXPROGRESS) {
			progress = amount;
		} else {
			throw new ArithmeticException("Can not set progress above the max progress limit");
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
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockPowerInfuser.ACTIVE, isPowered));
		}
	}
}
