package com.catmandoe1.pollutionplus;

import com.catmandoe1.pollutionplus.block.PPBlocks;
import com.catmandoe1.pollutionplus.datagen.Generator;
import com.catmandoe1.pollutionplus.gui.screen.ScreenPollutionDeleter;
import com.catmandoe1.pollutionplus.item.PPItems;
import com.catmandoe1.pollutionplus.network.PPNetwork;
import com.catmandoe1.pollutionplus.tabs.PPCreativeModeTabs;
import com.mojang.logging.LogUtils;
//import net.catmandoe1.pollutionplus.block.PPBlocks;
//import net.catmandoe1.pollutionplus.item.PPItems;
//import net.catmandoe1.pollutionplus.tabs.PPCreativeModeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "pollutionplus";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();
	public static SimpleChannel NETWORK_WRAPPER;

	public Main() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		PPCreativeModeTabs.register(modEventBus); // register creative tab
		PPItems.register(modEventBus); // register items
		PPBlocks.register(modEventBus); // register blocks
		PPSounds.register(modEventBus);
		PPContainers.init(modEventBus);
		PPRecipes.init(modEventBus);

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		// Register the item to a creative tab
		modEventBus.addListener(this::addCreative);

		// Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

		// for runData run config - TODO completely broken!
		modEventBus.addListener(Generator::gatherData);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		// Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//
//      if (Config.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
		NETWORK_WRAPPER = PPNetwork.getNetworkChannel();
	}

	// Add the example block item to the building blocks tab
	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			//event.accept(PPItems.VOID_INGOT);
		}
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
		// Do something when the server starts
		//LOGGER.info("HELLO from server starting");


	}

	// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			// Some client setup code
			//LOGGER.info("HELLO FROM CLIENT SETUP");
			//LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

			event.enqueueWork(
					() -> MenuScreens.register(PPContainers.POLLUTION_DELETER.get(), ScreenPollutionDeleter::new)
			);
		}
	}
}
