package com.catmandoe1.pollutionplus.block;

import com.catmandoe1.pollutionplus.item.PPItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class  RegisteredBlock<B extends Block, T extends BlockEntity> implements Supplier<B> {
	public String name;
	private RegistryObject<BlockItem> item;
	private RegistryObject<B> block;
	private RegistryObject<BlockEntityType<T>> tileEntity;

	public RegisteredBlock(String name, Supplier<B> block) {
		this.block = PPBlocks.BLOCKS.register(name, block);
		//this.item = (RegistryObject<I>) PPBlocks.registerBlockItem(name, this.block);
		this.item = PPItems.ITEMS.register(name, () -> new BlockItem(this.block.get(), new Item.Properties()));
	}

	public RegisteredBlock(String name, Supplier<B> block, BlockEntityType.BlockEntitySupplier<T> tileEntity) {
		this.block = PPBlocks.BLOCKS.register(name, block);
		//this.item = (RegistryObject<I>) PPBlocks.registerBlockItem(name, this.block);
		this.item = PPItems.ITEMS.register(name, () -> new BlockItem(this.block.get(), new Item.Properties()));
		this.tileEntity = PPBlocks.TILE_ENTITIES.register(name, () -> BlockEntityType.Builder.of(tileEntity, this.block.get()).build(null));
	}

	@Override
	public B get() {
		return block.get();
	}

	@Nonnull
	public B getBlock() {
		return this.get();
	}

	@Nonnull
	public Item getItem() {
		return this.getBlockItem().asItem();
	}

	@Nonnull
	public BlockItem getBlockItem() {
		return this.item.get();
	}

	public boolean isTileEntity() {
		return this.tileEntity != null;
	}

	public BlockEntityType<T> getTileEntityType() {
		return this.tileEntity.get();
	}
}
