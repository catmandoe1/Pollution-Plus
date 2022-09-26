package s11.mod.integration.jei.powerinfuser;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import s11.mod.config.PowerInfuserConfig;
import s11.mod.init.BlockInit;
import s11.mod.util.Reference;
import s11.mod.util.TextHelper;

public class PowerInfuserRecipeCategory implements IRecipeCategory<PowerInfuserRecipeWrapper> {
	public static final String UID = Reference.MODID + ".powerinfuser";
	private final IDrawableStatic background;
	private final String localizedName;
	private final IDrawable icon;
	
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	
	public PowerInfuserRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(Reference.MODID + ":textures/jei/gui/power_infuser.png");
		background = guiHelper.createDrawable(location, 0, 0, 86, 30);
		localizedName = I18n.format("pollutionplus.jei.powerinfuser");
		icon = guiHelper.createDrawableIngredient(new ItemStack(BlockInit.TILE_POWER_INFUSER));
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
	public IDrawable getIcon() {
		return icon;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltip = Lists.newArrayList();
		// the area of the progress arrow
        if (mouseX >= 21 && mouseX <= 56 && mouseY >= 8 && mouseY <= 22) {
            tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.localize(new String[] {"jei.recipe.processTime"}) + TextFormatting.WHITE + " 1200 Ticks");
            tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.localize(new String[] { "jei.recipe.pertickcost"}) + TextFormatting.WHITE + PowerInfuserConfig.infuserOperationCost + " RF/t");
        }
        return tooltip;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, PowerInfuserRecipeWrapper recipeWrapper, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(INPUT_SLOT, true, 2, 6);
		recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 62, 6);
		
		recipeLayout.getItemStacks().set(INPUT_SLOT, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		recipeLayout.getItemStacks().set(OUTPUT_SLOT, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}
}
