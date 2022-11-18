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
import s11.mod.objects.tileEntities.machines.TileAlloyFurnace;
import s11.mod.recipes.AlloyFurnaceRecipes;

public class ContainerAlloyFurnace extends Container{
	private final TileAlloyFurnace tileentity;
	private int progress, maxProgress, energy, maxEnergy;

	public ContainerAlloyFurnace(InventoryPlayer player, TileAlloyFurnace tileentity) {
		this.tileentity = tileentity;
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 35, 35)); //input1
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 56, 35));// input2
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 116, 35)); // output
		
		
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
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if (this.progress != this.tileentity.getProgress()) {
				listener.sendWindowProperty(this, 0, this.tileentity.getProgress());
			}
			
			if (this.maxProgress != this.tileentity.getMaxProgress()) {
				listener.sendWindowProperty(this, 1, this.tileentity.getMaxProgress());
			}
			
			if (this.energy != this.tileentity.getEnergyStored()) {
				listener.sendWindowProperty(this, 2, this.tileentity.getEnergyStored());
			}
			
			if (this.maxEnergy != this.tileentity.getMaxEnergyStored()) {
				listener.sendWindowProperty(this, 3, this.tileentity.getMaxEnergyStored());
			}
		}
		
		this.progress = this.tileentity.getProgress();
		this.maxProgress = this.tileentity.getMaxProgress();
		this.energy = this.tileentity.getEnergyStored();
		this.maxEnergy = this.tileentity.getMaxEnergyStored();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileentity.setField(id, data);
	}
	
	//TODO needs fixed
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
        	ItemStack itemstack1 = slot.getStack();
        	itemstack = itemstack1.copy();
            
            //output = 2  (old 1)
            //input1 = 0  (old 0)
        	//input2 = 1
            //3 -> 29 inventory
            //30 -> 38 hotbar
        	
        	// output to inventory
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 0 && index != 1) {
            	// transfer to inputs
                if (AlloyFurnaceRecipes.getInstance().canTransferToSlot(itemstack)) {
                    if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
                    	return ItemStack.EMPTY;
                    }
                //inventory to hotbar 
                } else if (index >= 3 && index < 30) { 
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                //hotbar to inventory    
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
             //output to player    
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

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
