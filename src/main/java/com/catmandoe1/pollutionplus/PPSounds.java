package com.catmandoe1.pollutionplus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PPSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MODID);
	public static final RegistryObject<SoundEvent> BLOCK_INCINERATOR_WORK = register("block.incinerator.work");
	public static final RegistryObject<SoundEvent> ITEM_LOCATION_MARKER_USE = register("item.location_marker.use");
	public static final RegistryObject<SoundEvent> BLOCK_INFINITE_FILTER_USE = register ("block.infinite_filter.use");

	private static RegistryObject<SoundEvent> register(String name) {
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, name)));
	}

	public static void register(IEventBus bus) {
		SOUNDS.register(bus);
	}

//	@SuppressWarnings("deprecated")
//	private static SoundEvent register(String name) {
//		ResourceLocation resourceLocation = new ResourceLocation(name);
//		return register(resourceLocation, resourceLocation);
//	}
//
//	private static SoundEvent register(ResourceLocation pName, ResourceLocation pLocation) {
//		return (SoundEvent) Registry.register(BuiltInRegistries.SOUND_EVENT, pName, SoundEvent.createVariableRangeEvent(pLocation));
//	}
}
