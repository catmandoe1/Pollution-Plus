package s11.mod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import s11.mod.util.Reference;

@Config(modid = Reference.MODID, name = "pollutionplus/power_infuser")
public class PowerInfuserConfig {
	
		@Config.Name("Power infuser operation cost")
		@Config.Comment("The amount of rf the machine will use per tick while running (Only changeable in main menu)")
		@Config.RangeInt(min = 0)
		@RequiresWorldRestart
		public static int infuserOperationCost = 1000;
		
		@Config.Name("Power infuser rf capacity")
		@Config.Comment("The maximum amount of rf the power infuser can hold (Only changeable in main menu)")
		@Config.RangeInt(min = 1)
		@RequiresWorldRestart
		public static int infuserMaxCapacity = 100000;
}
