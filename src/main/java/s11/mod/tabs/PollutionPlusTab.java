package s11.mod.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import s11.mod.init.BlockInit;

public class PollutionPlusTab extends CreativeTabs	{

	public PollutionPlusTab(String label) {super("pollutionplustab");
	this.setBackgroundImageName("pollutionplustab.png");}
	public ItemStack getTabIconItem() {return new ItemStack(BlockInit.TILE_VOID_POWERED_FILTER);}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(BlockInit.TILE_VOID_POWERED_FILTER);
	}
	
}
