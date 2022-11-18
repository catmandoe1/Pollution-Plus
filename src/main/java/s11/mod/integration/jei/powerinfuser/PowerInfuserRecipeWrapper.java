package s11.mod.integration.jei.powerinfuser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.recipes.PowerInfuserRecipes;
import s11.mod.util.TextHelper;

public class PowerInfuserRecipeWrapper implements IRecipeWrapper {
	
	@Nonnull
    private final Item itemInput;
    @Nonnull
    private final Item itemOutput;

	public PowerInfuserRecipeWrapper(Item input, Item output) {
		this.itemInput = input;
        this.itemOutput = output;
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltip = Lists.newArrayList();
		// the area of the progress arrow
        if (mouseX >= 21 && mouseX <= 56 && mouseY >= 8 && mouseY <= 22) {
            tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.localize(new String[] {"jei.recipe.processTime"}) + TextFormatting.WHITE + " 1200 Ticks");
            tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.localize(new String[] { "jei.recipe.pertickcost"}) + " " + TextFormatting.WHITE + 
            		PowerInfuserRecipes.getInstance().getPowerUse(new ItemStack(itemInput))+ " RF/t");
        }
		return tooltip;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		//List<List<ItemStack>> inputStacks = new ArrayList<>();
		
		List<ItemStack> itemStacks = new ArrayList<>();
		itemStacks.add(new ItemStack(itemInput));
		
		//inputStacks.add(itemStacks);
		
		ingredients.setInputs(VanillaTypes.ITEM, itemStacks);
        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(itemOutput));
	}

}
