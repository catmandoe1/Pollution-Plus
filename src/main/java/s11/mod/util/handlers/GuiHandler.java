package s11.mod.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import s11.mod.containers.ContainerPollutionDeleter;
import s11.mod.gui.GuiPollutionDeleter;
import s11.mod.objects.tileEntities.TilePollutionDeleter;
import s11.mod.util.Reference;

public class GuiHandler implements IGuiHandler {

	//containers only
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (Reference.GuiIds.fromInt(ID)) {
			case POLLUTION_DELETER:
				return new ContainerPollutionDeleter();
			default:
				return null;
		}
	}

	// guis only
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (Reference.GuiIds.fromInt(ID)) {
			case POLLUTION_DELETER:
				return new GuiPollutionDeleter(player, (TilePollutionDeleter)world.getTileEntity(new BlockPos(x, y, z)));
			default:
				return null;
		}
	}
}
