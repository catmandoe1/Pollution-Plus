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
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
//import mezz.jei.recipes.RecipeTransferRegistry; - removed because it broke builds
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import s11.mod.containers.ContainerHydraulicPress;
import s11.mod.containers.ContainerPowerInfuser;
import s11.mod.gui.GuiHydraulicPress;
import s11.mod.gui.GuiPowerInfuser;
import s11.mod.init.BlockInit;
import s11.mod.integration.jei.hydraulicpress.HydraulicPressRecipeCatergory;
import s11.mod.integration.jei.hydraulicpress.HydraulicPressRecipeFactory;
import s11.mod.integration.jei.powerinfuser.PowerInfuserRecipeCategory;
import s11.mod.integration.jei.powerinfuser.PowerInfuserRecipeFactory;
import s11.mod.integration.jei.powerinfuser.PowerInfuserRecipeWrapper;
import s11.mod.recipes.PowerInfuserRecipes;

@JEIPlugin
public class PollutionPlusJEI implements IModPlugin {
	 public static IJeiHelpers helper;
	 
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new PowerInfuserRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeCategories(new HydraulicPressRecipeCatergory(registry.getJeiHelpers().getGuiHelper()));
	}
	
	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}
	
	@Override
	public void register(IModRegistry registry) {
		helper = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
				
		registry.addIngredientInfo(new ItemStack(BlockInit.TILE_INCINERATOR), ItemStack.class, "jei.description.incinerator"); //dont change, works fine
		
//		List<PowerInfuserRecipeWrapper> recipes = new ArrayList<>();
//		for (Map.Entry<Item, Item> entry : PowerInfuserRecipes.getInstance().getRecipes().entrySet()) {
//			recipes.add(new PowerInfuserRecipeWrapper(entry.getKey(), entry.getValue()));
//		}
		
		//power infuser
		registry.addRecipes(PowerInfuserRecipeFactory.recipes(), PowerInfuserRecipeCategory.UID);
		registry.addRecipeClickArea(GuiPowerInfuser.class, 74, 36, 36, 15, PowerInfuserRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockInit.TILE_POWER_INFUSER), PowerInfuserRecipeCategory.UID);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerPowerInfuser.class, PowerInfuserRecipeCategory.UID, 0, 1, 2, 36);
		
		//hydraulic press
		registry.addRecipes(HydraulicPressRecipeFactory.recipes(), HydraulicPressRecipeCatergory.UID);
		registry.addRecipeClickArea(GuiHydraulicPress.class, 74, 36, 36, 16, HydraulicPressRecipeCatergory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockInit.TILE_HYDRAULIC_PRESS), HydraulicPressRecipeCatergory.UID);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerHydraulicPress.class, HydraulicPressRecipeCatergory.UID, 0, 1, 2, 36);
	}
}
