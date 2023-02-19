package s11.mod.objects.items;

import net.minecraft.item.Item;
import s11.mod.Main;
import s11.mod.init.ItemInit;
import s11.mod.util.interfaces.HasModel;

public class ItemBase extends Item implements HasModel {
	public ItemBase(String name) {
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(Main.pollutionplustab);
		
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
