package com.catmandoe1.pollutionplus.item;

import com.catmandoe1.pollutionplus.Main;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PPItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	//items
	public static final RegistryObject<Item> VOID_INGOT = ITEMS.register("void_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<ItemLocationMarker> LOCATION_MARKER = ITEMS.register("location_marker", () -> new ItemLocationMarker(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> FILTER_CORE_WEAK = ITEMS.register("filter_core_weak", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FILTER_CORE = ITEMS.register("filter_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FAN = ITEMS.register("fan", () -> new Item(new Item.Properties()));
}
