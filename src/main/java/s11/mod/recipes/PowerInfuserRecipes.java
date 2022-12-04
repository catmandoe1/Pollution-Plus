package s11.mod.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.init.ItemInit;

public class PowerInfuserRecipes {
	private static final PowerInfuserRecipes INSTANCE = new PowerInfuserRecipes();
	private final Map<Item, Item> infusingList = new HashMap<Item, Item>();
	private final Map<Item, Integer> powerList = new HashMap<Item, Integer>();
	
	public static PowerInfuserRecipes getInstance( ) {
		return INSTANCE;
	}
	
	private PowerInfuserRecipes() {
		//addInfuserRecipe(ItemInit.SHARD_REFINED_VOID, ItemInit.SHARD_REFINED_VOID_INFUSED); removed
		addInfuserRecipe(ItemInit.SHARD_VOID, ItemInit.SHARD_VOID_INFUSED, 300);
		addInfuserRecipe(ItemInit.INGOT_VOID, ItemInit.INGOT_VOID_INFUSED, 500);
		addInfuserRecipe(ItemInit.INGOT_REFINED_VOID, ItemInit.INGOT_REFINED_VOID_INFUSED, 1000);
		addInfuserRecipe(ItemInit.STAR_VOID_OFF, ItemInit.STAR_VOID, 2000);
	}
	
	public void oreDictAddRecipe(NonNullList<ItemStack> input, Item output) {
		if (!input.isEmpty()) {
			input.forEach(is -> {
				addInfuserRecipe(is.getItem(), output);
			});
		}
	}
	
	public void addInfuserRecipe(Item input, Item output) {
		addInfuserRecipe(input, output, PollutionPlusConfig.Machines.powerInfuser.baseOperationCost);
	}
	
	public void addInfuserRecipe(Item input, Item output, int powerUse) {
		infusingList.put(input, output);
		powerList.put(input, powerUse);
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
	
	public int getPowerUse(ItemStack input) {
		if (input.isEmpty()) {
			return PollutionPlusConfig.Machines.powerInfuser.baseOperationCost;
		}
		
		Item item = input.getItem();
		if (powerList.containsKey(item)) {
			return powerList.get(item);
		} else {
			return PollutionPlusConfig.Machines.powerInfuser.baseOperationCost;
		}
	}
	
	public Map<Item, Item> getRecipes() {
		return infusingList;
	}
	
	public Map<Item, Integer> getPowerRecipes() {
		return powerList;
	}
}
