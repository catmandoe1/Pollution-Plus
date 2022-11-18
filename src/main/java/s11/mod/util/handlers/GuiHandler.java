package s11.mod.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import s11.mod.containers.ContainerAlloyFurnace;
import s11.mod.containers.ContainerHydraulicPress;
import s11.mod.containers.ContainerPowerInfuser;
import s11.mod.gui.GuiAlloyFurnace;
import s11.mod.gui.GuiHydraulicPress;
import s11.mod.gui.GuiPowerInfuser;
import s11.mod.objects.tileEntities.machines.TileAlloyFurnace;
import s11.mod.objects.tileEntities.machines.TileHydraulicPress;
import s11.mod.objects.tileEntities.machines.TilePowerInfuser;
import s11.mod.util.Reference;

public class GuiHandler implements IGuiHandler {

	//containers only
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GuiIds.POWER_INFUSER.getIndex()) {
			return new ContainerPowerInfuser(player.inventory, (TilePowerInfuser)world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == Reference.GuiIds.HYDRAULIC_PRESS.getIndex()) {
			return new ContainerHydraulicPress(player.inventory, (TileHydraulicPress)world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == Reference.GuiIds.ALLOY_FURNACE.getIndex()) {
			return new ContainerAlloyFurnace(player.inventory, (TileAlloyFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	// guis only
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GuiIds.POWER_INFUSER.getIndex()) {
			return new GuiPowerInfuser(player.inventory, (TilePowerInfuser)world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == Reference.GuiIds.HYDRAULIC_PRESS.getIndex()) {
			return new GuiHydraulicPress(player.inventory, (TileHydraulicPress)world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == Reference.GuiIds.ALLOY_FURNACE.getIndex()) {
			return new GuiAlloyFurnace(player.inventory, (TileAlloyFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
