package s11.mod.util.interfaces;

import net.minecraft.block.Block;

public interface MultiBlock {
	public Boolean compareStructure(Block[][][] structureIn);
	public Block[][][] getStructure();
}
