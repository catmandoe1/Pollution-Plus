package s11.mod.integration.jei.powerinfuser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import s11.mod.recipes.PowerInfuserRecipes;

public class PowerInfuserRecipeFactory {
	
	public static List<PowerInfuserRecipeWrapper> recipes() {
		List<PowerInfuserRecipeWrapper> recipes = new ArrayList<>();
		for (Map.Entry<Item, Item> entry : PowerInfuserRecipes.getInstance().getRecipes().entrySet()) {
			recipes.add(new PowerInfuserRecipeWrapper(entry.getKey(), entry.getValue()));
		}
		return recipes;
	}
}
	
