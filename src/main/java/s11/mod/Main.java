package s11.mod;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import s11.mod.proxy.CommonProxy;
import s11.mod.recipes.SmeltingInit;
import s11.mod.tabs.PollutionPlusTab;
import s11.mod.util.Reference;
import s11.mod.util.handlers.RegisteryHandler;
import s11.mod.util.oreDict.OreDictionaryRegister;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = Reference.DEPENDENCIES)
public class Main {
	@Instance
	public static Main instance;
	
	public static final CreativeTabs pollutionplustab = new PollutionPlusTab("pollutionplustab");
		
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		RegisteryHandler.otherRegisteries();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		RegisteryHandler.initRegistries();
		SmeltingInit.initSmelting();
		OreDictionaryRegister.registerOres();
	}
		
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}
}
