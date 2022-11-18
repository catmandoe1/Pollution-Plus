package s11.mod.objects.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.deprecated;

/**
 * @deprecated
 * use {@link ItemToolTipAuto} as it is easier
 */
public class ItemToolTip extends ItemBase {
	
	String toolTip;

	/**
	 * 
	 * @param name
	 * @param toolTip the lang file code
	 */
	public ItemToolTip(String name, String toolTip) {
		super(name);
		this.toolTip = toolTip;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GOLD + I18n.format(toolTip));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
