package com.catmandoe1.pollutionplus.tileentities.poweredFilters;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPoweredFilterVoid extends TileEntityPoweredFilter{
	public TileEntityPoweredFilterVoid(BlockPos pos, BlockState blockState) {
		super(PPBlocks.POWERED_FILTER_VOID.getTileEntityType(), pos, blockState);
	}

	@Override
	int getMaxEnergyCap() {
		return Config.poweredFilterVoidPowerCapacity;
	}

	@Override
	int getFilterSpeed() {
		return Config.poweredFilterVoidSpeed;
	}

	@Override
	int getFilterPowerUse() {
		return Config.poweredFilterVoidPowerUse;
	}
}
