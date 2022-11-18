package s11.mod.integration.jei.hydraulicpress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import s11.mod.recipes.HydraulicPressRecipes;

public class HydraulicPressRecipeFactory {

	public static List<HydraulicPressRecipeWrapper> recipes() {
		List<HydraulicPressRecipeWrapper> recipes = new ArrayList<>();
		for (Map.Entry<Item, ItemStack> entry : HydraulicPressRecipes.getInstance().getRecipes().entrySet()) {
			recipes.add(new HydraulicPressRecipeWrapper(entry.getKey(), entry.getValue()));
		}
		return recipes;
	}
}
