package s11.mod.objects.blocks.unique;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.Main;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.tileEntities.machines.TileHydraulicPress;
import s11.mod.util.PlayerPressing;
import s11.mod.util.Reference;

public class BlockHydraulicPress extends BlockBase {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockHydraulicPress(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, resistance, resistance, harvestTool, harvestLevel);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isCrtlDown()) {
			tooltip.add(I18n.format("tile.tile_hydraulic_press.tooltip"));;
		} else {
			tooltip.add(TextFormatting.RED + I18n.format("global.ctrl_help"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
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
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileHydraulicPress();
	}
	
	//drops items in the the inventory
		@Override
		public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
			TileHydraulicPress te = (TileHydraulicPress)worldIn.getTileEntity(pos);
			
			for (int i = 0; i < te.getInventoryHandler().getSlots(); i++) {
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), te.getInventoryHandler().getStackInSlot(i));
			}
			super.breakBlock(worldIn, pos, state);
		}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(Main.instance, Reference.GuiIds.HYDRAULIC_PRESS.getIndex(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
	
		return true;
	}
}
