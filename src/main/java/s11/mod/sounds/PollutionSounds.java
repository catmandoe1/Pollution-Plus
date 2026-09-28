package s11.mod.sounds;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import s11.mod.util.Reference;

public class PollutionSounds {
	public static SoundEvent BLOCK_INCINERATOR_WORK; //			incinerator
	public static SoundEvent BLOCK_INFINITE_FILTER_USE; //		infinite filter
	public static SoundEvent ITEM_LOCATION_MARKER_USE; //		location marker
	public static SoundEvent BLOCK_POLLUTION_PUMP_WORK; //		pollution pump
	
	public static void registerSounds() {
		BLOCK_INCINERATOR_WORK = register("block.incinerator.work");
		BLOCK_INFINITE_FILTER_USE = register("block.infinite_filter.use");
		ITEM_LOCATION_MARKER_USE = register("item.location_marker.use");
		BLOCK_POLLUTION_PUMP_WORK = register("block.pollution_pump.work");
	}
	
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
