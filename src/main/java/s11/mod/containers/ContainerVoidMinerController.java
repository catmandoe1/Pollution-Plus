package s11.mod.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import s11.mod.objects.tileEntities.machines.TileVoidMinerController;
import s11.mod.recipes.PowerInfuserRecipes;

public class ContainerVoidMinerController extends Container {
	private final TileVoidMinerController tileEntity;
	private int progress, maxProgress, energy, maxEnergy;
	
	public ContainerVoidMinerController(InventoryPlayer player, TileVoidMinerController tileEntity) {
		this.tileEntity = tileEntity;
		IItemHandler handler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		int index = 0;
		// output
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 4; x++) {
				addSlotToContainer(new SlotItemHandler(handler, index, 98 + x * 18, 17 + y * 18));
				index++;
			}
		}
		
		
		//player inventory
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileEntity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if (this.progress != this.tileEntity.getProgress()) {
				listener.sendWindowProperty(this, 0, this.tileEntity.getProgress());
			}
			
			if (this.maxProgress != this.tileEntity.getMaxProgress()) {
				listener.sendWindowProperty(this, 1, this.tileEntity.getMaxProgress());
			}
			
			if (this.energy != this.tileEntity.getEnergyStored()) {
				listener.sendWindowProperty(this, 2, this.tileEntity.getEnergyStored());
			}
			
			if (this.maxEnergy != this.tileEntity.getMaxEnergyStored()) {
				listener.sendWindowProperty(this, 3, this.tileEntity.getMaxEnergyStored());
			}
		}
		
		this.progress = this.tileEntity.getProgress();
		this.maxProgress = this.tileEntity.getMaxProgress();
		this.energy = this.tileEntity.getEnergyStored();
		this.maxEnergy = this.tileEntity.getMaxEnergyStored();
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		this.tileEntity.setField(id, data);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
        	ItemStack itemstack1 = slot.getStack();
        	itemstack = itemstack1.copy();
            
            //output 0 -> 11
            //12 -> 38 inventory
            //39 -> 47 hotbar
            if (index <= 11) { // outputs to inventory
                if (!this.mergeItemStack(itemstack1, 12, 47, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } //else if (index >= 39 && index < 48 && !this.mergeItemStack(itemstack1, 12, 39, false)) {
//                    return ItemStack.EMPTY;
//                
//             //output to player    
//            } else if (!this.mergeItemStack(itemstack1, 12, 48, false)) {
//                return ItemStack.EMPTY;
//            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
