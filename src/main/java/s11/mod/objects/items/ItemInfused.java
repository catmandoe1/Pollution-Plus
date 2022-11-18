package s11.mod.objects.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemInfused extends ItemBase {

	public ItemInfused(String name) {
		super(name);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}
}
