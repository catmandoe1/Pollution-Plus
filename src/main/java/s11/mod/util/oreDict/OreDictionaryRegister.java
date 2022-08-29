package s11.mod.util.oreDict;

import net.minecraftforge.oredict.OreDictionary;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;

public class OreDictionaryRegister {
	public static void registerOres() {
		// items
		OreDictionary.registerOre("ingotVoid", ItemInit.INGOT_VOID);
		OreDictionary.registerOre("starVoid", ItemInit.STAR_VOID);
		// blocks
		OreDictionary.registerOre("oreVoid", BlockInit.ORE_VOID);
	}
}
