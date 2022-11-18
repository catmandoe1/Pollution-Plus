package s11.mod.objects.items.unique;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
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
		//setTranslationKey(TextFormatting.LIGHT_PURPLE + name);
	}
	
	@Override
	public Item setMaxStackSize(int maxStackSize) {
		return super.setMaxStackSize(1);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.EPIC;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isCrtlDown()) {
			tooltip.add(I18n.format("item.void_star.tooltip1"));
		} else {
			tooltip.add(TextFormatting.GOLD + I18n.format("item.void_star.tooltip2"));
			tooltip.add(TextFormatting.RED + I18n.format("global.ctrl_help"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
