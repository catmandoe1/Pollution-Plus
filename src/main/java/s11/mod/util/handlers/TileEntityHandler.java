package s11.mod.util.handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import s11.mod.init.BlockInit;
import s11.mod.objects.tileEntities.TileIncinerator;
import s11.mod.objects.tileEntities.TileInfiniteFilter;
import s11.mod.objects.tileEntities.TilePollutionDeleter;
import s11.mod.objects.tileEntities.TilePollutionPump;
import s11.mod.objects.tileEntities.powered_filters.TileDiamondPoweredFilter;
import s11.mod.objects.tileEntities.powered_filters.TileGoldPoweredFilter;
import s11.mod.objects.tileEntities.powered_filters.TileIronPoweredFilter;
import s11.mod.objects.tileEntities.powered_filters.TileVoidPoweredFilter;

public class TileEntityHandler {
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileIncinerator.class, BlockInit.TILE_INCINERATOR.getRegistryName());
		GameRegistry.registerTileEntity(TileInfiniteFilter.class, BlockInit.TILE_INFINITE_FILTER.getRegistryName());
		GameRegistry.registerTileEntity(TilePollutionPump.class, BlockInit.TILE_POLLUTION_PUMP.getRegistryName());
		GameRegistry.registerTileEntity(TilePollutionDeleter.class, BlockInit.TILE_POLLUTION_DELETER.getRegistryName());
		
		//powered filters
		GameRegistry.registerTileEntity(TileIronPoweredFilter.class, BlockInit.TILE_IRON_POWERED_FILTER.getRegistryName());
		GameRegistry.registerTileEntity(TileGoldPoweredFilter.class, BlockInit.TILE_GOLD_POWERED_FILTER.getRegistryName());
		GameRegistry.registerTileEntity(TileDiamondPoweredFilter.class, BlockInit.TILE_DIAMOND_POWERED_FILTER.getRegistryName());
		GameRegistry.registerTileEntity(TileVoidPoweredFilter.class, BlockInit.TILE_VOID_POWERED_FILTER.getRegistryName());
	}
}
