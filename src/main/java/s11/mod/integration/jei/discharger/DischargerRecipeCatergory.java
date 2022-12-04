package s11.mod.integration.jei.discharger;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import s11.mod.init.BlockInit;
import s11.mod.recipes.DischargerRecipes;
import s11.mod.util.Reference;
import s11.mod.util.TextHelper;

public class DischargerRecipeCatergory implements IRecipeCategory<DischargerRecipeWrapper> {
	public static final String UID = Reference.MODID + ".discharger";
	private final IDrawableStatic background;
	private final String localizedName;
	private final IDrawable icon;
	
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	
	public DischargerRecipeCatergory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(Reference.MODID + ":textures/jei/gui/discharger.png");
		background = guiHelper.createDrawable(location, 0, 0, 86, 30);
		localizedName = TextHelper.getLang("pollutionplus.jei.discharger");
		icon = guiHelper.createDrawableIngredient(new ItemStack(BlockInit.TILE_DISCHARGER));
	}

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return Reference.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DischargerRecipeWrapper recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(INPUT_SLOT, true, 2, 6);
		recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 62, 6);
		
		recipeLayout.getItemStacks().set(INPUT_SLOT, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

}
