package com.catmandoe1.pollutionplus.block.poweredFilters;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.tileentities.poweredFilters.TileEntityPoweredFilterVoid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockPoweredFilterVoid extends BlockPoweredFilter {
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TileEntityPoweredFilterVoid(blockPos, blockState);
	}

	@Override
	protected BlockEntityType<? extends BlockEntity> getTileEntityType() {
		return PPBlocks.POWERED_FILTER_VOID.getTileEntityType();
	}

	@Override
	protected <T extends BlockEntity> BlockEntityTicker<T> getTileEntityTicker() {
		return TileEntityPoweredFilterVoid::update;
	}

	@Override
	protected int getFilterSpeed() {
		return Config.poweredFilterVoidSpeed;
	}

	@Override
	protected int getFilterPowerUse() {
		return Config.poweredFilterVoidPowerUse;
	}
}
