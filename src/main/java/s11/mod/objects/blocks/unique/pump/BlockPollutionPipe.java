package s11.mod.objects.blocks.unique.pump;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import s11.mod.Main;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.tileEntities.TilePollutionPump;

public class BlockPollutionPipe extends BlockBase {
	public static final PropertyBool FULL = PropertyBool.create("full");
	
	private static final AxisAlignedBB SHAPE_FULL = new AxisAlignedBB(4d / 16d, 0, 4d / 16d, 12d / 16d, 16d / 16d, 12d / 16d);
	private static final AxisAlignedBB SHAPE_HALF = new AxisAlignedBB(4d / 16d, 0, 4d / 16d, 12d / 16d, 8d / 16d, 12d / 16d);
	
	// no viable (simple) solution for this in 1.12.2
//	private static final List<AxisAlignedBB> SHAPE_FULL = new ArrayList<AxisAlignedBB>(Arrays.asList(
//		new AxisAlignedBB(5d / 16d, 0, 5d / 16d, 11d / 16d, 16d / 16d, 11d/ 16d), //	centre post
//		new AxisAlignedBB(6d/ 16d, 0, 4d / 16d, 10d / 16d, 16d / 16d, 5d / 16d), // 	north side
//		new AxisAlignedBB(11d / 16d, 0, 6d / 16d, 12d / 16d, 16d / 16d, 10d / 16d), //	right side
//		new AxisAlignedBB(6d / 16d, 0, 11d / 16d, 10d / 16d, 16d / 16d, 12d / 16d), //	south side
//		new AxisAlignedBB(4d / 16d, 0, 6d / 16d, 5d / 16d, 16d / 16d, 10d / 16d) // 	left side
//		
//	));

	public BlockPollutionPipe(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, hardness, resistance, harvestTool, harvestLevel);
		setDefaultState(this.blockState.getBaseState().withProperty(this.FULL, true));
		setLightOpacity(0);
	}
	
	// for some reason full cube is different from full block with full block causing some magical force
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}
	
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(FULL) ? 1 : 0;
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FULL, (meta & 1) != 0);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FULL);
	}
	
//	@Override
//	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
//		// return full shape if the block is full
//		return blockState.getValue(FULL) ? SHAPE_FULL : SHAPE_HALF;
//	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getActualState(state, source, pos).getValue(FULL) ? SHAPE_FULL : SHAPE_HALF;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		Block above = worldIn.getBlockState(pos.up()).getBlock();
		Block below = worldIn.getBlockState(pos.down()).getBlock();
		
		boolean isFull = this.willPipeBeFull(above, below);
		return getDefaultState().withProperty(FULL, isFull);
	}
	
	private boolean willPipeBeFull(Block above, Block below) {
		return above instanceof BlockPollutionPipe || !(below instanceof BlockPollutionPipe || below instanceof BlockPollutionPump);
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	// from carpet block
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canBlockStay(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}
	
	// from carpet block
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}
	
	
	private boolean canBlockStay(World world, BlockPos pos) {
		IBlockState below = world.getBlockState(pos.down());
		return !world.isAirBlock(pos.down()) && (below.isFullBlock() || below.getBlock() instanceof BlockPollutionPipe);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isRemote) {
			return;
		}
		
		this.updatePump(worldIn, pos);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (worldIn.isRemote) {
			return;
		}
		
		this.updatePump(worldIn, pos);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack held = playerIn.getHeldItem(hand);
		
		if (held.getItem() == Item.getItemFromBlock(this)) {
			BlockPos top = findTop(worldIn, pos);
			if (top == null) {
				top = pos;
			}
			
			// the block 2 blocks above the top
			Block aboveBlock = worldIn.getBlockState(top.up(2)).getBlock();
			Block belowBlock = worldIn.getBlockState(top).getBlock();
			
			if (worldIn.isAirBlock(top.up()) && worldIn.setBlockState(top.up(), this.getDefaultState().withProperty(FULL, willPipeBeFull(aboveBlock, belowBlock)), 2)) {
				if (!worldIn.isRemote) {
					worldIn.playSound(playerIn, pos, getSoundType(state, worldIn, pos, playerIn).getPlaceSound(), SoundCategory.BLOCKS, 1, 1.2f);
					
					if (!playerIn.isCreative()) {
						held.shrink(1);
					}					
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * fires onPipeChanged() on the connected pollution pump at the bottom of the chain (if theres one)
	 * @param world
	 * @param pos
	 */
	private void updatePump(World world, BlockPos pos) {
		BlockPos pumpPos = findBottom(world, pos);
		if (pumpPos == null) {
			pumpPos = pos;
		}
		pumpPos = pumpPos.down();

		TileEntity tileEntity = world.getTileEntity(pumpPos);
		if (tileEntity != null && tileEntity instanceof TilePollutionPump) {
			((TilePollutionPump)tileEntity).onPipeChanged();
		}
	}
	
	@Nullable
	public static BlockPos findTop(World world, BlockPos pos) {
		BlockPos top = null;

		for (int y = pos.getY() + 1; y < world.getHeight(); y++) {
			BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
			Block block = world.getBlockState(checkPos).getBlock();

			if (block instanceof BlockPollutionPipe) {
				top = checkPos;
			} else {
				return top;
			}
		}
		return top;
	}

	@Nullable
	public static BlockPos findBottom(World world, BlockPos pos) {
		BlockPos bottom = null;

		for (int y = pos.getY() - 1; y > 0; y--) {
			BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
			Block block = world.getBlockState(checkPos).getBlock();

			if (block instanceof BlockPollutionPipe) {
				bottom = checkPos;
			} else {
				return bottom;
			}
		}

		return bottom;
	}
}
