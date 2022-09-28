package s11.mod.integration.jei.hydraulicpress;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HydraulicPressRecipeWrapper implements IRecipeWrapper {
	
	@Nonnull
    private final Item itemInput;
    @Nonnull
    private final Item itemOutput;
    
    public HydraulicPressRecipeWrapper(Item input, Item output) {
    	this.itemInput = input;
    	this.itemOutput = output;
    }

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<ItemStack> itemStacks = new ArrayList<>();
		itemStacks.add(new ItemStack(itemInput));
		
		ingredients.setInputs(VanillaTypes.ITEM, itemStacks);
        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(itemOutput));
	}

}
