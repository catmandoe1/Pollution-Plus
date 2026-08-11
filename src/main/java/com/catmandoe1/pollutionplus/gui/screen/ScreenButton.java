package com.catmandoe1.pollutionplus.gui.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ScreenButton extends Button {
	public int id;
	public ScreenButton(int id, int x, int y, int width, int height, Component pMessage, OnPress pOnPress) {
		super(x, y, width, height, pMessage, pOnPress, DEFAULT_NARRATION);

		this.id = id;
	}

	public ScreenButton(int id, int x, int y, int width, int height, String message, OnPress onPress) {
		this(id, x, y, width, height, Component.literal(message), onPress);
	}
}
