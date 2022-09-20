package s11.mod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import s11.mod.util.Reference;

@Config(modid = Reference.MODID, name = "power infuser")
public class PowerInfuserConfig {
	
		@Config.Name("Power infuser operation cost")
		@Config.Comment("The amount of rf the machine will use per tick while running (Changeable in main menu)")
		@Config.RangeInt(min = 0)
		@RequiresWorldRestart
		public static int infuserOperationCost = 1;
		
		@Config.Name("Power infuser rf capacity")
		@Config.Comment("The maximum amount of rf the power infuser can hold (Changeable in main menu)")
		@Config.RangeInt(min = 0)
		@RequiresWorldRestart
		public static int infuserMaxCapacity = 10000;
}
