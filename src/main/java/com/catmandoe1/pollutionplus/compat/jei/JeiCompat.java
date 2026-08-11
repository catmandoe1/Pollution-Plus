package com.catmandoe1.pollutionplus.compat.jei;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.item.PPItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JeiCompat implements IModPlugin {
	public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "jei_compat");
	public static final RecipeType<InfiniteFilterRecipe> INFINITE_FILTER = RecipeType.create(Main.MODID, "infinite_filter", InfiniteFilterRecipe.class);

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registry) {
		// go through all items and check if they have a jei lang, then add info page with the text
		for (RegistryObject<Item> item : PPItems.ITEMS.getEntries()) {
			String langKey = item.get().getDescriptionId() + ".jei";

			if (I18n.exists(langKey)) {
				registry.addIngredientInfo(new ItemStack(item.get()), VanillaTypes.ITEM_STACK, Component.translatable(langKey));
			}
		}

		// powered filters info
		List<ItemStack> filters = new ArrayList<ItemStack>();
		filters.add(new ItemStack(PPBlocks.POWERED_FILTER_IRON.get()));
		filters.add(new ItemStack(PPBlocks.POWERED_FILTER_GOLD.get()));
		filters.add(new ItemStack(PPBlocks.POWERED_FILTER_DIAMOND.get()));
		filters.add(new ItemStack(PPBlocks.POWERED_FILTER_VOID.get()));
		registry.addIngredientInfo(filters, VanillaTypes.ITEM_STACK, Component.translatable("jei.pollutionplus.description.powered_filters"));

		// infinite filter activation item
		registry.addRecipes(INFINITE_FILTER, List.of(new InfiniteFilterRecipe()));
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		registration.addRecipeCategories(new InfiniteFilterCatergory(registration.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(PPBlocks.INFINITE_FILTER.getItem(), INFINITE_FILTER);
	}
}
