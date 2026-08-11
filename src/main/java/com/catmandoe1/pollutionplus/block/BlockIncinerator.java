package com.catmandoe1.pollutionplus.block;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.tileentities.TileEntityIncinerator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockIncinerator extends Block implements EntityBlock {
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public BlockIncinerator() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(2.0F, 6).sound(SoundType.METAL));

		// fix default blockstate
		this.registerDefaultState(this.getStateDefinition().any().setValue(POWERED, false));

	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
		if (Screen.hasControlDown()) {
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.incinerator.line1", Config.incineratorWorkRange)));
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.incinerator.line2", Config.incineratorPowerUse)));
			pTooltip.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.incinerator.line3", Config.incineratorWorkSpeed)));

		} else {
			pTooltip.add(Component.literal(ChatFormatting.GRAY + I18n.get("tooltip.pollutionplus.hold_ctrl")));
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(POWERED);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TileEntityIncinerator(blockPos, blockState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
		// if server side and correct tile entity
		if (!pLevel.isClientSide && pBlockEntityType == PPBlocks.INCINERATOR.getTileEntityType()) {
			return TileEntityIncinerator::update;
		}
		return null;
	}

	@Override
	public boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
		return state.is(PPBlocks.INCINERATOR.getBlock());
	}

	//	@Override
//	public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
//		boolean redstoneSignal = level.hasNeighborSignal(pos);
//		System.out.println("changed!");
//
//		for (Direction direction : Direction.values()) {
//			System.out.println(level.getSignal(pos, direction));
//		}
//
//		if (redstoneSignal != state.getValue(POWERED)) {
//			state.setValue(POWERED, redstoneSignal);
//			System.out.println("new signal: " + redstoneSignal);
//		}
//	}
}
