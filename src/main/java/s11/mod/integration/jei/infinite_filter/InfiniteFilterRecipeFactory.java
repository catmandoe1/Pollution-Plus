package s11.mod.integration.jei.infinite_filter;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;

public class InfiniteFilterRecipeFactory {
	public static List<InfiniteFilterRecipeWrapper> recipes() {
		List<InfiniteFilterRecipeWrapper> recipes = new ArrayList<>();
		recipes.add(new InfiniteFilterRecipeWrapper(Items.NETHER_STAR));
		return recipes;
	}
}
