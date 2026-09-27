package s11.mod.gui;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import s11.mod.containers.ContainerPollutionDeleter;
import s11.mod.network.MessagePollutionDeleter;
import s11.mod.network.PPNetwork;
import s11.mod.objects.tileEntities.TilePollutionDeleter;
import s11.mod.util.PlayerPressing;
import s11.mod.util.Reference;

public class GuiPollutionDeleter extends GuiContainer {
	private final TilePollutionDeleter deleter;
	private final EntityPlayer player;
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/gui/container/gui_pollution_deleter.png");
	
	private int leftPos;
	private int topPos;
	private int middleX;

	public GuiPollutionDeleter(EntityPlayer player, TilePollutionDeleter deleter) {
		super(new ContainerPollutionDeleter());
		this.deleter = deleter;
		this.player = player;
		this.xSize = 176;
		this.ySize = 150;
		this.middleX = this.xSize / 2;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear(); // just incase
		
		// bottom of button outlines vanished?
		// adding pointAA
		buttonList.add(new GuiButton(0, guiLeft + middleX + 40, guiTop + 39, 9, 8, "+"));
		buttonList.add(new GuiButton(1, guiLeft + middleX + 40, guiTop + 50, 9, 8, "+"));
		buttonList.add(new GuiButton(2, guiLeft + middleX + 40, guiTop + 61, 9, 8, "+"));

		// subbing pointAA
		buttonList.add(new GuiButton(3, guiLeft + middleX - 40, guiTop + 39, 9, 8, "-"));
		buttonList.add(new GuiButton(4, guiLeft + middleX - 40, guiTop + 50, 9, 8, "-"));
		buttonList.add(new GuiButton(5, guiLeft + middleX - 40, guiTop + 61, 9, 8, "-"));

		// adding pointBB
		buttonList.add(new GuiButton(6, guiLeft + middleX + 40, guiTop + 92, 9, 8, "+"));
		buttonList.add(new GuiButton(7, guiLeft + middleX + 40, guiTop + 103, 9, 8, "+"));
		buttonList.add(new GuiButton(8, guiLeft + middleX + 40, guiTop + 114, 9, 8, "+"));

		// subbing pointBB
		buttonList.add(new GuiButton(9, guiLeft + middleX - 40, guiTop + 92, 9, 8, "-"));
		buttonList.add(new GuiButton(10, guiLeft + middleX - 40, guiTop + 103, 9, 8, "-"));
		buttonList.add(new GuiButton(11, guiLeft + middleX - 40, guiTop + 114, 9, 8, "-"));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground(); //the black overlay
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// set background
		GlStateManager.color(1f, 1f, 1f, 1f);
		mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		// title
		drawCentredString(I18n.format("tile.pollutionplus.tile_pollution_deleter.name"), middleX, this.topPos + 6, 0x404040, false);
		
		// point 1 cords
		drawCentredString(I18n.format("gui.pollutionplus.pollution_deleter.areaAA"), middleX, 29, 0x404040, false);
		drawCentredString("x: " + String.valueOf(this.deleter.deletionAreaAA.getX()), middleX, 40, 0x404040, false);
		drawCentredString("y: " + String.valueOf(this.deleter.deletionAreaAA.getY()), middleX, 51, 0x404040, false);
		drawCentredString("z: " + String.valueOf(this.deleter.deletionAreaAA.getZ()), middleX, 62, 0x404040, false);

		// point 2 cords
		drawCentredString(I18n.format("gui.pollutionplus.pollution_deleter.areaBB"), middleX, 82, 0x404040, false);
		drawCentredString("x: " + String.valueOf(this.deleter.deletionAreaBB.getX()), middleX, 93, 0x404040, false);
		drawCentredString("y: " + String.valueOf(this.deleter.deletionAreaBB.getY()), middleX, 104, 0x404040, false);
		drawCentredString("z: " + String.valueOf(this.deleter.deletionAreaBB.getZ()), middleX, 115, 0x404040, false);
}
	
	private void drawCentredString(String text, int x, int y, int colour, boolean hasDropShadow) {
		fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2.0f, y, colour, hasDropShadow);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button instanceof GuiButton) { // no really needed
			PPNetwork.NETWORK.sendToServer(new MessagePollutionDeleter(mc.player, button.id, PlayerPressing.isCrtlDown(), deleter.getPos()));
		}
	}
}
