package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.blocks.unique.BlockIncinerator;
import s11.mod.objects.blocks.unique.VoidOre;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();	
	// Blocks
	public static final Block ORE_VOID = new VoidOre("ore_void", Material.IRON);
	public static final Block TILE_INCINERATOR = new BlockIncinerator("tile_incinerator", Material.IRON);
}
