package com.catmandoe1.pollutionplus.gui.screen;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.gui.container.ContainerPollutionDeleter;
import com.catmandoe1.pollutionplus.network.MessagePollutionDeleter;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.gui.components.Button;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ScreenPollutionDeleter extends AbstractContainerScreen<ContainerPollutionDeleter> {
	private final TileEntityPollutionDeleter deleter;
	private final Player player;
	private static final ResourceLocation TEXTURE = new ResourceLocation(Main.MODID + ":textures/gui/gui_pollution_deleter.png");


	private int leftPos;
	private int topPos;
	private int middleX;
	public ScreenPollutionDeleter(ContainerPollutionDeleter container, Inventory inventory, Component title) {
		super(container, inventory, title);
		this.deleter = container.deleter;
		this.player = inventory.player;
		this.imageWidth = 176;
		this.imageHeight= 150;
		this.middleX = this.imageWidth / 2;
	}

	@Override
	protected void init() {
		super.init();
		clearWidgets();

		// because not working
		this.leftPos = (this.width - this.imageWidth) / 2;
		this.topPos = (this.height - this.imageHeight) / 2;

		Button.OnPress message = button -> {
			if (button instanceof ScreenButton but) {
				Main.NETWORK_WRAPPER.sendToServer(new MessagePollutionDeleter(this.player, but.id, hasControlDown(), this.deleter.getBlockPos()));
				//Main.LOGGER.info("pre number" + deleter.deletionAreaAA);
			}
		};

		// adding pointAA
		addRenderableWidget(new ScreenButton(0, leftPos + middleX + 40, topPos + 39, 9, 9, "+", message));
		addRenderableWidget(new ScreenButton(1, leftPos + middleX + 40, topPos + 50, 9, 9, "+", message));
		addRenderableWidget(new ScreenButton(2, leftPos + middleX + 40, topPos + 61, 9, 9, "+", message));

		// subbing pointAA
		addRenderableWidget(new ScreenButton(3, leftPos + middleX - 40, topPos + 39, 9, 9, "-", message));
		addRenderableWidget(new ScreenButton(4, leftPos + middleX - 40, topPos + 50, 9, 9, "-", message));
		addRenderableWidget(new ScreenButton(5, leftPos + middleX - 40, topPos + 61, 9, 9, "-", message));

		// adding pointBB
		addRenderableWidget(new ScreenButton(6, leftPos + middleX + 40, topPos + 92, 9, 9, "+", message));
		addRenderableWidget(new ScreenButton(7, leftPos + middleX + 40, topPos + 103, 9, 9, "+", message));
		addRenderableWidget(new ScreenButton(8, leftPos + middleX + 40, topPos + 114, 9, 9, "+", message));

		// subbing pointBB
		addRenderableWidget(new ScreenButton(9, leftPos + middleX - 40, topPos + 92, 9, 9, "-", message));
		addRenderableWidget(new ScreenButton(10, leftPos + middleX - 40, topPos + 103, 9, 9, "-", message));
		addRenderableWidget(new ScreenButton(11, leftPos + middleX - 40, topPos + 114, 9, 9, "-", message));
	}

	@Override
	public void render(@NotNull GuiGraphics screen, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(screen);
		super.render(screen, pMouseX, pMouseY, pPartialTick);
		renderTooltip(screen, pMouseX, pMouseY);
	}

	@Override
	protected void renderBg(GuiGraphics screen, float partialTicks, int mouseX, int mouseY) {
		screen.blit(TEXTURE, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(@NotNull GuiGraphics screen, int pMouseX, int pMouseY) {
		// title
		drawCentredString(screen, this.title, middleX, this.titleLabelY, 0x404040, false);

		// point 1 cords
		drawCentredString(screen, Component.translatable("gui.pollutionplus.pollution_deleter.areaAA"), middleX, 29, 0x404040, false);
		drawCentredString(screen, "x: " + String.valueOf(this.deleter.deletionAreaAA.getX()), middleX, 40, 0x404040, false);
		drawCentredString(screen, "y: " + String.valueOf(this.deleter.deletionAreaAA.getY()), middleX, 51, 0x404040, false);
		drawCentredString(screen, "z: " + String.valueOf(this.deleter.deletionAreaAA.getZ()), middleX, 62, 0x404040, false);

		// point 2 cords
		drawCentredString(screen, Component.translatable("gui.pollutionplus.pollution_deleter.areaBB"), middleX, 82, 0x404040, false);
		drawCentredString(screen, "x: " + String.valueOf(this.deleter.deletionAreaBB.getX()), middleX, 93, 0x404040, false);
		drawCentredString(screen, "y: " + String.valueOf(this.deleter.deletionAreaBB.getY()), middleX, 104, 0x404040, false);
		drawCentredString(screen, "z: " + String.valueOf(this.deleter.deletionAreaBB.getZ()), middleX, 115, 0x404040, false);
	}

	private void drawCentredString(GuiGraphics screen, String text, int x, int y, int colour, boolean hasDropShadow) {
		screen.drawString(this.font, text, x - this.font.width(text) / 2.0f, y, colour, hasDropShadow);
	}

	private void drawCentredString(GuiGraphics screen,  Component text, int x, int y, int colour, boolean hasDropShadow) {
		drawCentredString(screen, text.getString(), x, y, colour, hasDropShadow);
	}


	//	@Override
//	public void renderBackground(GuiGraphics screen) {
//		super.renderBackground(screen);
//		screen.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
//	}
}
