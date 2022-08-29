package s11.mod.objects.blocks.unique;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.objects.blocks.BlockBase;

public class VoidOre extends BlockBase {

	public VoidOre(String name, Material material) {
		super(name, material);
		this.setHardness(3.0F);
		this.setResistance(15.0F);
		this.setHarvestLevel("pickaxe", 3);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GOLD + "Sparingly found around Y5 - Y30");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
