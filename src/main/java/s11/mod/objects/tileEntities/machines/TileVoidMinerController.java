package s11.mod.objects.tileEntities.machines;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
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
import s11.mod.init.BlockInit;
import s11.mod.objects.blocks.unique.BlockVoidMinerController;

public class TileVoidMinerController extends TileEntity implements ITickable {
	//	
	//	Unused
	//
	
	private final EnergyStorage energy = new EnergyStorage(PollutionPlusConfig.Machines.voidMinerController.maxCapacity, Integer.MAX_VALUE, Integer.MAX_VALUE);
	private boolean isActive = false;
	private boolean isBuilt = false;
	private int progress = 0;
	private int maxProgress = PollutionPlusConfig.Machines.voidMinerController.workSpeed;
	
	private ItemStackHandler handler = new ItemStackHandler(12);
	private String customName;
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		
		if (canMine() && isBuilt) {
			tickEnergy();
			progress++;
			
			if (progress >= maxProgress) {
				progress = 0;
				addItemToOutput(new ItemStack(BlockInit.ORE_VOID));
			}
		}
	}
	
	/**
	 * checks if the multiblock has been modified and if so stops it from working
	 */
	private boolean checkStructure() {
		Block controller = world.getBlockState(getPos()).getBlock();
		//IProperty facing = controller.getBlockState().getProperty("facing"); TODO figure out how to get the facing of the controller to optimize the check
		return true;
	}
	
	/**
	 * 
	 * @param itemStack Edit the amount to add more than 1
	 * @return if it failed to add the items
	 */
	private boolean addItemToOutput(ItemStack itemStack) {
		if (itemStack == null || itemStack == ItemStack.EMPTY) {
			return true;
		}
		
		ItemStack items = itemStack.copy();
		
		boolean failed = false;
		//output 0 -> 11 (12 total)
		
		//cycles through each slot starting from the top left.
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			ItemStack item = handler.getStackInSlot(slot).copy();
			
			items = handler.insertItem(slot, items, false);
			
			if (items == ItemStack.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean canMine() {
		return getEnergyStored() >= PollutionPlusConfig.Machines.voidMinerController.operationCost;
	}
	
	/**
	 * removes power based on config
	 */
	private void tickEnergy() {
		energy.extractEnergy(PollutionPlusConfig.Machines.voidMinerController.operationCost, false);
	}
	
	private void setEnergy(int setEnergy) {
		if (setEnergy > getEnergyStored()) {
			energy.receiveEnergy(setEnergy - getEnergyStored(), false);
		} else if (setEnergy < getEnergyStored()) {
			energy.extractEnergy(getEnergyStored() - setEnergy, false);
		}
	}
	
	/**
	 * built by right clicking with allen key
	 * @return
	 */
	public boolean isBuilt() {
		return isBuilt;
	}
	
	public void setBuilt() {
		this.isBuilt = true;
	}
	
	public int getProgress() {
		return this.progress;
	}
	
	public int getMaxProgress() {
		return this.maxProgress;
	}
	
	public int getEnergyStored() {
		return energy.getEnergyStored();
	}
	
	public int getMaxEnergyStored() {
		return energy.getMaxEnergyStored();
	}
	
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
	
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.customName) : new TextComponentTranslation("container.void_miner_controller");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setTag("inventory", this.handler.serializeNBT());
		compound.setInteger("energy", this.energy.getEnergyStored());
		compound.setBoolean("isBuilt", isBuilt());
		
		if(this.hasCustomName()) {
			compound.setString("customName", this.customName);
		}
		
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.handler.deserializeNBT(compound.getCompoundTag("inventory"));
		setEnergy(compound.getInteger("energy"));
		if (compound.getBoolean("isBuilt")) {			
			setBuilt();
		}
		
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
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	private void updateState() {
		if (world.getBlockState(pos) != null) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockVoidMinerController.ACTIVE, isActive));
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}
