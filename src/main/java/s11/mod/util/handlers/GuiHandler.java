package s11.mod.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import s11.mod.containers.ContainerAlloyFurnace;
import s11.mod.containers.ContainerDischarger;
import s11.mod.containers.ContainerHydraulicPress;
import s11.mod.containers.ContainerPowerInfuser;
import s11.mod.gui.GuiAlloyFurnace;
import s11.mod.gui.GuiDischarger;
import s11.mod.gui.GuiHydraulicPress;
import s11.mod.gui.GuiPowerInfuser;
import s11.mod.objects.tileEntities.machines.TileAlloyFurnace;
import s11.mod.objects.tileEntities.machines.TileDischarger;
import s11.mod.objects.tileEntities.machines.TileHydraulicPress;
import s11.mod.objects.tileEntities.machines.TilePowerInfuser;
import s11.mod.util.Reference;

public class GuiHandler implements IGuiHandler {

	//containers only
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (Reference.GuiIds.fromInt(ID)) {
			case POWER_INFUSER: 
				return new ContainerPowerInfuser(player.inventory, (TilePowerInfuser)world.getTileEntity(new BlockPos(x, y, z)));
			case HYDRAULIC_PRESS:
				return new ContainerHydraulicPress(player.inventory, (TileHydraulicPress)world.getTileEntity(new BlockPos(x, y, z)));
			case ALLOY_FURNACE:
				return new ContainerAlloyFurnace(player.inventory, (TileAlloyFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case DISCHARGER:
				return new ContainerDischarger(player.inventory, (TileDischarger)world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;
		}
	}

	// guis only
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (Reference.GuiIds.fromInt(ID)) {
			case POWER_INFUSER:
				return new GuiPowerInfuser(player.inventory, (TilePowerInfuser)world.getTileEntity(new BlockPos(x, y, z)));
			case HYDRAULIC_PRESS:
				return new GuiHydraulicPress(player.inventory, (TileHydraulicPress)world.getTileEntity(new BlockPos(x, y, z)));
			case ALLOY_FURNACE:
				return new GuiAlloyFurnace(player.inventory, (TileAlloyFurnace)world.getTileEntity(new BlockPos(x, y, z)));
			case DISCHARGER:
				return new GuiDischarger(player.inventory, (TileDischarger)world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;
		}
	}
}
