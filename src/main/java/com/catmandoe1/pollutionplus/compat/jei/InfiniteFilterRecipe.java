package com.catmandoe1.pollutionplus.compat.jei;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.PPRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InfiniteFilterRecipe implements Recipe<Container> {

	@Override
	public boolean matches(@NotNull Container container, @NotNull Level level) {
		return false;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
		return new ItemStack(Config.infiniteFilterActivationItem);
	}

	@Override
	public boolean canCraftInDimensions(int i, int i1) {
		return false;
	}

	@Override
	public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
		return new ItemStack(Config.infiniteFilterActivationItem);
	}

	@Override
	public @NotNull ResourceLocation getId() {
		return null; // oh no null!
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return null; // oh no null!
	}

	@Override
	public @NotNull RecipeType<?> getType() {
		return PPRecipes.INFINITE_FILTER.get();
	}
}
