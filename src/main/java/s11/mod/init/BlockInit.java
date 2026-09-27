package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import s11.mod.objects.blocks.BlockIncinerator;
import s11.mod.objects.blocks.BlockInfiniteFilter;
import s11.mod.objects.blocks.BlockPollutionDeleter;
import s11.mod.objects.blocks.poweredfilters.BlockDiamondPoweredFilter;
import s11.mod.objects.blocks.poweredfilters.BlockGoldPoweredFilter;
import s11.mod.objects.blocks.poweredfilters.BlockIronPoweredFilter;
import s11.mod.objects.blocks.poweredfilters.BlockVoidPoweredFilter;
import s11.mod.objects.blocks.pump.BlockPollutionPipe;
import s11.mod.objects.blocks.pump.BlockPollutionPump;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	// Filters - material is leaves so pollution passes through it
	public static final Block TILE_IRON_POWERED_FILTER = new BlockIronPoweredFilter("tile_iron_powered_filter", Material.LEAVES, 3, 10, "pickaxe", 2);
	public static final Block TILE_GOLD_POWERED_FILTER = new BlockGoldPoweredFilter("tile_gold_powered_filter", Material.LEAVES, 2, 8, "pickaxe", 2);
	public static final Block TILE_DIAMOND_POWERED_FILTER = new BlockDiamondPoweredFilter("tile_diamond_powered_filter", Material.LEAVES, 6, 15, "pickaxe", 3);
	public static final Block TILE_VOID_POWERED_FILTER = new BlockVoidPoweredFilter("tile_void_powered_filter", Material.LEAVES, 4, 12.5F, "pickaxe", 3);
	public static final Block TILE_INFINITE_FILTER = new BlockInfiniteFilter("tile_infinite_filter",  Material.LEAVES, 2, 20, "pickaxe", 2);
	
	public static final Block TILE_INCINERATOR = new BlockIncinerator("tile_incinerator", Material.IRON, 3, 10, "pickaxe", 2);
	public static final Block TILE_POLLUTION_DELETER = new BlockPollutionDeleter("tile_pollution_deleter", Material.IRON, 2, 6, "pickaxe", 2);
	public static final Block TILE_POLLUTION_PUMP = new BlockPollutionPump("tile_pollution_pump", Material.IRON, 3, 10, "pickaxe", 2);
	public static final Block PIPE = new BlockPollutionPipe("pipe", Material.IRON, 2, 6, "pickaxe", 2);	
}
