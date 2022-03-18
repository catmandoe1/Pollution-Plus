package s11.mod.objects.items;

import net.minecraft.item.Item;
import s11.mod.Main;
import s11.mod.init.ItemInit;
import s11.mod.util.interfaces.IHasModel;

public class ItemBase extends Item implements IHasModel {
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.pollutionplustab);
		
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
