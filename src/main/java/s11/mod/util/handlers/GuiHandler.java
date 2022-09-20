package s11.mod.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import s11.mod.containers.ContainerPowerInfuser;
import s11.mod.gui.GuiPowerInfuser;
import s11.mod.objects.tileEntities.machines.powerInfuser.TilePowerInfuser;
import s11.mod.util.Reference;

public class GuiHandler implements IGuiHandler {

	//containers only
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GUI_POWERINFUSER) {
			return new ContainerPowerInfuser(player.inventory, (TilePowerInfuser)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	// guis only
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GUI_POWERINFUSER) {
			return new GuiPowerInfuser(player.inventory, (TilePowerInfuser)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
