package s11.mod.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class PowerInfuserRecipes {
	private static final PowerInfuserRecipes INSTANCE = new PowerInfuserRecipes();
	private final Map<Item, Item> infusingList = new HashMap<Item, Item>();
	
	public static PowerInfuserRecipes getInstance( ) {
		return INSTANCE;
	}
	
	private PowerInfuserRecipes() {
		addInfuserRecipe(Items.APPLE, Items.GOLDEN_APPLE);
		addInfuserRecipe(Items.NETHER_STAR, Items.NETHERBRICK);
	}
	
	public void oreDictAddRecipe(NonNullList<ItemStack> input, Item output) {
		if (!input.isEmpty()) {
			input.forEach(is -> {
				addInfuserRecipe(is.getItem(), output);
			});
		}
	}
	
	public void addInfuserRecipe(Item input, Item output) {
		//if (!(input != null) && !(output != null)) {
			infusingList.put(input, output);
		//}
	}
	
	public ItemStack getRecipeResult(ItemStack input) {
		if (input.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		Item item = input.getItem();
		if (infusingList.containsKey(item)) {
			return new ItemStack(infusingList.get(item));
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	public Map<Item, Item> getRecipes() {
		return infusingList;
	}
}
