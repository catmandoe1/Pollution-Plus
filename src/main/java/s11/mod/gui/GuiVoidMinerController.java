package s11.mod.gui;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.containers.ContainerVoidMinerController;
import s11.mod.objects.tileEntities.machines.TileVoidMinerController;
import s11.mod.util.Reference;

public class GuiVoidMinerController extends GuiContainer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/container/void_miner_controller.png"); // defines the gui background png
	private final InventoryPlayer player;
	private final TileVoidMinerController tileEntity;

	public GuiVoidMinerController(InventoryPlayer player, TileVoidMinerController tileentity) {
		super(new ContainerVoidMinerController(player, tileentity));
		this.player = player;
		this.tileEntity = tileentity;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground(); //the black overlay
		super.drawScreen(mouseX, mouseY, partialTicks);
		List<String> text = Lists.newArrayList(TextFormatting.LIGHT_PURPLE + "Energy Stored:" + TextFormatting.WHITE + " " + Integer.toString(tileEntity.getEnergyStored()) + " / " + Integer.toString(tileEntity.getMaxEnergyStored()) + " RF", 
				TextFormatting.LIGHT_PURPLE + "Process Power:" + TextFormatting.WHITE + " " + Integer.toString(PollutionPlusConfig.Machines.voidMinerController.operationCost) + " RF/t");
		drawTooltip(text, mouseX, mouseY, 8, 6, 16, 74);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int progressScale = getProgressScaled(37); // 1 bigger than what the image is fixes stuff
		this.drawTexturedModalRect(this.guiLeft + 43, this.guiTop + 35, 176, 2, progressScale, 17);

		
		int energyScale = getEnergyLeftScaled(74);

		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 80 - energyScale, 176, 160 - energyScale, 16, energyScale);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String tileName = this.tileEntity.getDisplayName().getUnformattedComponentText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2), 8, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedComponentText(), 122, this.ySize - 96 + 2, 4210752);
	}
	
	protected void drawTooltip(List<String> text, int mouseX, int mouseY, int x, int y, int tooltipWidth, int tooltipHeight) {
		int xPos = x + guiLeft;
		int yPos = y + guiTop;
		if (mouseX >= xPos && mouseY >= yPos && mouseX < xPos + tooltipWidth && mouseY < yPos + tooltipHeight) {
			drawHoveringText(text, mouseX, mouseY);
		}
	}
	
	private int getEnergyLeftScaled(int pixels) {
		int energy = tileEntity.getEnergyStored();
		int maxEnergy = tileEntity.getMaxEnergyStored();
		return energy != 0 && maxEnergy != 0 ? energy * pixels / maxEnergy : 0;  
	}
	
	private int getProgressScaled(int pixels) {
		int progress = this.tileEntity.getProgress();
		int maxProgress = this.tileEntity.getMaxProgress();
		return (progress != 0 && maxProgress != 0) ? progress * pixels / maxProgress : 0;
	}
}
