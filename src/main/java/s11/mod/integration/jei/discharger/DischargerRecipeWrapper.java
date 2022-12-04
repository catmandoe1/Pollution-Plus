package s11.mod.integration.jei.discharger;

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
import s11.mod.recipes.DischargerRecipes;
import s11.mod.util.TextHelper;

public class DischargerRecipeWrapper implements IRecipeWrapper {
	@Nonnull
    private final Item itemInput;
    @Nonnull
    private final Item itemOutput;
    
    public DischargerRecipeWrapper(Item input, Item output) {
    	this.itemInput = input;
    	this.itemOutput = output;
    }
    
    @Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		List<String> tooltip = Lists.newArrayList();
		// the area of the progress arrow
        if (mouseX >= 21 && mouseX <= 56 && mouseY >= 8 && mouseY <= 22) {
//            tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.localize(new String[] {"jei.recipe.processTime"}) + TextFormatting.WHITE + " 1200 Ticks");
//            tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.localize(new String[] { "jei.recipe.pertickcost"}) + " " + TextFormatting.WHITE + 
//            		PowerInfuserRecipes.getInstance().getPowerUse(new ItemStack(itemInput))+ " RF/t");
        	tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.getLang("jei.discharger.power") + " " + TextFormatting.WHITE + 
        			DischargerRecipes.getInstance().getPowerProduction(new ItemStack(itemInput)) + " RF");
        	
        	tooltip.add(TextFormatting.LIGHT_PURPLE + TextHelper.getLang("jei.discharger.time") + " " + TextFormatting.WHITE + 
        			TextHelper.getHrMnScFromSeconds(TextHelper.getSecondsFromTicks(getRecipeTime(new ItemStack(itemInput)))));
        }
		return tooltip;
	}
    
    /**
	 * the configs value - the efficiency
	 * @return
	 */
	private int getEnergyAddition() {
		float addition = PollutionPlusConfig.Machines.discharger.energyExtraction;
		float efficiency = PollutionPlusConfig.Machines.discharger.extractionEfficiency;
		return Math.round((addition * (efficiency * 0.01F)));
		
	}
	
	private int getRecipePower(ItemStack input) {
		return DischargerRecipes.getInstance().getPowerProduction(input);
	}
	
	/**
	 * the amount of ticks for the item to de infuse
	 * @param input
	 * @return
	 */
	private int getRecipeTime(ItemStack input) {
		return (int)Math.ceil(getRecipePower(input) / getEnergyAddition());
	}
    
	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> itemStacks = new ArrayList<>();
		itemStacks.add(new ItemStack(itemInput));
		
		ingredients.setInputs(VanillaTypes.ITEM, itemStacks);
        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(itemOutput));
	}
}
