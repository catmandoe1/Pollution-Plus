package s11.mod.objects.blocks.unique;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.util.TextHelper;

public class BlockVoidOre extends BlockBase {
	
	private String toolTip;

	public BlockVoidOre(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel, String toolTip) {
		super(name, material, hardness, resistance, harvestTool, harvestLevel);
		this.toolTip = toolTip;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		if (!PollutionPlusConfig.GeneralConfig.generateVoidOre) {
			tooltip.add(TextFormatting.RED + TextHelper.getLang("global.disabled"));
		} else {			
			tooltip.add(TextFormatting.GOLD + TextHelper.getLang(toolTip));
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
