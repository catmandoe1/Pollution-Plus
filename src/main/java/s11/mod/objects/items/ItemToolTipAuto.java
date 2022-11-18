package s11.mod.objects.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.util.TextHelper;

public class ItemToolTipAuto extends ItemBase {
	
	String name;

	public ItemToolTipAuto(String name) {
		super(name);
		this.name = name;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GOLD + TextHelper.itemToolTipGetLang(name));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}

