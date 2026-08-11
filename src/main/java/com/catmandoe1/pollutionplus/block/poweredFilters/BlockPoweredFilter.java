package com.catmandoe1.pollutionplus.block.poweredFilters;

import com.endertech.minecraft.forge.blocks.ISmokeContainer;
import com.endertech.minecraft.forge.configs.ColorARGB;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public abstract class BlockPoweredFilter extends Block implements EntityBlock, ISmokeContainer {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	// thanks quark
	private static final VoxelShape SHAPE_NORTH = Block.box(0F, 0F, 0F, 2F, 16F, 16F);
	private static final VoxelShape SHAPE_SOUTH = Block.box(14F, 0F, 0F, 16F, 16F, 16F);
	private static final VoxelShape SHAPE_EAST = Block.box(0F, 0F, 0F, 16F, 16F, 2F);
	private static final VoxelShape SHAPE_WEST = Block.box(0F, 0F, 14F, 16F, 16F, 16F);
	private static final VoxelShape SHAPE = Shapes.or(SHAPE_NORTH, SHAPE_SOUTH, SHAPE_EAST, SHAPE_WEST);

	public BlockPoweredFilter() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(2.0F, 6).sound(SoundType.METAL).noOcclusion());

		// fix default blockstate
		this.registerDefaultState(this.getStateDefinition().any().setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(POWERED);
	}

	@Nullable
	@Override
	public abstract BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);
//		return new TileEntityPoweredFilter(blockPos, blockState);
//	}

	protected abstract BlockEntityType<? extends BlockEntity> getTileEntityType();
	protected abstract <T extends BlockEntity> BlockEntityTicker<T> getTileEntityTicker();
	protected abstract int getFilterSpeed();
	protected abstract int getFilterPowerUse();

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		if (!pLevel.isClientSide && pBlockEntityType == this.getTileEntityType()) {
			return this.getTileEntityTicker();
		}
		return null;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
		if (Screen.hasControlDown()) {
//			String line1 = I18n.get("tooltip.pollutionplus.powered_filter.line1");
//			pTooltip.add(Component.literal(line1));

			String line2 = I18n.get("tooltip.pollutionplus.powered_filter.line1", this.getFilterSpeed());
			pTooltip.add(Component.literal(ChatFormatting.AQUA + line2));

			String line3 = I18n.get("tooltip.pollutionplus.powered_filter.line2", this.getFilterPowerUse());
			pTooltip.add(Component.literal(ChatFormatting.AQUA + line3));
		} else {
			pTooltip.add(Component.literal(ChatFormatting.GRAY + I18n.get("tooltip.pollutionplus.hold_ctrl")));
		}
	}


	@Override
	public ColorARGB getColor() {
		return ColorARGB.DEFAULT; // not used anyway
	}

	@Override
	public Type getType() {
		return Type.CHIMNEY;
	}

	@Override
	public boolean isActive(BlockGetter blockGetter, BlockPos blockPos) {
		return true; // yes
	}

	@Override
	public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
		return true;
	}

	// makes it so you can pass through it
	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		return true;
	}
}
