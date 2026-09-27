package s11.mod.integration.jei.infinite_filter;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import s11.mod.init.BlockInit;
import s11.mod.util.Reference;
import s11.mod.util.TextHelper;

public class InfiniteFilterRecipeCategory implements IRecipeCategory<InfiniteFilterRecipeWrapper> {
	public static final String UID = Reference.MODID + ".infinite_filter";
	private final IDrawableStatic background;
	private final String localizedName;
	private final IDrawable icon;
	
	public InfiniteFilterRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(Reference.MODID, "textures/jei/gui/infinite_filter.png");
		background = guiHelper.createDrawable(location, 0, 0, 26, 26);
		localizedName = TextHelper.getLang("jei.pollutionplus.infinite_filter.title");
		icon = guiHelper.createDrawableIngredient(new ItemStack(BlockInit.TILE_INFINITE_FILTER));
	}
	
	@Override
	public String getUid() {
		// TODO Auto-generated method stub
		return UID;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.localizedName;
	}

	@Override
	public String getModName() {
		return Reference.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, InfiniteFilterRecipeWrapper recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 4, 4);
		recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
	}

}
