package s11.mod.integration.jei.alloyfurnace;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AlloyFurnaceRecipeWrapper implements IRecipeWrapper {
	@Nonnull
    private final ItemStack itemInput1;
    @Nonnull
    private final ItemStack itemOutput;
    @Nonnull
    private final ItemStack itemInput2;

	public AlloyFurnaceRecipeWrapper(ItemStack input1, ItemStack input2, ItemStack output) {
		this.itemInput1 = input1;
		this.itemInput2 = input2;
        this.itemOutput = output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) {
		//List<List<ItemStack>> inputStacks = new ArrayList<>();
		
		List<ItemStack> itemStacks = new ArrayList<>();
		itemStacks.add(itemInput1);
		itemStacks.add(itemInput2);
		
		//inputStacks.add(itemStacks);
		
		ingredients.setInputs(VanillaTypes.ITEM, itemStacks);
        ingredients.setOutput(VanillaTypes.ITEM, itemOutput);
	}
}
