package s11.mod.objects.blocks.unique.pump;

import java.util.List;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.tileEntities.TilePollutionPump;
import s11.mod.util.PlayerPressing;

public class BlockPollutionPump extends BlockBase {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockPollutionPump(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, hardness, resistance, harvestTool, harvestLevel);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() | (state.getValue(ACTIVE) ? 4 : 0);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3)).withProperty(ACTIVE, (meta & 4) > 0);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ACTIVE);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		EnumFacing direction = placer.getHorizontalFacing();
		if (!placer.isSneaking()) {
			direction = direction.getOpposite(); //spins front towards player when not sneaking
		}
		return getDefaultState().withProperty(FACING, direction);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TilePollutionPump();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isLeftCrtlDown()) {
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.pollution_pump.line1", PollutionPlusConfig.Machines.pollutionPump.workRange));
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.pollution_pump.line2", PollutionPlusConfig.Machines.pollutionPump.operationCost));
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.pollution_pump.line3", PollutionPlusConfig.Machines.pollutionPump.workSpeed));
		} else {
			tooltip.add(TextFormatting.GRAY + I18n.format("global.ctrl_help"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
