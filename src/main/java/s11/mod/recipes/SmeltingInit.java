package s11.mod.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;

public class SmeltingInit {
	public static void initSmelting() {
		GameRegistry.addSmelting(BlockInit.ORE_VOID, new ItemStack(ItemInit.SHARD_VOID), 0.7F);
		GameRegistry.addSmelting(new ItemStack(ItemInit.BINDING_AGENT), new ItemStack(ItemInit.PRIMED_BINDING_AGENT), 1.0F);
		GameRegistry.addSmelting(new ItemStack(ItemInit.REFINED_CLUMP_VOID), new ItemStack(ItemInit.INGOT_REFINED_VOID), 10.0F);
	}
}
