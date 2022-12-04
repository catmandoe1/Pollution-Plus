package s11.mod.objects.tileEntities.powered_filters;

import java.util.List;

import com.endertech.minecraft.mods.adpother.entities.EntityPollutant;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.blocks.unique.powered_filters.BlockIronPoweredFilter;

public class TileIronPoweredFilter extends TilePoweredFilterBase {
	@Override
	public int getMaxEnergyCap() {
		return PollutionPlusConfig.PoweredFilters.iron.filterMaxCapacity;
	}
	
	@Override
	public int getFilterSpeed() {
		return PollutionPlusConfig.PoweredFilters.iron.filterSpeed;
	}
	
	@Override
	public int getFilterPowerUse() {
		return PollutionPlusConfig.PoweredFilters.iron.filterPowerUse;
	}
	
	@Override
	public PropertyBool getBlockPropertyACTIVE() {
		return BlockIronPoweredFilter.ACTIVE;
	}
}
