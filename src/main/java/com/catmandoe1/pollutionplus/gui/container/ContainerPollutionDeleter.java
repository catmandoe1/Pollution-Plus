package com.catmandoe1.pollutionplus.gui.container;

import com.catmandoe1.pollutionplus.PPContainers;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ContainerPollutionDeleter extends AbstractContainerMenu {
	public TileEntityPollutionDeleter deleter;
	public ContainerPollutionDeleter(final int id, final Inventory inv, FriendlyByteBuf extra) {
		super(PPContainers.POLLUTION_DELETER.get(), id);

		// get deleter tile entity from stored position in buffer
		if (inv.player.getCommandSenderWorld().getBlockEntity(extra.readBlockPos()) instanceof TileEntityPollutionDeleter tileEntity) {
			this.deleter = tileEntity;
		}
	}

	@Override
	public ItemStack quickMoveStack(Player player, int i) {
		return null;
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}
}
