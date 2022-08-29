package s11.mod.world.gen;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import s11.mod.init.BlockInit;

public class WorldGenCustomOres implements IWorldGenerator{
	private WorldGenerator ore_void;
	
	public WorldGenCustomOres() {
		// Void ore
		ore_void = new WorldGenMinable(BlockInit.ORE_VOID.getDefaultState(), 3, BlockMatcher.forBlock(Blocks.STONE));
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
		case -1:
			// Nether Ores
			break;
			
		case 0:
			// Overworld Ores
			runGenerator(ore_void, world, random, chunkX, chunkZ, 20, 5, 30); 
			break;
		case 1:
			// End Ores
			break;
		}
	}
	
	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight) {
		if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");
		
		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < chance; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);
			
			gen.generate(world, rand, new BlockPos(x, y, z));
		}
		
	}
}
