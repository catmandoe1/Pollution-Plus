package com.catmandoe1.pollutionplus.datagen;

import com.catmandoe1.pollutionplus.block.PPBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class PPLootTables extends BlockLootSubProvider {
	protected PPLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags()); // maybe doing something important
	}

	public static LootTableProvider getProvider(PackOutput output) {
		return new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(PPLootTables::new, LootContextParamSets.BLOCK)));
	}

	@Nonnull
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return PPBlocks.BLOCKS.getEntries().stream().flatMap(RegistryObject::stream)::iterator;
	}

	@Override
	protected void generate() {
		dropSelf(PPBlocks.INCINERATOR.get());
	}

}
