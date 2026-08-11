package com.catmandoe1.pollutionplus.compat.jade;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.block.BlockInfiniteFilter;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ComponentProviderInfiniteFilter implements IBlockComponentProvider {
	INSTANCE;
	public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "infinite_filter");

	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor block, IPluginConfig iPluginConfig) {
		if (block.getBlockState().getValue(BlockInfiniteFilter.POWERED)) {
			tooltip.add(Component.translatable("jade.pollutionplus.infinite_filter.active").withStyle(ChatFormatting.GREEN));
		} else {
			tooltip.add(Component.translatable("jade.pollutionplus.infinite_filter.inactive").withStyle(ChatFormatting.RED));
		}
	}
}
