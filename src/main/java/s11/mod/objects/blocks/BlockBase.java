package s11.mod.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import s11.mod.Main;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;
import s11.mod.util.interfaces.HasModel;

public class BlockBase extends Block implements HasModel {

	/**
	 * 
	 * @param name
	 * @param material
	 * @param hardness
	 * @param resistance
	 * @param harvestTool
	 * @param harvestLevel Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 */
	public BlockBase(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(material);
		//setRegistryName(name);
		setTranslationKey(name);
		setRegistryName(name);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel(harvestTool, harvestLevel);

		setCreativeTab(Main.pollutionplustab);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
