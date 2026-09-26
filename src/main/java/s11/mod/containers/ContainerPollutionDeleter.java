package s11.mod.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPollutionDeleter extends Container {
	
	public ContainerPollutionDeleter() {}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	
	// not much here because the deleter isnt a container
}
