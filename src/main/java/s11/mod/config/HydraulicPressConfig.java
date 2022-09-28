package s11.mod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import s11.mod.util.Reference;

@Config(modid = Reference.MODID, name = "pollutionplus/hydraulic_press")
public class HydraulicPressConfig {
	@Config.Name("Hydraulic press operation cost")
	@Config.Comment("The amount of rf the machine will use per tick while running (Only changeable in main menu)")
	@Config.RangeInt(min = 0)
	@RequiresWorldRestart
	public static int OperationCost = 100;
	
	@Config.Name("Hydraulic press rf capacity")
	@Config.Comment("The maximum amount of rf the Hydraulic press can hold (Only changeable in main menu)")
	@Config.RangeInt(min = 1)
	@RequiresWorldRestart
	public static int MaxCapacity = 10000;
}