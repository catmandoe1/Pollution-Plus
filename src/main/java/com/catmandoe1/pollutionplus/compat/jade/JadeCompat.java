package com.catmandoe1.pollutionplus.compat.jade;

import com.catmandoe1.pollutionplus.block.BlockIncinerator;
import com.catmandoe1.pollutionplus.block.BlockInfiniteFilter;
import com.catmandoe1.pollutionplus.block.BlockPollutionDeleter;
import com.catmandoe1.pollutionplus.block.poweredFilters.BlockPoweredFilter;
import com.catmandoe1.pollutionplus.block.pump.BlockPollutionPump;
import com.catmandoe1.pollutionplus.tileentities.TileEntityIncinerator;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionPump;
import com.catmandoe1.pollutionplus.tileentities.poweredFilters.TileEntityPoweredFilter;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeCompat implements IWailaPlugin {
	@Override
	public void register(IWailaCommonRegistration registry) {
		registry.registerBlockDataProvider(ComponentProviderPollutionDeleter.INSTANCE, TileEntityPollutionDeleter.class);
		registry.registerBlockDataProvider(ComponentProviderIncinerator.INSTANCE, TileEntityIncinerator.class);
		registry.registerBlockDataProvider(ComponentProviderPoweredFilter.INSTANCE, TileEntityPoweredFilter.class);
		registry.registerBlockDataProvider(ComponentProviderPollutionPump.INSTANCE, TileEntityPollutionPump.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registry) {
		registry.registerBlockComponent(ComponentProviderPollutionDeleter.INSTANCE, BlockPollutionDeleter.class);
		registry.registerBlockComponent(ComponentProviderIncinerator.INSTANCE, BlockIncinerator.class);
		registry.registerBlockComponent(ComponentProviderPoweredFilter.INSTANCE, BlockPoweredFilter.class);
		registry.registerBlockComponent(ComponentProviderInfiniteFilter.INSTANCE, BlockInfiniteFilter.class);
		registry.registerBlockComponent(ComponentProviderPollutionPump.INSTANCE, BlockPollutionPump.class);
	}
}
