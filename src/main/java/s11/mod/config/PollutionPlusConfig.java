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
	
	@LangKey("config.general")
	@Config(modid = Reference.MODID, name = "pollutionplus/general")
	public static class GeneralConfig {
		
//		@Config.Name("All Machine Volume")
//		@Config.Comment("Turns off or on all machine volume (such as the incinerator zap etc.).")
//		public static boolean machineVolume = true;
		
		@RequiresWorldRestart
		@Name("Generate Void Ore")
		@Comment("Enables/Disables ore generation for void ore. Only works when generating new chunks or a new world (already spawned ore wont disappear).")
		public static boolean generateVoidOre = true;
		
		@LangKey("config.machine_sounds")
		public static MachineSounds machinesSounds = new MachineSounds(true, true, true, true, true);
		
		public static class MachineSounds {
			@Name("Hydraulic Press Sound")
			@Comment("Turns on and off all sounds for this machine")
			public boolean hydraulicPressSound;
			
			@Name("Power Infuser Sound")
			@Comment("Turns on and off all sounds for this machine")
			public boolean powerInfuserSound;
			
			@Name("Incinerator Sound")
			@Comment("Turns on and off all sounds for this machine")
			public boolean incineratorSound;
			
			@Name("Alloy Furnace Sound")
			@Comment("Turns on and off all sounds for this machine")
			public boolean alloyFurnaceSound;
			
			@Name("Discharger Sound")
			@Comment("Turns on and off all sounds for this machine")
			public boolean dischargerSound;

			MachineSounds(boolean hydraulicPressSound, boolean powerInfuserSound, boolean incineratorSound, boolean alloyFurnaceSound, boolean dischargerSound) {
				this.hydraulicPressSound = hydraulicPressSound; 
				this.powerInfuserSound = powerInfuserSound;
				this.incineratorSound = incineratorSound;
				this.alloyFurnaceSound = alloyFurnaceSound;
				this.dischargerSound = dischargerSound;
			}
		}
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
		@LangKey("config.machines.discharger")
		public static Discharger discharger = new Discharger(1000, 10000, 90, 2400000);
		@LangKey("config.machines.void_miner_controller")
		public static VoidMinerController voidMinerController = new VoidMinerController(35000, 2048, 20);
		
		
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
		
		public static class Discharger {
			@Name("Power extraction")
			@Comment("The amount of energy that the discharger extracts from the infused item per tick.")
			@RangeInt(min = 1, max = 10000000)
			public int energyExtraction;
			
			@Name("Max power output")
			@Comment("The amount of energy that can be outputted by the discharger.")
			@RangeInt(min = 0)
			public int energyOutput;
			
			@Name("Extraction efficiency")
			@Comment("Percentage of how much energy of the infused item it extracts and the extraction speed (%).")
			@RangeInt(min = 1, max = 100)
			public int extractionEfficiency; 
			
			@Name("Discharger rf capacity")
			@Comment("The capacity of the internal power storage.")
			@RangeInt(min = 1)
			public int maxCapacity;
			
			/**
			 * 
			 * @param energyExtraction
			 * @param energyOutput
			 * @param extractionEfficiency
			 * @param maxCapacity
			 */
			Discharger(int energyExtraction, int energyOutput, int extractionEfficiency, int maxCapacity) {
				this.energyExtraction = energyExtraction;
				this.energyOutput = energyOutput;
				this.extractionEfficiency = extractionEfficiency;
				this.maxCapacity = maxCapacity;
			}
		}
		
		public static class VoidMinerController {
			@Name("Controller storage capacity")
			@Comment("The maximum amount of rf the controller can hold.")
			@RangeInt(min = 1)
			public int maxCapacity;
			
			@Name("Controller operation cost")
			@Comment("The amount of rf the controller will use per tick while running.")
			@RangeInt(min = 0)
			public int operationCost;
			
			@Name("Controller work speed")
			@Comment("The delay between mines")
			@RangeInt(min = 0)
			public int workSpeed;
			
			/**
			 * 
			 * @param maxCapacity
			 * @param powerUse
			 * @param workSpeed
			 */
			public VoidMinerController(int maxCapacity, int powerUse, int workSpeed) {
				this.maxCapacity = maxCapacity;
				this.operationCost = powerUse;
				this.workSpeed = workSpeed;
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
