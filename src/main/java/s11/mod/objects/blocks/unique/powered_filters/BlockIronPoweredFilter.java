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

public class BlockIronPoweredFilter extends BlockBase {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockIronPoweredFilter(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, resistance, resistance, harvestTool, harvestLevel);
//		this.setHardness(3.0F);
//		this.setResistance(10.0F);
//		this.setHarvestLevel("pickaxe", 2); // iron pickaxe level
		this.setDefaultState(blockState.getBaseState().withProperty(ACTIVE, false));
//		this.setCreativeTab(Main.pollutionplustab);
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
		return new TileIronPoweredFilter();
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
//		
//	public int fill(Filter.BlockTile storage, Pollutant<?> pollutant, int amount) {
//	    if (storage instanceof TileIronPoweredFilter) {
//	    	TileIronPoweredFilter filter = (TileIronPoweredFilter)storage;
//	      return filter.active ? amount : 0;
//	    } 
//	    return 0;
//	}
//
//
//
//	@Override
//	public UnitId getRelatedId() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//	@Override
//	public ForgeConfig getConfig() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//	@Override
//	public ColorARGB getColor() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//	@Override
//	public boolean isActive(World arg0, BlockPos arg1) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//
//
//	@Override
//	public boolean isChimney(IBlockState arg0) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//
//
//	@Override
//	public boolean isPump(IBlockState arg0) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//
//
//	@Override
//	public boolean isVent(IBlockState arg0) {
//		// TODO Auto-generated method stub
//		return true;
//	}
}
