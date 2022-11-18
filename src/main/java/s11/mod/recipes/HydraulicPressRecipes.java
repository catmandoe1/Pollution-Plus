package s11.mod.recipes;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import s11.mod.init.ItemInit;

public class HydraulicPressRecipes {
	private static final HydraulicPressRecipes INSTANCE = new HydraulicPressRecipes();
	private final Map<Item, ItemStack> crushingList = new HashMap<Item, ItemStack>();
	
	public static HydraulicPressRecipes getInstance() {
		return INSTANCE;
	}
	
	private HydraulicPressRecipes() {
		addRecipe(Item.getItemFromBlock(Blocks.STONE), Item.getItemFromBlock(Blocks.COBBLESTONE));
		addRecipe(Item.getItemFromBlock(Blocks.COBBLESTONE), Item.getItemFromBlock(Blocks.GRAVEL));
		addRecipe(Item.getItemFromBlock(Blocks.GRAVEL), Item.getItemFromBlock(Blocks.SAND));
		//addRecipe(Item.getItemFromBlock(Blocks.IRON_BLOCK), Items.IRON_INGOT, 9);
		oreDictAddRecipe(OreDictionary.getOres("ingotIron"), ItemInit.DUST_IRON);
		oreDictAddRecipe(OreDictionary.getOres("ingotGold"), ItemInit.DUST_GOLD);
		addRecipe(Items.DIAMOND, ItemInit.SPECK_DIAMOND, 8);
	}
	
	public void oreDictAddRecipe(NonNullList<ItemStack> input, Item output) {
		if (!input.isEmpty()) {
			input.forEach(is -> {
				addRecipe(is.getItem(), output);
			});
//			for (int i = 0; i <= input.size(); i = i + 1) {
//				//addRecipe(input.get(i).getItem(), output);
//				System.out.println(input.get(i).getItem());
//			}
		}
	}
	
	public void addRecipe(Item input, Item output) {
		addRecipe(input, output, 1);
	}
	
	public void addRecipe(Item input, Item output, int outputAmount) {
		crushingList.put(input, new ItemStack(output, outputAmount));
	}
	
	public ItemStack getRecipeResult(ItemStack input) {
		if (input.isEmpty()) {
			return ItemStack.EMPTY;
		}
		
		Item item = input.getItem();
		if (crushingList.containsKey(item)) {
			return crushingList.get(item);
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	public Map<Item, ItemStack> getRecipes() {
		return crushingList;
	}
}
