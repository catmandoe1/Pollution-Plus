package com.catmandoe1.pollutionplus.compat.jade;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionPump;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ComponentProviderPollutionPump implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
	INSTANCE;
	public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "pollution_pump");
	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor block, IPluginConfig iPluginConfig) {
		if (!block.getServerData().contains("topPipe")) {
			tooltip.add(Component.translatable("jade.pollutionplus.pollution_pump.require_pipe").withStyle(ChatFormatting.RED));
			return;
		}

		BlockPos topPipe = BlockPos.of(block.getServerData().getLong("topPipe"));
		tooltip.add(Component.translatable("jade.pollutionplus.pollution_pump.pipe_top", topPipe.getY()));
	}

	@Override
	public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
		TileEntityPollutionPump pump = (TileEntityPollutionPump) blockAccessor.getBlockEntity();
		if (pump.hasPipe()) {
			compoundTag.putLong("topPipe", pump.getTopPipe().asLong());
		}
	}
}
