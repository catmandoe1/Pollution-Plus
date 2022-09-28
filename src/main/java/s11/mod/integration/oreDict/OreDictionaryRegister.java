package s11.mod.integration.oreDict;

import net.minecraftforge.oredict.OreDictionary;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;

public class OreDictionaryRegister {
	public static void registerOres() {
		// items
		OreDictionary.registerOre("ingotVoid", ItemInit.INGOT_VOID);
		OreDictionary.registerOre("starVoid", ItemInit.STAR_VOID);
		OreDictionary.registerOre("ingotRefinedVoid", ItemInit.INGOT_REFINED_VOID);
		OreDictionary.registerOre("shardVoid", ItemInit.SHARD_VOID);
		OreDictionary.registerOre("shardRefinedVoid", ItemInit.SHARD_REFINED_VOID);
		OreDictionary.registerOre("dustIron", ItemInit.DUST_IRON);
		OreDictionary.registerOre("dustGold", ItemInit.DUST_GOLD);
		OreDictionary.registerOre("tinyDustDiamond", ItemInit.SPECK_DIAMOND);
		
		// blocks
		OreDictionary.registerOre("oreVoid", BlockInit.ORE_VOID);
	}
}
