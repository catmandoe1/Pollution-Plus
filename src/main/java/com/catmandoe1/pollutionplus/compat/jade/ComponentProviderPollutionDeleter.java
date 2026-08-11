package com.catmandoe1.pollutionplus.compat.jade;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ComponentProviderPollutionDeleter implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
	INSTANCE;
	public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "pollution_deleter");

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor block, IPluginConfig iPluginConfig) {
		if (block.getServerData().contains("current_position")) {
			BlockPos currentPosition = BlockPos.of(block.getServerData().getLong("current_position"));

			tooltip.add(Component.translatable("jade.pollutionplus.pollution_deleter.current_position", currentPosition.getX(), currentPosition.getY(), currentPosition.getZ()));
		}
	}

	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	@Override
	public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
		TileEntityPollutionDeleter deleter = (TileEntityPollutionDeleter) blockAccessor.getBlockEntity();
		compoundTag.putLong("current_position", deleter.currentDeletionPos.asLong());

	}
}
