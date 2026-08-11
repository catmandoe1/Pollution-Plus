package com.catmandoe1.pollutionplus.block.pump;

import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionPump;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;


public class BlockPollutionPipe extends Block {
	public static final EnumProperty<PipeSize> PIPE_SIZE = EnumProperty.create("size", PipeSize.class);
	private static final VoxelShape CENTRE_POST_FULL = Block.box(5, 0, 5, 11, 16, 11);
	private static final VoxelShape SIDE_NORTH = Block.box(6, 0, 4, 10, 16, 5);
	private static final VoxelShape SIDE_RIGHT = Block.box(11, 0, 6, 12, 16, 10);
	private static final VoxelShape SIDE_SOUTH = Block.box(6, 0, 11, 10, 16, 12);
	private static final VoxelShape SIDE_LEFT = Block.box(4, 0, 6, 5, 16, 10);
	private static final VoxelShape SHAPE_FULL = Shapes.or(CENTRE_POST_FULL, SIDE_NORTH, SIDE_RIGHT, SIDE_SOUTH, SIDE_LEFT);

	private static final VoxelShape CENTRE_POST_HALF = Block.box(5, 0, 5, 11, 8, 11);
	private static final VoxelShape SIDE_NORTH_HALF = Block.box(6, 0, 4, 10, 8, 5);
	private static final VoxelShape SIDE_RIGHT_HALF = Block.box(11, 0, 6, 12, 8, 10);
	private static final VoxelShape SIDE_SOUTH_HALF = Block.box(6, 0, 11, 10, 8, 12);
	private static final VoxelShape SIDE_LEFT_HALF = Block.box(4, 0, 6, 5, 8, 10);
	private static final VoxelShape SHAPE_HALF = Shapes.or(CENTRE_POST_HALF, SIDE_NORTH_HALF, SIDE_RIGHT_HALF, SIDE_SOUTH_HALF, SIDE_LEFT_HALF);

	public BlockPollutionPipe() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(2.0F, 6).sound(SoundType.METAL).noOcclusion());

		this.registerDefaultState(this.getStateDefinition().any().setValue(PIPE_SIZE, PipeSize.FULL));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(PIPE_SIZE);
	}

	@Override
	public boolean isOcclusionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		return false;
	}

	public enum PipeSize implements StringRepresentable {
		FULL,
		HALF;

		@Override
		public @NotNull String getSerializedName() {
			return name().toLowerCase(Locale.ENGLISH);
		}
	}

//	@Override
//	public BlockState getAppearance(BlockState state, BlockAndTintGetter level, BlockPos pos, Direction side, @Nullable BlockState queryState, @Nullable BlockPos queryPos) {
//		Block above = level.getBlockState(pos.above()).getBlock();
//		Block below = level.getBlockState(pos.below()).getBlock();
//
//		boolean isFull = above instanceof BlockPollutionPipe && (below instanceof BlockPollutionPipe || below instanceof BlockPollutionPump);
//		return state.setValue(PIPE_SIZE, isFull ? PipeSize.FULL : PipeSize.HALF);
//	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
		Block above = pLevel.getBlockState(pCurrentPos.above()).getBlock();
		Block below = pLevel.getBlockState(pCurrentPos.below()).getBlock();

		boolean isFull = this.willPipeBeFull(above, below);
		return pState.setValue(PIPE_SIZE, isFull ? PipeSize.FULL : PipeSize.HALF);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return pState.getValue(PIPE_SIZE) == PipeSize.FULL ? SHAPE_FULL : SHAPE_HALF;
	}

	@Override
	public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		return pState.getValue(PIPE_SIZE) == PipeSize.FULL ? SHAPE_FULL : SHAPE_HALF;
	}

	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return pState.getValue(PIPE_SIZE) == PipeSize.FULL ? SHAPE_FULL : SHAPE_HALF;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		Block above = pContext.getLevel().getBlockState(pContext.getClickedPos().above()).getBlock();
		Block below = pContext.getLevel().getBlockState(pContext.getClickedPos().below()).getBlock();

		boolean isFull = this.willPipeBeFull(above, below);
		return defaultBlockState().setValue(PIPE_SIZE, isFull ? PipeSize.FULL : PipeSize.HALF);
	}

	private boolean willPipeBeFull(Block above, Block below) {
		return above instanceof BlockPollutionPipe || !(below instanceof BlockPollutionPipe || below instanceof BlockPollutionPump);
	}

	@Override
	public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
		if (pLevel.isClientSide) {
			return;
		}

		BlockPos pumpPos = findBottom(pLevel, pPos);
		if (pumpPos == null) {
			pumpPos = pPos;
		}
		pumpPos = pumpPos.below();

		BlockEntity tileEntity = pLevel.getBlockEntity(pumpPos);
		if (tileEntity != null && tileEntity instanceof TileEntityPollutionPump tileEntityPollutionPump) {
			tileEntityPollutionPump.onPipeChanged();
		}
	}

	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (pLevel.isClientSide) {
			return;
		}

		BlockPos pumpPos = findBottom(pLevel, pPos);
		boolean wasBottom = false;
		if (pumpPos == null) {
			pumpPos = pPos;
			wasBottom = true;
		}
		pumpPos = pumpPos.below();

		BlockEntity tileEntity = pLevel.getBlockEntity(pumpPos);
		if (tileEntity != null && tileEntity instanceof TileEntityPollutionPump tileEntityPollutionPump) {
			tileEntityPollutionPump.onPipeChanged();

		}

		super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
	}


	@Override
	public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
		if (!this.canSurvive(pState, pLevel, pPos)) {
			popResource(pLevel, pPos, new ItemStack(PPBlocks.POLLUTION_PIPE.getItem(), 1));
			pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
		}
		super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
	}

	@Override
	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		// exception for pipes since they cant support rigid block
		return canSupportRigidBlock(pLevel, pPos.below()) || pLevel.getBlockState(pPos.below()).getBlock() instanceof BlockPollutionPipe;
	}

	public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		return true;
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		ItemStack held = pPlayer.getMainHandItem();
		// easy pipe building

		// if block right clicked with same
		if (held.getItem() == this.asItem()) {
			BlockPos top = findTop(pLevel, pPos);
			if (top == null) {
				top = pPos;
			}

			// the block 2 blocks above the top
			Block aboveBlock = pLevel.getBlockState(top.above(2)).getBlock();
			Block belowBlock = pLevel.getBlockState(top).getBlock();

			// if block above top is air and if it was set to a pipe successfully
			if (pLevel.isEmptyBlock(top.above()) && pLevel.setBlock(top.above(), this.defaultBlockState().setValue(PIPE_SIZE, willPipeBeFull(aboveBlock, belowBlock) ? PipeSize.FULL : PipeSize.HALF), 2)) {
				pLevel.playSound(pPlayer, pPos, this.soundType.getPlaceSound(), SoundSource.BLOCKS, 1, 1.2f);

				if (!pPlayer.isCreative()) {
					held.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}

		}
		return InteractionResult.PASS;
	}

	@Nullable
	public static BlockPos findTop(Level world, BlockPos pos) {
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
	public static BlockPos findBottom(Level world, BlockPos pos) {
		BlockPos bottom = null;

		for (int y = pos.getY() - 1; y > -65; y--) {
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
