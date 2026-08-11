package com.catmandoe1.pollutionplus.block.poweredFilters;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.tileentities.poweredFilters.TileEntityPoweredFilterIron;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockPoweredFilterIron extends BlockPoweredFilter{
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new TileEntityPoweredFilterIron(blockPos, blockState);
	}

	@Override
	protected BlockEntityType<? extends BlockEntity> getTileEntityType() {
		return PPBlocks.POWERED_FILTER_IRON.getTileEntityType();
	}

	@Override
	protected <T extends BlockEntity> BlockEntityTicker<T> getTileEntityTicker() {
		return TileEntityPoweredFilterIron::update;
	}

	@Override
	protected int getFilterSpeed() {
		return Config.poweredFilterIronSpeed;
	}

	@Override
	protected int getFilterPowerUse() {
		return Config.poweredFilterIronPowerUse;
	}
}
