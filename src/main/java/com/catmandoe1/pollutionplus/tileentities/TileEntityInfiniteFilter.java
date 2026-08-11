package com.catmandoe1.pollutionplus.tileentities;

import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.block.poweredFilters.BlockPoweredFilter;
import com.endertech.minecraft.mods.adpother.entities.GasEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TileEntityInfiniteFilter extends BlockEntity implements IFilter {
	public TileEntityInfiniteFilter(BlockPos pPos, BlockState pBlockState) {
		super(PPBlocks.INFINITE_FILTER.getTileEntityType(), pPos, pBlockState);
	}

	public static <T extends BlockEntity> void update(Level level, BlockPos blockPos, BlockState blockState, T t) {
		if (!(t instanceof TileEntityInfiniteFilter)) {
			return;
		}

		TileEntityInfiniteFilter tileEntity = (TileEntityInfiniteFilter)t;

		if (blockState.getValue(BlockPoweredFilter.POWERED)) {
			tileEntity.filterPollution(level, blockPos);
		}
	}

	private boolean filterPollution(Level level, BlockPos blockPos) {
		List<GasEntity> pollution =  level.getEntitiesOfClass(GasEntity.class, AABB.ofSize(blockPos.getCenter(), 1, 1, 1));
		boolean hasDeleted = false;

		for (GasEntity poll : pollution) {
			hasDeleted = true;
			poll.discard();
		}

		return hasDeleted;
	}

	@Override
	public boolean canWork() {
		return this.getBlockState().getValue(BlockPoweredFilter.POWERED);
	}

	@Override
	public boolean hasCooledOff(Level level) {
		return true;
	}

	@Override
	public boolean fakeUse(Level level) {
		return true;
	}
}
