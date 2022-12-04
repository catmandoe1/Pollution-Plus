package s11.mod.objects.blocks.unique.powered_filters;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import s11.mod.Main;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.tileEntities.powered_filters.TileIronPoweredFilter;
import s11.mod.util.PlayerPressing;
import s11.mod.util.TextHelper;

public class BlockIronPoweredFilter extends BlockPoweredFilterBase {

	public BlockIronPoweredFilter(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, resistance, resistance, harvestTool, harvestLevel);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isLeftCrtlDown()) {
			tooltip.add(TextHelper.getLang("powered_filters.tooltip"));
			tooltip.add("");
			tooltip.add(TextFormatting.DARK_PURPLE + TextHelper.getLang("global.disc_stats"));
			tooltip.add(TextFormatting.BLUE + "" + PollutionPlusConfig.PoweredFilters.iron.filterPowerUse + " RF/t");
			tooltip.add(TextFormatting.BLUE + "" + PollutionPlusConfig.PoweredFilters.iron.filterSpeed + " " + TextHelper.getLang("global.cooldown"));
		} else {
			tooltip.add(TextFormatting.RED + I18n.format("global.ctrl_help"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileIronPoweredFilter();
	}
}
