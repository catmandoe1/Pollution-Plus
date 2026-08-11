package com.catmandoe1.pollutionplus.compat.jade;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.tileentities.TileEntityIncinerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ComponentProviderIncinerator implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
	INSTANCE;
	public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "incinerator");

	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor block, IPluginConfig iPluginConfig) {
		if (block.getServerData().contains("cooldown")) {
			long cooldown = block.getServerData().getLong("cooldown");

			// display in seconds
			if (cooldown >= 20) {
				tooltip.add(Component.translatable("jade.pollutionplus.cooldown_seconds", cooldown / 20));
			} else {
				tooltip.add(Component.translatable("jade.pollutionplus.cooldown_ticks", cooldown));
			}
		}
	}

	@Override
	public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
		TileEntityIncinerator tileEntity = (TileEntityIncinerator) blockAccessor.getBlockEntity();
		compoundTag.putLong("cooldown", tileEntity.getCooldownRemaining(blockAccessor.getLevel()));
	}
}
