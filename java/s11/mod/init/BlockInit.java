package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import s11.mod.objects.blocks.BlockBase;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();	
	// Blocks
	public static final Block ORE_VOID = new BlockBase("ore_void", Material.IRON);
}
