package s11.mod.integration.jei.discharger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import s11.mod.recipes.DischargerRecipes;

public class DischargerRecipeFactory {
	public static List<DischargerRecipeWrapper> recipes() {
		List<DischargerRecipeWrapper> recipes = new ArrayList<>();
		for (Map.Entry<Item, Item> entry : DischargerRecipes.getInstance().getRecipes().entrySet()) {
			recipes.add(new DischargerRecipeWrapper(entry.getKey(), entry.getValue()));
		}
		return recipes;
	}
}
