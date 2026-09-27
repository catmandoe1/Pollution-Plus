package s11.mod.integration.oreDict;

import net.minecraftforge.oredict.OreDictionary;
import s11.mod.init.ItemInit;

public class OreDictionaryRegister {
	public static void registerOres() {
		// ingots
		OreDictionary.registerOre("ingotVoid", ItemInit.INGOT_VOID);
	}
}
