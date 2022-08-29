package s11.mod.objects.items.unique;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.Main;
import s11.mod.objects.items.ItemBase;
import s11.mod.util.PlayerPressing;

public class VoidStar extends ItemBase{

	public VoidStar(String name) {
		super(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isCrtlDown()) {
			tooltip.add("Infinite filter config has to be changed to this item's id for it to be used as the activator");
		} else {
			tooltip.add(TextFormatting.GOLD + "Can be used as an alternative for activating an infinite filter");
			tooltip.add(TextFormatting.RED + "Hold Ctrl for help");
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
