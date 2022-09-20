package s11.mod.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import s11.mod.objects.tileEntities.machines.powerInfuser.TilePowerInfuser;
import s11.mod.recipes.PowerInfuserRecipes;

public class ContainerPowerInfuser extends Container {
	
	private final TilePowerInfuser tileentity;
	private int progress, maxProgress, energy, maxEnergy;

		public ContainerPowerInfuser(InventoryPlayer player, TilePowerInfuser tileentity) {
		this.tileentity = tileentity;
		//System.out.println(tileentity); // null?
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 56, 35));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 116, 35));
		
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
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void detectAndSendChanges() {
		// TODO Auto-generated method stub
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
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		tileentity.setField(id, data);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
        	ItemStack itemstack1 = slot.getStack();
        	itemstack = itemstack1.copy();
            
            //output 1
            //input 0
            //2 -> 28 inventory
            //29 -> 37 hotbar
            if (index == 1) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 0) {
                if (!PowerInfuserRecipes.getInstance().getRecipeResult(itemstack1).isEmpty()) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                //inventory to hotbar   
                } else if (index >= 2 && index < 29) { 
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                //hotbar to inventory    
                } else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
             //output to player    
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
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
