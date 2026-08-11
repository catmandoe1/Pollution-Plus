package com.catmandoe1.pollutionplus;

import com.catmandoe1.pollutionplus.compat.jei.InfiniteFilterRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

public class PPRecipes {
	public static void init(IEventBus bus) {
		RECIPE_TYPES.register(bus);
	}

	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Main.MODID);
	public static final RegistryObject<RecipeType<InfiniteFilterRecipe>> INFINITE_FILTER = RECIPE_TYPES.register("infinite_filter", () -> new RecipeType<>() {});
}
