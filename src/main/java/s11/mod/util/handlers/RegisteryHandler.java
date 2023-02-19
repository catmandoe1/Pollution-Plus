package s11.mod.util.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import s11.mod.Main;
import s11.mod.init.BlockInit;
import s11.mod.init.ItemInit;
import s11.mod.util.PollutionSounds;
import s11.mod.util.interfaces.HasModel;
import s11.mod.world.gen.WorldGenCustomOres;

@EventBusSubscriber
public class RegisteryHandler {
	public static List<Item> disabledItems = new ArrayList<Item>();
	public static List<Block> disabledBlocks = new ArrayList<Block>();
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		//event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
		
		event.getRegistry().registerAll(filterDisabledItem(ItemInit.ITEMS, disabledItems).toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(filterDisabledBlock(BlockInit.BLOCKS, disabledBlocks).toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for(Item item : ItemInit.ITEMS) {
			if(item instanceof HasModel) {
				((HasModel)item).registerModels();
			}
		}
		
		for(Block block : BlockInit.BLOCKS) {
			if(block instanceof HasModel) {
				((HasModel)block).registerModels();
			}
		}
	}
	
	public static void initRegistries() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
		PollutionSounds.registerSounds();
	}
	
	public static void otherRegisteries() {
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 50);
		TileEntityHandler.registerTileEntities();
	}
	
	public static List<Item> filterDisabledItem(List<Item> list, List<Item> filter) {
		List<Item> newList = new ArrayList<Item>();
		
		for (Item item : list) {
			if (!filter.contains(item)) {
				newList.add(item);
			}
		}
		
		return newList;
	}
	
	public static List<Block> filterDisabledBlock(List<Block> list, List<Block> filter) {
		List<Block> newList = new ArrayList<Block>();
		
		for (Block block : list) {
			if (!filter.contains(block)) {
				newList.add(block);
			}
		}
		
		return newList;
	}
}
