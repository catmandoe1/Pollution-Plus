package s11.mod.objects.blocks.unique.powered_filters;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import s11.mod.Main;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.tileEntities.powered_filters.TileDiamondPoweredFilter;
import s11.mod.util.PlayerPressing;
import s11.mod.util.TextHelper;

public class BlockDiamondPoweredFilter extends BlockBase {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockDiamondPoweredFilter(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, resistance, resistance, harvestTool, harvestLevel);
//		this.setHardness(6.0F);
//		this.setResistance(15.0F);
//		setHarvestLevel("pickaxe", 3); // iron pickaxe level
		setDefaultState(blockState.getBaseState().withProperty(ACTIVE, false));
//		setCreativeTab(Main.pollutionplustab);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isLeftCrtlDown()) {
			tooltip.add(TextHelper.getLang("powered_filters.tooltip"));
			tooltip.add("");
			tooltip.add(TextFormatting.DARK_PURPLE + TextHelper.getLang("global.disc_stats"));
			tooltip.add(TextFormatting.BLUE + "" + PollutionPlusConfig.PoweredFilters.diamond.filterPowerUse + " RF/t");
			tooltip.add(TextFormatting.BLUE + "" + PollutionPlusConfig.PoweredFilters.diamond.filterSpeed + " " + TextHelper.getLang("global.cooldown"));
		} else {
			tooltip.add(TextFormatting.RED + I18n.format("global.ctrl_help"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(ACTIVE) ? 1 : 0;
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, (meta & 1) != 0);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		// TODO Auto-generated method stub
		return new TileDiamondPoweredFilter();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
}
