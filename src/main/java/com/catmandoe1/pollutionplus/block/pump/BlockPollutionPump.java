package com.catmandoe1.pollutionplus.block.pump;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.tileentities.TileEntityIncinerator;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionPump;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockPollutionPump extends HorizontalDirectionalBlock implements EntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public BlockPollutionPump() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(2.0F, 6).sound(SoundType.METAL));
		registerDefaultState(this.getStateDefinition().any().setValue(POWERED, false));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TileEntityPollutionPump(blockPos, blockState);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, POWERED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		Direction direction = pContext.getHorizontalDirection().getOpposite();
		return this.defaultBlockState().setValue(FACING, direction).setValue(POWERED, false);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		// if server side and correct tile entity
		if (!pLevel.isClientSide && pBlockEntityType == PPBlocks.POLLUTION_PUMP.getTileEntityType()) {
			return TileEntityPollutionPump::update;
		}
		return null;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
		if (Screen.hasControlDown()) {
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.pollution_pump.line1", Config.pollutionPumpRange)));
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.pollution_pump.line2", Config.pollutionPumpPowerUse)));
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.pollution_pump.line3", Config.pollutionPumpWorkSpeed)));

		} else {
			pTooltip.add(Component.literal(ChatFormatting.GRAY + I18n.get("tooltip.pollutionplus.hold_ctrl")));
		}
	}
}
