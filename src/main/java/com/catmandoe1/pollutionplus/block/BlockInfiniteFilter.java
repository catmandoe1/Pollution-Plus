package com.catmandoe1.pollutionplus.block;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.poweredFilters.BlockPoweredFilter;
import com.catmandoe1.pollutionplus.item.PPItems;
import com.catmandoe1.pollutionplus.tileentities.TileEntityInfiniteFilter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockInfiniteFilter extends BlockPoweredFilter {
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TileEntityInfiniteFilter(blockPos, blockState);
	}

	@Override
	protected BlockEntityType<? extends BlockEntity> getTileEntityType() {
		return PPBlocks.INFINITE_FILTER.getTileEntityType();
	}

	@Override
	protected <T extends BlockEntity> BlockEntityTicker<T> getTileEntityTicker() {
		return TileEntityInfiniteFilter::update;
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (!pLevel.isClientSide() && !pState.getValue(POWERED)) {
			if (pPlayer.isHolding(Config.infiniteFilterActivationItem) && pHand == InteractionHand.MAIN_HAND) {

				//pLevel.sendBlockUpdated(pPos, pState, pState.setValue(POWERED, true), 3);
				pLevel.setBlock(pPos, pState.setValue(POWERED, true), 3); // update block
				if (!pPlayer.isCreative()) {
					pPlayer.getMainHandItem().shrink(1); // use item
				}

				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	@Override
	protected int getFilterSpeed() {return 0;}

	@Override
	protected int getFilterPowerUse() {return 0;}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
		if (Screen.hasControlDown()) {
//			String line1 = I18n.get("tooltip.pollutionplus.powered_filter.line1");
//			pTooltip.add(Component.literal(line1));

			String line2 = I18n.get("tooltip.pollutionplus.infinite_filter", Config.infiniteFilterActivationItem.getName(Config.infiniteFilterActivationItem.getDefaultInstance()).getString());
			//String line2 = I18n.get("tooltip.pollutionplus.infinite_filter.line2", Config.infiniteFilterActivationItem.getDescription().getString());
			//String line2 = I18n.get("tooltip.pollutionplus.infinite_filter.line2", Config.infiniteFilterActivationItem.toString());
			pTooltip.add(Component.literal(ChatFormatting.AQUA + line2));
		} else {
			pTooltip.add(Component.literal(ChatFormatting.GRAY + I18n.get("tooltip.pollutionplus.hold_ctrl")));
		}
	}

	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		// if not being updated
		if (!pState.is(pNewState.getBlock())) {
			if (pState.getValue(POWERED)) {
				// drop activation item
				// could cheat with this by changing the config to dirt, activate the filter and then change to something expensive but not my problem
				Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(Config.infiniteFilterActivationItem));
			}

			super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
		}
	}
}
