package com.catmandoe1.pollutionplus.tabs;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.block.RegisteredBlock;
import com.catmandoe1.pollutionplus.item.PPItems;
import com.catmandoe1.pollutionplus.block.PPBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class PPCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

	public static final RegistryObject<CreativeModeTab> PP_TAB = CREATIVE_MODE_TAB.register(Main.MODID + "_tab",
			() -> CreativeModeTab.builder().icon(
							() -> new ItemStack(PPBlocks.POWERED_FILTER_VOID.getItem()) // sets tab icon to void filter
					).title(Component.translatable("creativetab.pollutionplus.title")) // sets title to translatable key
					.displayItems((itemDisplayParameters, output) -> { // adds items to the tab in order of the list and no im not doing this better
						output.accept(PPBlocks.POWERED_FILTER_IRON.get());
						output.accept(PPBlocks.POWERED_FILTER_GOLD.getItem());
						output.accept(PPBlocks.POWERED_FILTER_DIAMOND.getItem());
						output.accept(PPBlocks.POWERED_FILTER_VOID.getItem());
						output.accept(PPBlocks.INFINITE_FILTER.getItem());
						output.accept(PPBlocks.INCINERATOR.get());
						output.accept(PPBlocks.POLLUTION_DELETER.getItem());
						output.accept(PPBlocks.POLLUTION_PUMP.getItem());
						output.accept(PPBlocks.POLLUTION_PIPE.getItem());
						output.accept(PPItems.LOCATION_MARKER.get());
						output.accept(PPItems.VOID_INGOT.get());
						output.accept(PPItems.FILTER_CORE.get());
						output.accept(PPItems.FILTER_CORE_WEAK.get());
						output.accept(PPItems.FAN.get());
					})
					.build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TAB.register(eventBus);
	}
}
