package com.catmandoe1.pollutionplus.tileentities.poweredFilters;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPoweredFilterGold extends TileEntityPoweredFilter{
	public TileEntityPoweredFilterGold(BlockPos pos, BlockState blockState) {
		super(PPBlocks.POWERED_FILTER_GOLD.getTileEntityType(), pos, blockState);
	}

	@Override
	int getMaxEnergyCap() {
		return Config.poweredFilterGoldPowerCapacity;
	}

	@Override
	int getFilterSpeed() {
		return Config.poweredFilterGoldSpeed;
	}

	@Override
	int getFilterPowerUse() {
		return Config.poweredFilterGoldPowerUse;
	}
}
