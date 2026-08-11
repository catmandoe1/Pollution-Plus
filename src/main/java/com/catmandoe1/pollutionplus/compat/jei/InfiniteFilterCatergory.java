package com.catmandoe1.pollutionplus.compat.jei;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfiniteFilterCatergory implements IRecipeCategory<InfiniteFilterRecipe> {
	public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "infinite_filter_jei");
	private final IDrawableStatic background;
	private final IGuiHelper gui;

	public InfiniteFilterCatergory(IGuiHelper gui) {
		background = gui.drawableBuilder(new ResourceLocation(Main.MODID, "textures/jei/infinite_filter.png"), 0, 0, 26, 26).setTextureSize(26, 26).build();
		this.gui = gui;
	}

	@Override
	public @NotNull RecipeType<InfiniteFilterRecipe> getRecipeType() {
		return JeiCompat.INFINITE_FILTER;
	}

	@Nullable
	@Override
	public IDrawableStatic getBackground() {
		return background;
	}

	@Override
	public @NotNull Component getTitle() {
		return Component.translatable("jei.pollutionplus.infinite_filter.title");
	}

	@Override
	public @Nullable IDrawable getIcon() {
		return gui.createDrawableItemLike(PPBlocks.INFINITE_FILTER.getItem());
	}

	@Override
	public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull InfiniteFilterRecipe recipe, @NotNull IFocusGroup focusGroup) {
		builder.addSlot(RecipeIngredientRole.INPUT, 5, 5).addIngredients(Ingredient.of(recipe.getResultItem(RegistryAccess.EMPTY)));
	}
}
