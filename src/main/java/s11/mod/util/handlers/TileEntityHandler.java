package s11.mod.util.handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import s11.mod.init.BlockInit;
import s11.mod.objects.tileEntities.machines.TileHydraulicPress;
import s11.mod.objects.tileEntities.machines.incinerator.TileIncinerator;
import s11.mod.objects.tileEntities.machines.powerInfuser.TilePowerInfuser;

public class TileEntityHandler {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileIncinerator.class, BlockInit.TILE_INCINERATOR.getRegistryName());
		GameRegistry.registerTileEntity(TilePowerInfuser.class, BlockInit.TILE_POWER_INFUSER.getRegistryName());
		GameRegistry.registerTileEntity(TileHydraulicPress.class, BlockInit.TILE_HYDRAULIC_PRESS.getRegistryName());
	}
}
