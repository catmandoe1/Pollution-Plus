package s11.mod.gui;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import s11.mod.containers.ContainerDischarger;
import s11.mod.objects.tileEntities.machines.TileDischarger;
import s11.mod.util.Reference;
import s11.mod.util.TextHelper;

public class GuiDischarger extends GuiContainer {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/container/discharger.png"); // TODO change
	private final InventoryPlayer player;
	private final TileDischarger tileentity;

	public GuiDischarger(InventoryPlayer player, TileDischarger tileEntity) {
		super(new ContainerDischarger(player, tileEntity));
		this.player = player;
		this.tileentity = tileEntity;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground(); //the black overlay
		super.drawScreen(mouseX, mouseY, partialTicks);
		List<String> text = Lists.newArrayList(TextFormatting.LIGHT_PURPLE + "Energy Stored:" + TextFormatting.WHITE + " " + Integer.toString((int)(tileentity.getEnergyStored() * 0.01F)) + " / " + Integer.toString((int)(tileentity.getMaxEnergyStored() * 0.01F)) + " kRF", 
				TextFormatting.LIGHT_PURPLE + TextHelper.getLang("discharger.disc1") + TextFormatting.WHITE + " " + TextHelper.getHrMnScFromSeconds(TextHelper.getSecondsFromTicks(tileentity.getTimeRemaining())));
		
		drawTooltip(text, mouseX, mouseY, 8, 6, 36, 74);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	// foreground is only for text, drawing textures will break
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String tileName = this.tileentity.getDisplayName().getUnformattedComponentText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2), 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedComponentText(), 122, this.ySize - 96 + 2, 4210752);
	}

	// anything after the main texture bind with over lap the background
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int progressScale = getProgressScaled(37);
		this.drawTexturedModalRect(this.guiLeft + 74, this.guiTop + 34, 176, 2, progressScale, 17);
		
		int energyScale = getEnergyScaled(74);

		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 80 - energyScale, 176, 164 - energyScale, 36, energyScale);		
	}

	protected void drawTooltip(List<String> text, int mouseX, int mouseY, int x, int y, int tooltipWidth, int tooltipHeight) {
		int xPos = x + guiLeft;
		int yPos = y + guiTop;
		if (mouseX >= xPos && mouseY >= yPos && mouseX < xPos + tooltipWidth && mouseY < yPos + tooltipHeight) {
			drawHoveringText(text, mouseX, mouseY);
		}
	}
	
	private int getEnergyScaled(int pixels) {
		int energy = tileentity.getEnergyStored();
		int maxEnergy = tileentity.getMaxEnergyStored();
		return energy != 0 && maxEnergy != 0 ? energy * pixels / maxEnergy : 0;  
	}
	
	private int getProgressScaled(int pixels) {
		int progress = this.tileentity.getProgress();
		int maxProgress = this.tileentity.getMaxProgress();
		return (progress != 0 && maxProgress != 0) ? progress * pixels / maxProgress : 0;
	}
}
