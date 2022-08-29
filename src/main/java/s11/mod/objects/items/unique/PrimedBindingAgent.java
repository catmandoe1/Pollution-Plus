package s11.mod.objects.items.unique;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.objects.items.ItemBase;

public class PrimedBindingAgent extends ItemBase {

	public PrimedBindingAgent(String name) {
		super(name);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GOLD + "Super sticky mixture");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
