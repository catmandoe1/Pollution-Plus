package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.blocks.BlockTrans;
import s11.mod.objects.blocks.unique.BlockAlloyFurnace;
import s11.mod.objects.blocks.unique.BlockDischarger;
import s11.mod.objects.blocks.unique.BlockHydraulicPress;
import s11.mod.objects.blocks.unique.BlockIncinerator;
import s11.mod.objects.blocks.unique.BlockPowerInfuser;
import s11.mod.objects.blocks.unique.BlockVoidOre;
import s11.mod.objects.blocks.unique.powered_filters.BlockDiamondPoweredFilter;
import s11.mod.objects.blocks.unique.powered_filters.BlockGoldPoweredFilter;
import s11.mod.objects.blocks.unique.powered_filters.BlockIronPoweredFilter;
import s11.mod.objects.blocks.unique.powered_filters.BlockVoidPoweredFilter;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();	
	// Blocks Sparingly found around Y5 - Y30
	public static final Block ORE_VOID = new BlockVoidOre("ore_void", Material.IRON, 3, 15, "pickaxe", 3, "tile.ore_void.tooltip");
	public static final Block STEEL_FRAME = new BlockTrans("steel_frame", Material.IRON, 2, 10, "pickaxe", 2);
	public static final Block STEEL_BLOCK = new BlockBase("steel_block", Material.IRON, 4, 25, "pickaxe", 2);
	
	// Void miner special components
//	public static final Block VOID_MINER_ENERGY_COMPONENT = new BlockBase("void_miner_energy_component", Material.IRON, 2, 10, "pickaxe", 2);
//	public static final Block VOID_MINER_HEAT_SINK = new BlockBase("void_miner_heat_sink", Material.IRON, 2, 10, "pickaxe", 2);
//	public static final Block VOID_MINER_LASER_CORE = new BlockBase("void_miner_laser_core", Material.IRON, 2, 10, "pickaxe", 2);
//	public static final Block VOID_MINER_LENS = new BlockTrans("void_miner_lens", Material.GLASS, 1, 3, "pickaxe", 1);
//	public static final Block VOID_MINER_CONTROLLER = new BlockVoidMinerController("void_miner_controller", Material.IRON, 2, 10, "pickaxe", 2);
	
	// Machines
	public static final Block TILE_INCINERATOR = new BlockIncinerator("tile_incinerator", Material.IRON, 3, 10, "pickaxe", 2);
	public static final Block TILE_POWER_INFUSER = new BlockPowerInfuser("tile_power_infuser", Material.IRON, 3, 10, "pickaxe", 2);
	public static final Block TILE_HYDRAULIC_PRESS = new BlockHydraulicPress("tile_hydraulic_press", Material.IRON, 3, 10, "pickaxe", 2);
	public static final Block TILE_ALLOY_FURNACE = new BlockAlloyFurnace("tile_alloy_furnace", Material.IRON, 3, 10, "pickaxe", 1);
	public static final Block TILE_DISCHARGER = new BlockDischarger("tile_discharger", Material.IRON, 3, 10, "pickaxe", 2);
	
	// Filters - material is leaves so pollution passes through it
	public static final Block TILE_IRON_POWERED_FILTER = new BlockIronPoweredFilter("tile_iron_powered_filter", Material.LEAVES, 3, 10, "pickaxe", 2);
	public static final Block TILE_GOLD_POWERED_FILTER = new BlockGoldPoweredFilter("tile_gold_powered_filter", Material.LEAVES, 2, 8, "pickaxe", 2);
	public static final Block TILE_DIAMOND_POWERED_FILTER = new BlockDiamondPoweredFilter("tile_diamond_powered_filter", Material.LEAVES, 6, 15, "pickaxe", 3);
	public static final Block TILE_VOID_POWERED_FILTER = new BlockVoidPoweredFilter("tile_void_powered_filter", Material.LEAVES, 4, 12.5F, "pickaxe", 3);
}
