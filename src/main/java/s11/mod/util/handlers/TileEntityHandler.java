package s11.mod.util.handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import s11.mod.objects.tileEntities.machines.incinerator.TileIncinerator;
import s11.mod.objects.tileEntities.machines.powerInfuser.TilePowerInfuser;

public class TileEntityHandler {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileIncinerator.class, "tile_incinerator");
		GameRegistry.registerTileEntity(TilePowerInfuser.class, "tile_power_infuser");
	}
}
