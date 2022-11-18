package s11.mod.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import s11.mod.init.ItemInit;
import s11.mod.integration.jei.alloyfurnace.AlloyFurnaceRecipeWrapper;

public class AlloyFurnaceRecipes {
	private static final AlloyFurnaceRecipes INSTANCE = new AlloyFurnaceRecipes();
	//private final Table<Item, Item, Item> alloyList = HashBasedTable.<Item, Item, Item>create();
	private final Table<ItemStack, ItemStack, ItemStack> alloyList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	//private final Table<Item, Item, Item> JEIAlloyList = HashBasedTable.<Item, Item, Item>create();
	private final Table<ItemStack, ItemStack, ItemStack> JEIAlloyList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	
	public static AlloyFurnaceRecipes getInstance() {
		return INSTANCE;
	}
	
	private AlloyFurnaceRecipes() {
		//general
		addRecipe(Items.APPLE, 1, Items.GOLD_INGOT, 6,  Items.GOLDEN_APPLE, 1);
		//addRecipe(Items.COAL, Items.IRON_INGOT, ItemInit.INGOT_STEEL);
		addRecipe(new ItemStack(Items.COAL), new ItemStack(Items.IRON_INGOT, 2), new ItemStack(ItemInit.INGOT_STEEL, 2));
		
		
//		oreDictAddRecipe(OreDictionary.getOres("ingotCopper"), 3, OreDictionary.getOres("ingotTin"), 1, OreDictionary.getOres("ingotBronze"), 4);
//		oreDictAddRecipe(OreDictionary.getOres("ingotCopper"), 1, OreDictionary.getOres("ingotZinc"), 1, OreDictionary.getOres("ingotBrass"), 4);
//		oreDictAddRecipe(OreDictionary.getOres("ingotCopper"), 1, OreDictionary.getOres("ingotNickel"), 1, OreDictionary.getOres("ingotConstantan"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotGold"), 1, OreDictionary.getOres("ingotSilver"), 1, OreDictionary.getOres("ingotElectrum"), 2);
//		
//		//lucraft core
//		oreDictAddRecipe(OreDictionary.getOres("ingotIron"), 2, OreDictionary.getOres("ingotOsmium"), 1, OreDictionary.getOres("ingotIntertium"), 3);
//		oreDictAddRecipe(OreDictionary.getOres("ingotSteel"), 2, OreDictionary.getOres("ingotVibranium"), 1, OreDictionary.getOres("ingotAdamantium"), 3);
//		oreDictAddRecipe(OreDictionary.getOres("ingotGold"), 1, OreDictionary.getOres("ingotTitanium"), 2, OreDictionary.getOres("ingotGoldTitaniumAlloy"), 3);
//		
//		//nuclear craft
//		oreDictAddRecipe(OreDictionary.getOres("ingotSteel"), 1, OreDictionary.getOres("ingotBoron"), 1, OreDictionary.getOres("ingotFerroboron"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotFerroboron"), 1, OreDictionary.getOres("ingotLithium"), 1, OreDictionary.getOres("ingotTough"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotGraphite"), 1, OreDictionary.getOres("gemDiamond"), 1, OreDictionary.getOres("ingotHardCarbon"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotMagnesium"), 1, OreDictionary.getOres("ingotBoron"), 2, OreDictionary.getOres("ingotMagnesiumDiboride"), 3);
//		oreDictAddRecipe(OreDictionary.getOres("ingotLithium"), 1, OreDictionary.getOres("ingotMagnesiumDioxide"), 1, OreDictionary.getOres("ingotLithiumManganeseDioxide"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotCopper"), 3, OreDictionary.getOres("ingotSilver"), 1, OreDictionary.getOres("ingotShibuichi"), 4);
//		oreDictAddRecipe(OreDictionary.getOres("ingotTin"), 3, OreDictionary.getOres("ingotSilver"), 1, OreDictionary.getOres("ingotTinSilver"), 4);
//		oreDictAddRecipe(OreDictionary.getOres("ingotLead"), 3, OreDictionary.getOres("ingotPlatinum"), 1, OreDictionary.getOres("ingotLeadPlatinum"), 4);
//		oreDictAddRecipe(OreDictionary.getOres("ingotTough"), 1, OreDictionary.getOres("ingotHardCarbon"), 1, OreDictionary.getOres("ingotExtreme"), 1);
//		oreDictAddRecipe(OreDictionary.getOres("ingotExtreme"), 1, OreDictionary.getOres("gemBoronArsenide"), 1, OreDictionary.getOres("ingotThermoconducting"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotZirconium"), 7, OreDictionary.getOres("ingotTin"), 1, OreDictionary.getOres("ingotTinSilver"), 8);
//		oreDictAddRecipe(OreDictionary.getOres("itemSilicon"), 1, OreDictionary.getOres("ingotGraphite"), 1, OreDictionary.getOres("ingotSiliconCarbide"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotSilicon"), 1, OreDictionary.getOres("ingotGraphite"), 1, OreDictionary.getOres("ingotSiliconCarbide"), 2);
//		oreDictAddRecipe(OreDictionary.getOres("ingotIron"), 15, OreDictionary.getOres("dustCarbonManganese"), 1, OreDictionary.getOres("ingotHSLASteel"), 16);
	}
	
	public void oreDictAddRecipe(NonNullList<ItemStack> input, NonNullList<ItemStack> input2, ItemStack output) {
		input.forEach(is -> {
			input2.forEach(is2 -> {
				addRecipe(is, is2, output);
			});
		});
	}
	
