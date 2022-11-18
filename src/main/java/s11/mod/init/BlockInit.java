package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import s11.mod.Main;
import s11.mod.objects.blocks.BlockToolTip;
import s11.mod.objects.blocks.unique.BlockAlloyFurnace;
import s11.mod.objects.blocks.unique.BlockHydraulicPress;
import s11.mod.objects.blocks.unique.BlockIncinerator;
import s11.mod.objects.blocks.unique.BlockPowerInfuser;
import s11.mod.objects.blocks.unique.powered_filters.BlockDiamondPoweredFilter;
import s11.mod.objects.blocks.unique.powered_filters.BlockGoldPoweredFilter;
import s11.mod.objects.blocks.unique.powered_filters.BlockIronPoweredFilter;
import s11.mod.objects.blocks.unique.powered_filters.BlockVoidPoweredFilter;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();	
	// Blocks Sparingly found around Y5 - Y30
	public static final Block ORE_VOID = new BlockToolTip("ore_void", Material.IRON, 3, 15, "pickaxe", 3, I18n.format("tile.ore_void.tooltip"));
	
	// Machines
	public static final Block TILE_INCINERATOR = new BlockIncinerator("tile_incinerator", Material.IRON, 3, 10, "pickaxe", 0);
	public static final Block TILE_POWER_INFUSER = new BlockPowerInfuser("tile_power_infuser", Material.IRON, 3, 10, "pickaxe", 0);
	public static final Block TILE_HYDRAULIC_PRESS = new BlockHydraulicPress("tile_hydraulic_press", Material.IRON, 3, 10, "pickaxe", 0);
	public static final Block TILE_ALLOY_FURNACE = new BlockAlloyFurnace("tile_alloy_furnace", Material.IRON, 3, 10, "pickaxe", 0);
	
	// Filters - material is leaves so pollution passes through it
	public static final Block TILE_IRON_POWERED_FILTER = new BlockIronPoweredFilter("tile_iron_powered_filter", Material.LEAVES, 3, 10, "pickaxe", 2);
	public static final Block TILE_GOLD_POWERED_FILTER = new BlockGoldPoweredFilter("tile_gold_powered_filter", Material.LEAVES, 2, 8, "pickaxe", 2);
	public static final Block TILE_DIAMOND_POWERED_FILTER = new BlockDiamondPoweredFilter("tile_diamond_powered_filter", Material.LEAVES, 6, 15, "pickaxe", 3);
	public static final Block TILE_VOID_POWERED_FILTER = new BlockVoidPoweredFilter("tile_void_powered_filter", Material.LEAVES, 4, 12.5F, "pickaxe", 3);
}
