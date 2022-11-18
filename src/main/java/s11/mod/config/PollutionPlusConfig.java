package s11.mod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import s11.mod.util.Reference;

@EventBusSubscriber
public class PollutionPlusConfig {
	
	@LangKey("config.general")
	@Config(modid = Reference.MODID, name = "pollutionplus/general")
	public static class GeneralConfig {
		
		@Config.Name("Machine volume")
		@Config.Comment("Turns off or on all machine volume (such as the incinerator zap etc.).")
		public static boolean machineVolume = true;
		
//		@Name("Disable Hydraulic Press")
//		public static boolean disableHydraulicPress = false;
//		
//		@Name("Disable Power Infuser")
//		public static boolean disablePowerInfuser = false;
//		
//		@Name("Disable Alloy Furnace")
//		public static boolean disableAlloyFurnace = false;
	}
	
	@LangKey("config.machines")
	@Config(modid = Reference.MODID, name = "pollutionplus/machines")
	public static class Machines {
		@LangKey("config.machines.hydraulic_press")
		public static HydraulicPress hydraulicPress = new HydraulicPress(100, 10000);
		@LangKey("config.machines.power_infuser")
		public static PowerInfuser powerInfuser = new PowerInfuser(1000, 1000000, 1200);
		@LangKey("config.machines.incinerator")
		public static Incinerator incinerator = new Incinerator(2500000, 5000000, 200);
		@LangKey("config.machines.alloy_furnace")
		public static AlloyFurnace alloyFurnace = new AlloyFurnace(100, 10000);
		
		
		//@LangKey("config.hydraulic_press")
		public static class HydraulicPress {
			
			@Config.Name("Hydraulic press operation cost")
			@Config.Comment("The amount of rf the machine will use per tick while running.")
			@Config.RangeInt(min = 0)
			public int operationCost; //= 100;
			
			@Config.Name("Hydraulic press rf capacity")
			@Config.Comment("The maximum amount of rf the Hydraulic press can hold.")
			@Config.RangeInt(min = 1)
			public int maxCapacity; //= 10000;
			
			HydraulicPress(int operationCost, int maxCapacity) {
				this.maxCapacity = maxCapacity;
				this.operationCost = operationCost;
			}
		}
		
		public static class PowerInfuser {
			@Config.Name("Power infuser base operation cost")
			@Config.Comment("The base amount of rf the machine will use per tick while running.")
			@Config.RangeInt(min = 0)
			public int baseOperationCost; //= 1000;
			
			@Name("Power infuser infusing speed")
			@Comment("The speed in which the infuser infuses a item at (ticks)")
			@RangeInt(min = 1)
			public int speed;
			
			@Config.Name("Power infuser rf capacity")
			@Config.Comment("The maximum amount of rf the power infuser can hold.")
			@Config.RangeInt(min = 1)
			public int maxCapacity; //= 100000;
			PowerInfuser(int baseOperationCost, int maxCapacity, int speed) {
				this.maxCapacity = maxCapacity;
				this.baseOperationCost = baseOperationCost;
				this.speed = speed;
			}
		}
		
		public static class Incinerator {
			@Config.Name("Incinerator operation cost")
			@Config.Comment("The amount of rf the machine will use per pollutant deletion cycle.")
			@Config.RangeInt(min = 0)
			public int powerUse; //2500000;
			
			@Config.Name("Incinerator storage capacity")
			@Config.Comment("The maximum amount of rf the incinerator can hold.")
			@Config.RangeInt(min = 1)
			public int maxCapacity; //= 5000000;
			
			@Config.Name("Incinerator Work Speed")
			@Config.Comment("E.g. the incinerator works every 10 seconds when this is set to 200 (ticks).")
			@Config.RangeInt(min = 1)
			public int workSpeed; //= 200;
			
			Incinerator(int powerUse, int maxCapacity, int workSpeed) {
				this.maxCapacity = maxCapacity;
				this.powerUse = powerUse;
				this.workSpeed = workSpeed;
			}
		}
		
		public static class AlloyFurnace {
			@Name("Alloy furnace operation cost")
			@Comment("The amount of rf the machine will use per tick while running.")
			@RangeInt(min = 0)
			public int operationCost = 100;
			
			@Config.Name("Alloy furnace rf capacity")
			@Config.Comment("The maximum amount of rf the alloy furnace can hold.")
			@Config.RangeInt(min = 1)
			public int maxCapacity = 10000;
			
			AlloyFurnace(int operationCost, int maxCapacity) {
				this.maxCapacity = maxCapacity;
				this.operationCost = operationCost;
			}
		}
	}
	
	@LangKey("config.powered_filters")
	@Config(modid = Reference.MODID, name = "pollutionplus/powered_filters")
	public static class PoweredFilters {
		@LangKey("config.powered_filters.iron")
		public static PoweredFilter iron = new PoweredFilter(500, 100000, 50);
		@LangKey("config.powered_filters.gold")
		public static PoweredFilter gold = new PoweredFilter(400, 100000, 30);
		@LangKey("config.powered_filters.diamond")
		public static PoweredFilter diamond = new PoweredFilter(250, 100000, 20);
		@LangKey("config.powered_filters.vvoid")
		public static PoweredFilter vvoid = new PoweredFilter(100, 100000, 1);
		
		public static class PoweredFilter {
			
			@Name("Filter Power Use")
			@Comment("The amount of rf the filter will use per tick.")
			@RangeInt(min = 0)
			@Config.RequiresWorldRestart
			public int filterPowerUse;
			
			@Name("Filter Power Capacity")
			@Comment("The amount of rf the filter can store.")
			@RangeInt(min = 1)
			@Config.RequiresWorldRestart
			public int filterMaxCapacity;
			
			@Name("Filter's Filtration Speed")
			@Comment("The amount of ticks before the filter \"filters\" the pollution in it.")
			@RangeInt(min = 1)
			@Config.RequiresWorldRestart
			public int filterSpeed;
			
			/**
			 * 
			 * @param filterPowerUse (rf)
			 * @param filterMaxCapacity (rf)
			 * @param filterSpeed (ticks)
			 */
			PoweredFilter(int filterPowerUse, int filterMaxCapacity, int filterSpeed) {
				this.filterPowerUse = filterPowerUse;
				this.filterMaxCapacity = filterMaxCapacity;
				this.filterSpeed = filterSpeed;
			}
		}
	}
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent ev) {
		if (ev.getModID().equals(Reference.MODID)) {
			ConfigManager.sync(Reference.MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
		}
	}
}
