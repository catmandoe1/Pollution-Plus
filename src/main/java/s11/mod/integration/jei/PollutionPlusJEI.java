package s11.mod.integration.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
//import mezz.jei.recipes.RecipeTransferRegistry; - removed because it broke builds
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import s11.mod.containers.ContainerAlloyFurnace;
import s11.mod.containers.ContainerHydraulicPress;
import s11.mod.containers.ContainerPowerInfuser;
import s11.mod.gui.GuiAlloyFurnace;
import s11.mod.gui.GuiHydraulicPress;
import s11.mod.gui.GuiPowerInfuser;
import s11.mod.init.BlockInit;
import s11.mod.integration.jei.alloyfurnace.AlloyFurnaceRecipeCatergory;
import s11.mod.integration.jei.alloyfurnace.AlloyFurnaceRecipeFactory;
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
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		
		registry.addRecipeCategories(new PowerInfuserRecipeCategory(guiHelper));
		registry.addRecipeCategories(new HydraulicPressRecipeCatergory(guiHelper));
		registry.addRecipeCategories(new AlloyFurnaceRecipeCatergory(guiHelper));
	}
	
	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}
	
	@Override
	public void register(IModRegistry registry) {
		helper = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
				
		registry.addIngredientInfo(new ItemStack(BlockInit.TILE_INCINERATOR), VanillaTypes.ITEM, "jei.description.incinerator"); //dont change, works fine
		
		//powered filters
		List<ItemStack> filters = new ArrayList<ItemStack>();
		filters.add(new ItemStack(BlockInit.TILE_IRON_POWERED_FILTER));
		filters.add(new ItemStack(BlockInit.TILE_GOLD_POWERED_FILTER));
		filters.add(new ItemStack(BlockInit.TILE_DIAMOND_POWERED_FILTER));
		filters.add(new ItemStack(BlockInit.TILE_VOID_POWERED_FILTER));
		registry.addIngredientInfo(filters, VanillaTypes.ITEM, "jei.description.powered_filters");
		
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
		
		//alloy furnace
		registry.addRecipes(AlloyFurnaceRecipeFactory.recipes(), AlloyFurnaceRecipeCatergory.UID);
		registry.addRecipeClickArea(GuiAlloyFurnace.class, 74, 36, 36, 16, AlloyFurnaceRecipeCatergory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockInit.TILE_ALLOY_FURNACE), AlloyFurnaceRecipeCatergory.UID);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerAlloyFurnace.class, AlloyFurnaceRecipeCatergory.UID, 0, 2, 3, 36);
	}
}
