package com.catmandoe1.pollutionplus.tileentities.poweredFilters;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPoweredFilterIron extends TileEntityPoweredFilter {
	public TileEntityPoweredFilterIron(BlockPos pos, BlockState blockState) {
		super(PPBlocks.POWERED_FILTER_IRON.getTileEntityType(), pos, blockState);
	}

	@Override
	int getMaxEnergyCap() {
		return Config.poweredFilterIronPowerCapacity;
	}

	@Override
	int getFilterSpeed() {
		return Config.poweredFilterIronSpeed;
	}

	@Override
	int getFilterPowerUse() {
		return Config.poweredFilterIronPowerUse;
	}
}
