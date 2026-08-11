package com.catmandoe1.pollutionplus.tileentities;

import net.minecraft.world.level.Level;

public interface IFilter {
	boolean canWork();
	boolean hasCooledOff(Level level);

	/**
	 * @return true if successfully used
	 */
	boolean fakeUse(Level level);
}
