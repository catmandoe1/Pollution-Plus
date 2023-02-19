package s11.mod.util.handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import s11.mod.init.BlockInit;
import s11.mod.objects.tileEntities.machines.TileAlloyFurnace;
import s11.mod.objects.tileEntities.machines.TileDischarger;
import s11.mod.objects.tileEntities.machines.TileHydraulicPress;
import s11.mod.objects.tileEntities.machines.TileIncinerator;
import s11.mod.objects.tileEntities.machines.TilePowerInfuser;
import s11.mod.objects.tileEntities.machines.TileVoidMinerController;
import s11.mod.objects.tileEntities.powered_filters.TileDiamondPoweredFilter;
import s11.mod.objects.tileEntities.powered_filters.TileGoldPoweredFilter;
import s11.mod.objects.tileEntities.powered_filters.TileIronPoweredFilter;
import s11.mod.objects.tileEntities.powered_filters.TileVoidPoweredFilter;

public class TileEntityHandler {
	public static void registerTileEntities() {
		//machines
		GameRegistry.registerTileEntity(TileIncinerator.class, BlockInit.TILE_INCINERATOR.getRegistryName());
		GameRegistry.registerTileEntity(TilePowerInfuser.class, BlockInit.TILE_POWER_INFUSER.getRegistryName());
		GameRegistry.registerTileEntity(TileHydraulicPress.class, BlockInit.TILE_HYDRAULIC_PRESS.getRegistryName());
		GameRegistry.registerTileEntity(TileAlloyFurnace.class, BlockInit.TILE_ALLOY_FURNACE.getRegistryName());
		GameRegistry.registerTileEntity(TileDischarger.class, BlockInit.TILE_DISCHARGER.getRegistryName());
		//GameRegistry.registerTileEntity(TileVoidMinerController.class, BlockInit.VOID_MINER_CONTROLLER.getRegistryName());
		
		//powered filters
		GameRegistry.registerTileEntity(TileIronPoweredFilter.class, BlockInit.TILE_IRON_POWERED_FILTER.getRegistryName());
		GameRegistry.registerTileEntity(TileGoldPoweredFilter.class, BlockInit.TILE_GOLD_POWERED_FILTER.getRegistryName());
		GameRegistry.registerTileEntity(TileDiamondPoweredFilter.class, BlockInit.TILE_DIAMOND_POWERED_FILTER.getRegistryName());
		GameRegistry.registerTileEntity(TileVoidPoweredFilter.class, BlockInit.TILE_VOID_POWERED_FILTER.getRegistryName());
	}
}
