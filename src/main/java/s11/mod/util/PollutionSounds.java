package s11.mod.util;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PollutionSounds {
	//public static final ObjectSet<String> TICKABLE_SOUNDS = new ObjectOpenHashSet<>();
	
	public static SoundEvent BLOCK_POWER_INFUSER_RUNNING, BLOCK_POWER_INFUSER_RECIPE_COMPLETE; //	power infuser
	public static SoundEvent BLOCK_ALLOY_FURNACE_RUNNING, BLOCK_ALLOY_FURNACE_RECIPE_COMPLETE; //	alloy furnace
	public static SoundEvent BLOCK_HYDRAULIC_PRESS_RUNNING; //										hydraulic press 
	public static SoundEvent BLOCK_INCINERATOR_WORK; //												incinerator
	
	public static void registerSounds() {
		BLOCK_POWER_INFUSER_RUNNING = register("block.power_infuser.running");
		BLOCK_POWER_INFUSER_RECIPE_COMPLETE = register("block.power_infuser.recipe_complete");
		BLOCK_ALLOY_FURNACE_RUNNING = register("block.alloy_furnace.running");
		BLOCK_ALLOY_FURNACE_RECIPE_COMPLETE = register("block.alloy_furnace.recipe_complete");
		BLOCK_HYDRAULIC_PRESS_RUNNING = register("block.hydraulic_press.running");
		BLOCK_INCINERATOR_WORK = register("block.incinerator.work");
	}
	
//	private static SoundEvent register(String name) {
//		return register(name, false);
//	}
	
	public static ResourceLocation getSound(String name) {
		return new ResourceLocation(Reference.MODID, name);
	}
	
	private static SoundEvent register(String name) {
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		//if (tickable) {
		//	TICKABLE_SOUNDS.add(location.toString());
		//}
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
