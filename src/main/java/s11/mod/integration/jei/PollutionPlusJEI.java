package s11.mod.integration.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import s11.mod.gui.GuiPowerInfuser;
import s11.mod.init.BlockInit;
import s11.mod.integration.jei.powerinfuser.PowerInfuserRecipeCategory;
import s11.mod.integration.jei.powerinfuser.PowerInfuserRecipeWrapper;
import s11.mod.recipes.PowerInfuserRecipes;

@JEIPlugin
public class PollutionPlusJEI implements IModPlugin {
	 public static IJeiHelpers helper;
	 
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new PowerInfuserRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}
	
	@Override
	public void register(IModRegistry registry) {
		helper = registry.getJeiHelpers();
				
		registry.addIngredientInfo(new ItemStack(BlockInit.TILE_INCINERATOR), ItemStack.class, "jei.description.incinerator"); //dont change, works fine
		
		List<PowerInfuserRecipeWrapper> recipes = new ArrayList<>();
		for (Map.Entry<Item, Item> entry : PowerInfuserRecipes.getInstance().getRecipes().entrySet()) {
			recipes.add(new PowerInfuserRecipeWrapper(entry.getKey(), entry.getValue()));
		}
		
		registry.addRecipes(recipes, PowerInfuserRecipeCategory.UID);
		registry.addRecipeClickArea(GuiPowerInfuser.class, 74, 36, 36, 15, PowerInfuserRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockInit.TILE_POWER_INFUSER), PowerInfuserRecipeCategory.UID);
	}
}
