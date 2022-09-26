package s11.mod.integration.jei.powerinfuser;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	public void getIngredients(IIngredients ingredients) {
		//List<List<ItemStack>> inputStacks = new ArrayList<>();
		
		List<ItemStack> itemStacks = new ArrayList<>();
		itemStacks.add(new ItemStack(itemInput));
		
		//inputStacks.add(itemStacks);
		
		ingredients.setInputs(VanillaTypes.ITEM, itemStacks);
        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(itemOutput));
	}

}
