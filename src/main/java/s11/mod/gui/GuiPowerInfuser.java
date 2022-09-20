package s11.mod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import s11.mod.containers.ContainerPowerInfuser;
import s11.mod.objects.tileEntities.machines.powerInfuser.TilePowerInfuser;
import s11.mod.util.Reference;

public class GuiPowerInfuser extends GuiContainer {
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/container/power_infuser.png"); // defines the gui background png
	private final InventoryPlayer player;
	private final TilePowerInfuser tileentity;
	
	//important
	public GuiPowerInfuser(InventoryPlayer player, TilePowerInfuser tileentity) 
	{
		super(new ContainerPowerInfuser(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
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
		
		if (this.tileentity.isInfusing()) {
			int infusingScale = getInfusionProgressScaled(36);
			this.drawTexturedModalRect(this.guiLeft + 74, this.guiTop + 35, 176, 2, infusingScale + 1, 17);
		}
		
		int energyScale = getEnergyLeftScaled(74);
		//System.out.println("energy scale = " + energyScale);

		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 80 - energyScale, 176, 164 - energyScale, 16, energyScale);
		//System.out.println("container energy = " + tileentity.getField(2));
	}
	
	private int getEnergyLeftScaled(int pixels) {
		int energy = tileentity.getEnergyStored();
		int maxEnergy = tileentity.getMaxEnergyStored();
		System.out.println("energy = " + energy);
		//return (energy / this.tileentity.getField(3)) * pixels;
		return energy != 0 && maxEnergy != 0 ? energy * pixels / maxEnergy : 0;  
		//return  energy * pixels / tileentity.getField(3);
	}
	
	private int getInfusionProgressScaled(int pixels) {
		int progress = this.tileentity.getProgress();
		int maxProgress = this.tileentity.getMaxProgress();
		return (progress != 0 && maxProgress != 0) ? progress * pixels / maxProgress : 0;
	}
}
