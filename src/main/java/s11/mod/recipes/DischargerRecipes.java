package s11.mod.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.init.ItemInit;

public class DischargerRecipes {
	private static final DischargerRecipes INSTANCE = new DischargerRecipes();
	private final Map<Item, Item> dischargingMap = new HashMap<Item, Item>();
	private final Map<Item, Integer> powerList = new HashMap<Item, Integer>();
			
	/**
	 * based power outputs based off of power infuser
	 */
	public DischargerRecipes() {
		addRecipe(ItemInit.SHARD_VOID_INFUSED, ItemInit.SHARD_VOID, 360000);
		addRecipe(ItemInit.INGOT_VOID_INFUSED, ItemInit.INGOT_VOID, 600000);
		addRecipe(ItemInit.INGOT_REFINED_VOID_INFUSED, ItemInit.INGOT_REFINED_VOID, 1200000);
		addRecipe(ItemInit.STAR_VOID, ItemInit.STAR_VOID_OFF, 2400000);
	}
	
	public static DischargerRecipes getInstance() {
		return INSTANCE;
	}
	
	private void addRecipe(Item input, Item output, int totalPowerOutput) {
		dischargingMap.put(input, output);
		powerList.put(input, totalPowerOutput);
	}
	
	public ItemStack getRecipeResult(ItemStack input) {
		if (input == null || input.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		Item item = input.getItem();
		if (dischargingMap.containsKey(item)) {
			return new ItemStack(dischargingMap.get(item));
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	public int getPowerProduction(ItemStack input) {
		if (input == null|| input.isEmpty()) {
			return 1;
		}
		
		Item item = input.getItem();
		if (powerList.containsKey(item)) {
			int power = powerList.get(item);
			return Math.round((power * (PollutionPlusConfig.Machines.discharger.extractionEfficiency * 0.01F)));
		}
		return 1;
	}
	
	public Map<Item, Item> getRecipes() {
		return dischargingMap;
	}
	
	public Map<Item, Integer> getPowerRecipes() {
		return powerList;
	}
}
