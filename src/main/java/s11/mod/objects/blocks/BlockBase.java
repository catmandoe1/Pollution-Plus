package s11.mod.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import s11.mod.Main;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;
import s11.mod.util.interfaces.IHasModel;

public class BlockBase extends Block implements IHasModel {

	public BlockBase(String name, Material material) {
		super(material);
		//setRegistryName(name);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(Main.pollutionplustab);
//		if(name == "ore_void") {
//			   this.setHardness(3.0F);
//			   this.setResistance(15.0F);
//			   this.setHarvestLevel("pickaxe", 3);
//		}
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
