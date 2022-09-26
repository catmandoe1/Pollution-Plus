package s11.mod.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;
import s11.mod.util.Reference;

@Config(modid = Reference.MODID, name = "pollutionplus/incinerator")
public class IncineratorConfig {
	@Config.Name("Incinerator operation cost")
	@Config.Comment("The amount of rf the machine will use per pollutant deletion cycle (Only changeable in main menu)")
	@Config.RangeInt(min = 0)
	@RequiresWorldRestart
	public static int incineratorPowerUse = 2500000;
	
	@Config.Name("Incinerator storage capacity")
	@Config.Comment("The maximum amount of rf the incinerator can hold (Only changeable in main menu)")
	@Config.RangeInt(min = 1)
	@RequiresWorldRestart
	public static int incineratorMaxCapacity = 5000000;
	
	@Config.Name("Incinerator Work Speed")
	@Config.Comment("E.g. the incinerator works every 10 seconds when this is set to 200 (ticks) (Only changeable in main menu)")
	@Config.RangeInt(min = 1)
	@RequiresWorldRestart
	public static int incineratorWorkSpeed = 200;
}
