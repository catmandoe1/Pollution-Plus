package com.catmandoe1.pollutionplus.block.poweredFilters;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.tileentities.poweredFilters.TileEntityPoweredFilterDiamond;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockPoweredFilterDiamond extends BlockPoweredFilter{
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TileEntityPoweredFilterDiamond(blockPos, blockState);
	}

	@Override
	protected BlockEntityType<? extends BlockEntity> getTileEntityType() {
		return PPBlocks.POWERED_FILTER_DIAMOND.getTileEntityType();
	}

	@Override
	protected <T extends BlockEntity> BlockEntityTicker<T> getTileEntityTicker() {
		return TileEntityPoweredFilterDiamond::update;
	}

	@Override
	protected int getFilterSpeed() {
		return Config.poweredFilterDiamondSpeed;
	}

	@Override
	protected int getFilterPowerUse() {
		return Config.poweredFilterDiamondPowerUse;
	}
}
