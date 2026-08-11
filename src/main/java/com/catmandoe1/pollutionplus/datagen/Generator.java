package com.catmandoe1.pollutionplus.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;

public class Generator {
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();

		// saves time with loot tables (unnecessary, 1.12.2 had this sorted)
		generator.addProvider(event.includeServer(), PPLootTables.getProvider(generator.getPackOutput()));
	}
}
