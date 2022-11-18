package s11.mod.integration.jei.alloyfurnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import s11.mod.recipes.AlloyFurnaceRecipes;

public class AlloyFurnaceRecipeFactory {
	public static List<AlloyFurnaceRecipeWrapper> recipes() {
		List<AlloyFurnaceRecipeWrapper> recipes = new ArrayList<>();
		Map<ItemStack, Map<ItemStack, ItemStack>> map = AlloyFurnaceRecipes.getInstance().getRecipesJeiItemStack().rowMap();
		
		for (Map.Entry<ItemStack, Map<ItemStack, ItemStack>> outer : map.entrySet()) {
		    for (Map.Entry<ItemStack, ItemStack> inner : outer.getValue().entrySet()) {
		        recipes.add(new AlloyFurnaceRecipeWrapper(outer.getKey(), inner.getKey(), inner.getValue()));
		    }
		}
		
		return recipes;
	}
}
