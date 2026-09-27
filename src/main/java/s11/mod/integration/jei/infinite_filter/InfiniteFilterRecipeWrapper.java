package s11.mod.integration.jei.infinite_filter;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InfiniteFilterRecipeWrapper implements IRecipeWrapper {
	private final Item activationItem;
	
	public InfiniteFilterRecipeWrapper(Item activationItem) {
		this.activationItem = activationItem;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(VanillaTypes.ITEM, new ItemStack(this.activationItem));
	}
}
