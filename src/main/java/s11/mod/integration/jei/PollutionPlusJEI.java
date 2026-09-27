package s11.mod.integration.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;
import s11.mod.integration.jei.infinite_filter.InfiniteFilterRecipeCategory;
import s11.mod.integration.jei.infinite_filter.InfiniteFilterRecipeFactory;

@JEIPlugin
public class PollutionPlusJEI implements IModPlugin {
	 public static IJeiHelpers helper;
	 
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		
		registry.addRecipeCategories(new InfiniteFilterRecipeCategory(guiHelper));
	}
	
	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}
	
	@Override
	public void register(IModRegistry registry) {
		helper = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
				
		registry.addIngredientInfo(new ItemStack(BlockInit.TILE_INCINERATOR), VanillaTypes.ITEM, "block.pollutionplus.incinerator.jei"); //dont change, works fine
		registry.addIngredientInfo(new ItemStack(BlockInit.TILE_INFINITE_FILTER), VanillaTypes.ITEM, "block.pollutionplus.infinite_filter.jei"); //dont change, works fine
		registry.addIngredientInfo(new ItemStack(BlockInit.TILE_POLLUTION_DELETER), VanillaTypes.ITEM, "block.pollutionplus.pollution_deleter.jei"); //dont change, works fine
		registry.addIngredientInfo(new ItemStack(BlockInit.TILE_POLLUTION_PUMP), VanillaTypes.ITEM, "block.pollutionplus.pollution_pump.jei"); //dont change, works fine
		registry.addIngredientInfo(new ItemStack(BlockInit.PIPE), VanillaTypes.ITEM, "block.pollutionplus.pipe.jei"); //dont change, works fine
		registry.addIngredientInfo(new ItemStack(ItemInit.LOCATION_MARKER), VanillaTypes.ITEM, "item.pollutionplus.location_marker.jei"); //dont change, works fine
		
		//powered filters
		List<ItemStack> filters = new ArrayList<ItemStack>();
		filters.add(new ItemStack(BlockInit.TILE_IRON_POWERED_FILTER));
		filters.add(new ItemStack(BlockInit.TILE_GOLD_POWERED_FILTER));
		filters.add(new ItemStack(BlockInit.TILE_DIAMOND_POWERED_FILTER));
		filters.add(new ItemStack(BlockInit.TILE_VOID_POWERED_FILTER));
		registry.addIngredientInfo(filters, VanillaTypes.ITEM, "jei.pollutionplus.description.powered_filters");
		
		registry.addRecipes(InfiniteFilterRecipeFactory.recipes(), InfiniteFilterRecipeCategory.UID);
		registry.addRecipeCatalyst(new ItemStack(BlockInit.TILE_INFINITE_FILTER), InfiniteFilterRecipeCategory.UID);
	}
}
