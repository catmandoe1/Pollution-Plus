package s11.mod.objects.blocks.unique;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
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
import s11.mod.objects.tileEntities.TilePollutionDeleter;
import s11.mod.util.PlayerPressing;
import s11.mod.util.Reference;

public class BlockPollutionDeleter extends BlockBase {
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockPollutionDeleter(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, hardness, resistance, harvestTool, harvestLevel);
		setDefaultState(this.blockState.getBaseState().withProperty(this.POWERED, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED);
	}
		
	@Override
	public int getMetaFromState(IBlockState state) {
		int i= state.getValue(POWERED) ? 1 : 0;
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, (meta & 1) != 0);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TilePollutionDeleter();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(Reference.MODID, Reference.GuiIds.POLLUTION_DELETER.getIndex(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isCrtlDown()) {
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.pollution_deleter.line1", PollutionPlusConfig.Machines.pollutionDeleter.horizontalLimit));
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.pollution_deleter.line2", PollutionPlusConfig.Machines.pollutionDeleter.operationCost));
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.pollution_deleter.line3", PollutionPlusConfig.Machines.pollutionDeleter.workSpeed));

		} else {
			tooltip.add(TextFormatting.GRAY + I18n.format("global.ctrl_help"));
		}
	}
}
