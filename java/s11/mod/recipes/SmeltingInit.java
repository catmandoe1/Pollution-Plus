package s11.mod.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;

public class SmeltingInit {
	public static void initSmelting() {
		GameRegistry.addSmelting(BlockInit.ORE_VOID, new ItemStack(ItemInit.INGOT_VOID), 10.0F);
	}
}
