package com.catmandoe1.pollutionplus.block;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.gui.screen.ScreenPollutionDeleter;
import com.catmandoe1.pollutionplus.item.ItemLocationMarker;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockPollutionDeleter extends Block implements EntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public BlockPollutionDeleter() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(2.0F, 6).sound(SoundType.METAL));
		registerDefaultState(this.getStateDefinition().any().setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(POWERED);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TileEntityPollutionDeleter(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		// if server side and correct tile entity
		if (!pLevel.isClientSide && pBlockEntityType == PPBlocks.POLLUTION_DELETER.getTileEntityType()) {
			return TileEntityPollutionDeleter::update;
		}
		return null;
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (pPlayer.getItemInHand(pHand).getItem() instanceof ItemLocationMarker) {
			return InteractionResult.PASS;
		}

		if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) {
			if (pLevel.getBlockEntity(pPos) instanceof TileEntityPollutionDeleter deleter) {
				pLevel.sendBlockUpdated(pPos, pState, pState, 3);
				NetworkHooks.openScreen(serverPlayer, deleter, pPos);
			}
		}
		// only allow client!!!
//		if (pLevel.isClientSide) {
//			if (pLevel.getBlockEntity(pPos) instanceof TileEntityPollutionDeleter deleter) {
//				Minecraft.getInstance().setScreen(new ScreenPollutionDeleter(deleter, Component.literal("hi")));
//			}
//		}
		return InteractionResult.sidedSuccess(pLevel.isClientSide);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
		if (Screen.hasControlDown()) {
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.pollution_deleter.line1", Config.pollutionDeleterHorizontalLimit)));
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.pollution_deleter.line2", Config.pollutionDeleterPowerUse)));
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.pollution_deleter.line3", Config.pollutionDeleterWorkSpeed)));

		} else {
			pTooltip.add(Component.literal(ChatFormatting.GRAY + I18n.get("tooltip.pollutionplus.hold_ctrl")));
		}
	}
}
