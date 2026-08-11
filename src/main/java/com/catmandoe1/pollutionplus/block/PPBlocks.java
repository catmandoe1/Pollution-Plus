package com.catmandoe1.pollutionplus.block;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.block.poweredFilters.*;
import com.catmandoe1.pollutionplus.block.pump.BlockPollutionPipe;
import com.catmandoe1.pollutionplus.block.pump.BlockPollutionPump;
import com.catmandoe1.pollutionplus.item.PPItems;
import com.catmandoe1.pollutionplus.tileentities.TileEntityIncinerator;
import com.catmandoe1.pollutionplus.tileentities.TileEntityInfiniteFilter;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionPump;
import com.catmandoe1.pollutionplus.tileentities.poweredFilters.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PPBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);

	// registers the block and then it as an item
	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> registryObject = BLOCKS.register(name, block);
		registerBlockItem(name, registryObject);
		return registryObject;
	}

	private static <B extends Block, T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerTileEntity(String name, Supplier<B> block, BlockEntityType.BlockEntitySupplier<T> tileEntity) {
		registerBlock(name, block);

		// now do tile entity
		return TILE_ENTITIES.register(name, () -> BlockEntityType.Builder.of(tileEntity, block.get()).build(null));
	}

	// used to create the item variate of a block
	public static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return PPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
		TILE_ENTITIES.register(eventBus);
	}

	//public static final RegistryObject<BlockIncinerator> INCINERATOR = registerBlock("incinerator", () -> new BlockIncinerator());

	// 1.20.1 is very good :)

	// (i want my 1.12.2 back)
	public static final RegisteredBlock<BlockIncinerator, TileEntityIncinerator> INCINERATOR = new RegisteredBlock<>(
			"incinerator",
			() -> new BlockIncinerator(),
			TileEntityIncinerator::new
	);

	public static final RegisteredBlock<BlockPoweredFilter, TileEntityPoweredFilter> POWERED_FILTER_IRON = new RegisteredBlock<>(
			"powered_filter_iron",
			() -> new BlockPoweredFilterIron(),
			TileEntityPoweredFilterIron::new
	);

	public static final RegisteredBlock<BlockPoweredFilter, TileEntityPoweredFilter> POWERED_FILTER_GOLD = new RegisteredBlock<>(
			"powered_filter_gold",
			() -> new BlockPoweredFilterGold(),
			TileEntityPoweredFilterGold::new
	);

	public static final RegisteredBlock<BlockPoweredFilter, TileEntityPoweredFilter> POWERED_FILTER_DIAMOND = new RegisteredBlock<>(
			"powered_filter_diamond",
			() -> new BlockPoweredFilterDiamond(),
			TileEntityPoweredFilterDiamond::new
	);

	public static final RegisteredBlock<BlockPoweredFilter, TileEntityPoweredFilter> POWERED_FILTER_VOID = new RegisteredBlock<>(
			"powered_filter_void",
			() -> new BlockPoweredFilterVoid(),
			TileEntityPoweredFilterVoid::new
	);

	public static final RegisteredBlock<BlockInfiniteFilter, TileEntityInfiniteFilter> INFINITE_FILTER = new RegisteredBlock<>(
			"infinite_filter",
			() -> new BlockInfiniteFilter(),
			TileEntityInfiniteFilter::new
	);

	public static RegisteredBlock<BlockPollutionPump, TileEntityPollutionPump> POLLUTION_PUMP = new RegisteredBlock<>(
		"pollution_pump",
		() -> new BlockPollutionPump(),
		TileEntityPollutionPump::new
	);

	public static RegisteredBlock<BlockPollutionPipe, ?> POLLUTION_PIPE = new RegisteredBlock<>(
			"pipe",
			() -> new BlockPollutionPipe()
	);

	public static RegisteredBlock<BlockPollutionDeleter, TileEntityPollutionDeleter> POLLUTION_DELETER = new RegisteredBlock<BlockPollutionDeleter, TileEntityPollutionDeleter>(
			"pollution_deleter",
			() -> new BlockPollutionDeleter(),
			TileEntityPollutionDeleter::new
	);
}
