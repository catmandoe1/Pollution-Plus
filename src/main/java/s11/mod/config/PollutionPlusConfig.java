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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.util.Reference;

@EventBusSubscriber
public class PollutionPlusConfig {
	
	@LangKey("config.pollutionplus.general")
	@Config(modid = Reference.MODID, name = "pollutionplus/general")
	public static class GeneralConfig {
		
		
		@LangKey("config.pollutionplus.machine_sounds")
		public static MachineSounds machinesSounds = new MachineSounds(true);
		
		public static class MachineSounds {			
			@Name("Pollution Incinerator Sound")
			@Comment("Turns on and off all sounds for this machine")
			public boolean incineratorSound;

			MachineSounds(boolean incineratorSound) {
				this.incineratorSound = incineratorSound;
			}
		}
	}
	
	@LangKey("config.pollutionplus.machines")
	@Config(modid = Reference.MODID, name = "pollutionplus/machines")
	public static class Machines {
		@LangKey("config.pollutionplus.machines.incinerator")
		public static Incinerator incinerator = new Incinerator(2500000, 5000000, 200, 5, true);
		
		@LangKey("config.pollutionplus.machines.pollution_deleter")
		public static PollutionDeleter pollutionDeleter = new PollutionDeleter(10000, 1000, 2, 16);
		
		@LangKey("config.pollutionplus.machines.pollution_pump")
		public static PollutionPump pollutionPump = new PollutionPump(1000, 5, 60, 5);
		
		@LangKey("config.pollutionplus.machines.infinite_filter_item")
		@Comment("The item required to activate the infinite filter. (Must be id form)")
		@RequiresWorldRestart
		public static String infiniteFilterItem = "minecraft:nether_star";
		
		
		
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
			
			@Config.Name("Incinerator Work Range")
			@Comment("The distance from the incinerator that pollution will get deleted. Higher values are really performance expensive: 8 = 512 checks, 20 = 8000 checks ... (Only changeable in main menu)")
			@RangeInt(min = 1, max = 1290)
			@RequiresWorldRestart
			public int workRange;// = 5;
			
			@Name("Delete Pollution Entities")
			@Comment("If pollution entities (floating pollution) will get incinerated. (Only changeable in main menu)")
			@RequiresWorldRestart
			public boolean deleteMovingPollution;// = true;
			
			Incinerator(int powerUse, int maxCapacity, int workSpeed, int workRange, boolean deleteMovingPollution) {
				this.maxCapacity = maxCapacity;
				this.powerUse = powerUse;
				this.workSpeed = workSpeed;
				this.workRange = workRange;
				this.deleteMovingPollution = deleteMovingPollution;
			}
		}
		
		
		public static class PollutionPump {
			@Name("Power capacity")
			@Comment("The amount of rf the pump can store.")
			@RangeInt(min = 1)
			public int maxCapacity;
			
			@Name("Operation cost")
			@Comment("The amount of rf per tick the pump uses.")
			@RangeInt(min = 0)
			public int operationCost;
			
			@Name("Pump speed")
			@Comment("The amount of ticks between pumping pollution.")
			@RangeInt(min = 0)
			public int workSpeed;
			
			@Name("Pump Range")
			@Comment("The distance from the top pipe the pump pumps from.")
			@RangeInt(min = 1)
			public int workRange;
			
			public PollutionPump(int maxCapacity, int operationCost, int workSpeed, int workRange) {
				this.maxCapacity = maxCapacity;
				this.operationCost = operationCost;
				this.workSpeed = workSpeed;
				this.workRange = workRange;
			}
		}
		
		public static class PollutionDeleter {
			@Name("Power capacity")
			@Comment("The amount of rf the deleter can store.")
			@RangeInt(min = 1)
			public int maxCapacity;
			
			@Name("Operation cost")
			@Comment("The amount of rf per tick the deleter uses.")
			@RangeInt(min = 0)
			public int operationCost;
			
			@Name("Pump speed")
			@Comment("The amount of ticks between deleting pollution.")
			@RangeInt(min = 0)
			public int workSpeed;
			
			@Name("Horizontal range limit")
			@Comment("The max width of the area the deleter can delete pollution. (doesnt limit y value)")
			@RangeInt(min = 0, max = 32767)
			public int horizontalLimit;
			
			public PollutionDeleter(int maxCapacity, int operationCost, int workSpeed, int horizontalLimit) {
				this.maxCapacity = maxCapacity;
				this.operationCost = operationCost;
				this.workSpeed = workSpeed;
				this.horizontalLimit = horizontalLimit;
			}
		}
	}
	
	@LangKey("config.pollutionplus.powered_filters")
	@Config(modid = Reference.MODID, name = "pollutionplus/powered_filters")
	public static class PoweredFilters {
		@LangKey("config.pollutionplus.powered_filters.iron")
		public static PoweredFilter iron = new PoweredFilter(100, 10000, 200);
		@LangKey("config.pollutionplus.powered_filters.gold")
		public static PoweredFilter gold = new PoweredFilter(250, 10000, 120);
		@LangKey("config.pollutionplus.powered_filters.diamond")
		public static PoweredFilter diamond = new PoweredFilter(400, 10000, 50);
		@LangKey("config.pollutionplus.powered_filters.vvoid")
		public static PoweredFilter vvoid = new PoweredFilter(500, 10000, 10);
		
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
			@Comment("The amount of ticks between deleting the pollution inside the filter.")
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
