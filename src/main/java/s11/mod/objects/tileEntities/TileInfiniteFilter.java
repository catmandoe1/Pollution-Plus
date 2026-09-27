package s11.mod.objects.tileEntities;

import java.util.List;

import com.endertech.minecraft.mods.adpother.entities.EntityPollutant;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import s11.mod.objects.blocks.BlockInfiniteFilter;
import s11.mod.objects.blocks.IFilter;

public class TileInfiniteFilter extends TileEntity implements ITickable, IFilter {
	@Override
	public void update() {
		if (getWorld().isRemote) {
			return;
		}
		
		// run if blockstate active is true
		if (this.canWork()) {
			this.filterPollution();
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
	
	private boolean filterPollution() {
		List<EntityPollutant> pollution = getWorld().getEntitiesWithinAABB(EntityPollutant.class, getFilterBB());
		boolean hasDeleted = false;
		
		for (EntityPollutant poll : pollution) {
			hasDeleted = true;
			poll.setDead();
		}
		
		return hasDeleted;
	}
	
	public AxisAlignedBB getFilterBB() {
		return new AxisAlignedBB(getPos());
	}

	@Override
	public boolean canWork() {
		return world.getBlockState(this.pos).getValue(BlockInfiniteFilter.ACTIVE);
	}

	@Override
	public boolean hasCooledOff() {
		return true;
	}

	@Override
	public boolean fakeUse() {
		return true;
	}
}
