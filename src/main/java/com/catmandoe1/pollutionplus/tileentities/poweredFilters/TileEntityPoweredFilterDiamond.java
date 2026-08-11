package com.catmandoe1.pollutionplus.tileentities.poweredFilters;

import com.catmandoe1.pollutionplus.Config;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityPoweredFilterDiamond extends TileEntityPoweredFilter{
	public TileEntityPoweredFilterDiamond(BlockPos pos, BlockState blockState) {
		super(PPBlocks.POWERED_FILTER_DIAMOND.getTileEntityType(), pos, blockState);
	}

	@Override
	int getMaxEnergyCap() {
		return Config.poweredFilterDiamondPowerCapacity;
	}

	@Override
	int getFilterSpeed() {
		return Config.poweredFilterDiamondSpeed;
	}

	@Override
	int getFilterPowerUse() {
		return Config.poweredFilterDiamondPowerUse;
	}
}
