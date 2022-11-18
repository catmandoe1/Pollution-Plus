package s11.mod.gui;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.containers.ContainerHydraulicPress;
import s11.mod.objects.tileEntities.machines.TileHydraulicPress;
import s11.mod.util.Reference;

public class GuiHydraulicPress extends GuiContainer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/container/hydraulic_press.png"); // defines the gui background png
	private final InventoryPlayer player;
	private final TileHydraulicPress tileentity;
	
	public GuiHydraulicPress(InventoryPlayer player, TileHydraulicPress tileentity) {
		super(new ContainerHydraulicPress(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground(); //the black overlay
		super.drawScreen(mouseX, mouseY, partialTicks);
		List<String> text = Lists.newArrayList(TextFormatting.LIGHT_PURPLE + "Energy Stored:" + TextFormatting.WHITE + " " + Integer.toString(tileentity.getEnergyStored()) + " / " + Integer.toString(tileentity.getMaxEnergyStored()) + " RF", 
				TextFormatting.LIGHT_PURPLE + "Process Power:" + TextFormatting.WHITE + " " + Integer.toString(PollutionPlusConfig.Machines.hydraulicPress.operationCost) + " RF/t");
		drawTooltip(text, mouseX, mouseY, 8, 6, 16, 74);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String tileName = this.tileentity.getDisplayName().getUnformattedComponentText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2), 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedComponentText(), 122, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int crushingScale = getCrushingProgressScaled(37);
		this.drawTexturedModalRect(this.guiLeft + 74, this.guiTop + 34, 176, 2, crushingScale, 17);
		
		int energyScale = getEnergyLeftScaled(74);

		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 80 - energyScale, 176, 164 - energyScale, 16, energyScale);
	}
	
	protected void drawTooltip(List<String> text, int mouseX, int mouseY, int x, int y, int tooltipWidth, int tooltipHeight) {
		int xPos = x + guiLeft;
		int yPos = y + guiTop;
		if (mouseX >= xPos && mouseY >= yPos && mouseX < xPos + tooltipWidth && mouseY < yPos + tooltipHeight) {
			drawHoveringText(text, mouseX, mouseY);
		}
	}

	private int getEnergyLeftScaled(int pixels) {
		int energy = tileentity.getEnergyStored();
		int maxEnergy = tileentity.getMaxEnergyStored();
		return energy != 0 && maxEnergy != 0 ? energy * pixels / maxEnergy : 0;  
	}
	
	private int getCrushingProgressScaled(int pixels) {
		int progress = this.tileentity.getProgress();
		int maxProgress = this.tileentity.getMaxProgress();
		return (progress != 0 && maxProgress != 0) ? progress * pixels / maxProgress : 0;
	}
}
