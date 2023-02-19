package s11.mod.integration.oreDict;

import net.minecraftforge.oredict.OreDictionary;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;

public class OreDictionaryRegister {
	public static void registerOres() {
		// ingots
		OreDictionary.registerOre("ingotVoid", ItemInit.INGOT_VOID);
		OreDictionary.registerOre("ingotRefinedVoid", ItemInit.INGOT_REFINED_VOID);
		OreDictionary.registerOre("ingotSteel", ItemInit.INGOT_STEEL);
		
		// nuggets
		OreDictionary.registerOre("nuggetSteel", ItemInit.NUGGET_STEEL);
		
		// rods
		OreDictionary.registerOre("rodSteel", ItemInit.ROD_STEEL);
		
		//OreDictionary.registerOre("starVoid", ItemInit.STAR_VOID);
		//OreDictionary.registerOre("shardVoid", ItemInit.SHARD_VOID);
		//OreDictionary.registerOre("shardRefinedVoid", ItemInit.SHARD_REFINED_VOID);
		
		// dusts
		OreDictionary.registerOre("dustIron", ItemInit.DUST_IRON);
		OreDictionary.registerOre("dustGold", ItemInit.DUST_GOLD);
		
		// tiny dusts
		OreDictionary.registerOre("tinyDustDiamond", ItemInit.SPECK_DIAMOND);
		
		// blocks
		OreDictionary.registerOre("oreVoid", BlockInit.ORE_VOID);
		OreDictionary.registerOre("blockSteel", BlockInit.STEEL_BLOCK);
		OreDictionary.registerOre("frameSteel", BlockInit.STEEL_FRAME);
	}
}
