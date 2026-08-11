package com.catmandoe1.pollutionplus;

import com.catmandoe1.pollutionplus.gui.container.ContainerPollutionDeleter;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PPContainers {
	public static void init(IEventBus bus) {
		CONTAINERS.register(bus);
	}

	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MODID);

	public static final RegistryObject<MenuType<ContainerPollutionDeleter>> POLLUTION_DELETER = CONTAINERS.register("pollution_deleter", () -> IForgeMenuType.create(ContainerPollutionDeleter::new));
}