	public void oreDictAddRecipe(NonNullList<ItemStack> input, int inputAmount, NonNullList<ItemStack> input2, int input2Amount, NonNullList<ItemStack> output, int outputAmount) {
		if (input == null || input2 == null || output == null) {
			return;
		}
		input.forEach(is -> {
			input2.forEach(is2 -> {
				addRecipe(is.getItem(), inputAmount, is2.getItem(), input2Amount, output.get(0).getItem(), outputAmount);
			});
		});
	}
	
	public void addRecipe(Item input, Item input2, Item output) {
		alloyList.put(new ItemStack(input), new ItemStack(input2), new ItemStack(output));
		alloyList.put(new ItemStack(input2), new ItemStack(input), new ItemStack(output));
		JEIAlloyList.put(new ItemStack(input), new ItemStack(input2), new ItemStack(output));
	}
	
	public void addRecipe(Item input, int in1Amount, Item input2, int in2Amount, Item output, int outAmount) {
		alloyList.put(new ItemStack(input, in1Amount), new ItemStack(input2, in2Amount), new ItemStack(output, outAmount));
		alloyList.put(new ItemStack(input2, in2Amount), new ItemStack(input, in1Amount), new ItemStack(output, outAmount));
		JEIAlloyList.put(new ItemStack(input, in1Amount), new ItemStack(input2, in2Amount), new ItemStack(output, outAmount));
	}
	
	public void addRecipe(ItemStack input, ItemStack input2, ItemStack output) {
		alloyList.put(input, input2, output);
		alloyList.put(input2, input, output);
		JEIAlloyList.put(input, input2, output);
	}
	
	private boolean compareItemStacks(ItemStack inStack, ItemStack recipeStack) {
//		return recipeStack.getItem() == inStack.getItem() && (recipeStack.getMetadata() == 32767 || recipeStack.getMetadata() == inStack.getMetadata()) 
//				&& inStack.getCount() >= recipeStack.getCount();
		return recipeStack.getItem() == inStack.getItem() && inStack.getCount() >= recipeStack.getCount();
	}
	
	public ItemStack getRecipeResult(ItemStack input, ItemStack input2) {
		List<ItemStack> result = getFullRecipeResult(input, input2);
		return result.get(2);
	}
	
	public List<ItemStack> getFullRecipeResult(ItemStack input, ItemStack input2) {
		List<ItemStack> result = new ArrayList<ItemStack>();
		if (input.isEmpty() || input2.isEmpty()) {
			result.add(ItemStack.EMPTY);
			result.add(ItemStack.EMPTY);
			result.add(ItemStack.EMPTY);
			return result;
		}
	
		//Item input1Item = input.getItem();
		//Item input2Item = input2.getItem();
		
		Map<ItemStack, Map<ItemStack, ItemStack>> map = alloyList.rowMap();
		
		for (Map.Entry<ItemStack, Map<ItemStack, ItemStack>> outer : map.entrySet()) {
			if(this.compareItemStacks(input, (ItemStack)outer.getKey())) {
			    for (Map.Entry<ItemStack, ItemStack> inner : outer.getValue().entrySet()) {
			    	if(this.compareItemStacks(input2, (ItemStack)inner.getKey())) {
			    		result.add(outer.getKey().copy());
			    		result.add(inner.getKey().copy());
			    		result.add(inner.getValue().copy());
			    		return result;
			    	}
			    }				
			}

		}
		
//		if (alloyList.contains(input, input2)) {
//			System.out.println("first");
//			return alloyList.get(input, input2).copy();
//		} else if (alloyList.contains(input2, input) ) { // just in case if recipe backwards
//			System.out.println("second");
//			return alloyList.get(input2, input).copy();
//		} else {
//			System.out.println(input);
//			System.out.println(input2);
//			System.out.println(alloyList);
//			System.out.println(alloyList.column(input));
//			return ItemStack.EMPTY;
//		}
		result.add(ItemStack.EMPTY);
		result.add(ItemStack.EMPTY);
		result.add(ItemStack.EMPTY);
		return result;
	}
	
	/**
	 * @deprecated
	 * no point in using this now
	 * @param input
	 * @param input2
	 * @return
	 */
	public ItemStack getRecipeResultDouble(ItemStack input, ItemStack input2) {
		return new ItemStack(getRecipeResult(input, input2).getItem(), 2);
	}
	
	public boolean canTransferToSlot(ItemStack input) {
		int input1 = 1;
		int input2 = 2;
		Item inputItem = input.getItem();
		
		Map<ItemStack, Map<ItemStack, ItemStack>> map = alloyList.rowMap();
		
		for (Map.Entry<ItemStack, Map<ItemStack, ItemStack>> outer : map.entrySet()) {
			if ((Item)outer.getKey().getItem() == inputItem) {
				return true;
			}
		    for (Map.Entry<ItemStack, ItemStack> inner : outer.getValue().entrySet()) {
		    	if ((Item)inner.getKey().getItem() == inputItem) {
		    		return true;
		    	}
		        //recipes.add(new AlloyFurnaceRecipeWrapper(outer.getKey(), inner.getKey(), inner.getValue()));
		    }
		}
		return false;
		
//		Item inputItem = input.getItem();
//		if (alloyList.containsColumn(inputItem)) {
//			return true;
//		} else if (alloyList.containsRow(inputItem)) {
//			return true;
//		} else {
//			return false;
//		}
	}
	
	/**
	 * @deprecated return type is wrong
	 * @return
	 */
	public Table<Item, Item, Item> getRecipes() {
		//return alloyList;
		return null;
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getRecipesItemStack() {
		return alloyList;
	}
	
	/**
	 * @deprecated
	 * doesnt contain the reverse recipe that is automatically added to the original table
	 * @return
	 */
	public Table<Item, Item, Item> getRecipesJEI() {
		//return JEIAlloyList;
		return null;
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getRecipesJeiItemStack() {
		return JEIAlloyList;
	}
}
