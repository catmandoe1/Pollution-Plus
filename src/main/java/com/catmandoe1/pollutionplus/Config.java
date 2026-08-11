package com.catmandoe1.pollutionplus;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

//    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
//            .comment("Whether to log the dirt block on common setup")
//            .define("logDirtBlock", true);
//
//    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
//            .comment("A magic number")
//            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
//
//    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
//
//    // a list of strings that are treated as resource locations for items
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

	/**
	 * incinerator config
	 */
	private static final ForgeConfigSpec.IntValue INCINERATOR_POWERUSE = BUILDER
			.comment("The amount of rf the machine will use per use.")
			.defineInRange("incineratorPowerUse", 250000, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue INCINERATOR_MAXCAP = BUILDER
			.comment("The maximum amount of rf the incinerator can hold.")
			.defineInRange("incineratorMaxCapacity", 250000, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue INCINERATOR_WORK_SPEED = BUILDER
			.comment("The delay in ticks between activations. (20 ticks = 1sec)")
			.defineInRange("incineratorWorkSpeed", 200, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue INCINERATOR_WORK_RANGE = BUILDER
			.comment("The distance from the incinerator that pollution will get deleted. Higher values are really performance expensive: 8 = 512 checks, 20 = 8000 checks ...")
			.worldRestart()
			.defineInRange("incineratorWorkRange", 5, 1, 1290);

	private static final ForgeConfigSpec.BooleanValue INCINERATOR_DELETE_MOVING_POLLUTION = BUILDER
			.comment("If pollution entities (floating pollution) will get incinerated.")
			.define("incineratorDeleteMovingPollution", true);

	/**
	 * iron powered filter config
	 */
	private static final ForgeConfigSpec.IntValue POWERED_FILTER_IRON_POWER_USE = BUILDER
			.comment("The amount of rf the filter will use per tick.")
			.defineInRange("poweredFilterIronPowerUse", 100, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_IRON_POWERCAP = BUILDER
			.comment("The amount of rf the filter can store.")
			.defineInRange("poweredFilterIronPowerCapacity", 100000, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_IRON_SPEED = BUILDER
			.comment("The amount of ticks between deleting pollution inside of it.")
			.defineInRange("poweredFilterIronSpeed", 200, 0, Integer.MAX_VALUE);

	/**
	 * gold powered filter config
	 */
	private static final ForgeConfigSpec.IntValue POWERED_FILTER_GOLD_POWER_USE = BUILDER
			.comment("The amount of rf the filter will use per tick.")
			.defineInRange("poweredFilterGoldPowerUse", 250, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_GOLD_POWERCAP = BUILDER
			.comment("The amount of rf the filter can store.")
			.defineInRange("poweredFilterGoldPowerCapacity", 100000, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_GOLD_SPEED = BUILDER
			.comment("The amount of ticks between deleting pollution inside of it.")
			.defineInRange("poweredFilterGoldSpeed", 120, 0, Integer.MAX_VALUE);


	/**
	 * diamond powered filter config
	 */
	private static final ForgeConfigSpec.IntValue POWERED_FILTER_DIAMOND_POWER_USE = BUILDER
			.comment("The amount of rf the filter will use per tick.")
			.defineInRange("poweredFilterDiamondPowerUse", 400, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_DIAMOND_POWERCAP = BUILDER
			.comment("The amount of rf the filter can store.")
			.defineInRange("poweredFilterDiamondPowerCapacity", 100000, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_DIAMOND_SPEED = BUILDER
			.comment("The amount of ticks between deleting pollution inside of it.")
			.defineInRange("poweredFilterDiamondSpeed", 50, 0, Integer.MAX_VALUE);


	/**
	 * void powered filter config
	 */
	private static final ForgeConfigSpec.IntValue POWERED_FILTER_VOID_POWER_USE = BUILDER
			.comment("The amount of rf the filter will use per tick.")
			.defineInRange("poweredFilterVoidPowerUse", 500, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_VOID_POWERCAP = BUILDER
			.comment("The amount of rf the filter can store.")
			.defineInRange("poweredFilterVoidPowerCapacity", 100000, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POWERED_FILTER_VOID_SPEED = BUILDER
			.comment("The amount of ticks between deleting pollution inside of it.")
			.defineInRange("poweredFilterVoidSpeed", 10, 0, Integer.MAX_VALUE);


	/**
	 * infinite filter
	 */
	private static final ForgeConfigSpec.ConfigValue<String> INFINITE_FILTER_ITEM = BUILDER
			.comment("The item required to activate the infinite filter.")
			.worldRestart()
			.define("infinteFilterActivationItem", "minecraft:nether_star", Config::validateItemName);

	/**
	 * pollution pump
	 */
	private static final ForgeConfigSpec.IntValue POLLUTION_PUMP_POWERCAP = BUILDER
			.comment("The amount of rf the pump can store.")
			.defineInRange("pollutionPumpPowerCapacity", 1000, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POLLUTION_PUMP_POWER_USE = BUILDER
			.comment("The amount of rf per tick the pump uses.")
			.defineInRange("pollutionPumpPowerUse", 5, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POLLUTION_PUMP_WORK_SPEED = BUILDER
			.comment("The amount of ticks between pumping pollution.")
			.defineInRange("pollutionPumpWorkSpeed", 60, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POLLUTION_PUMP_RANGE = BUILDER
			.comment("The amount of rf the pump can store.")
			.defineInRange("pollutionPumpRange", 5, 0, Integer.MAX_VALUE);

	/**
	 * pollution deleter
	 */
	private static final ForgeConfigSpec.IntValue POLLUTION_DELETER_POWERCAP = BUILDER
			.comment("The amount of rf the deleter can store.")
			.defineInRange("pollutionDeleterPowerCapacity", 10000, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POLLUTION_DELETER_POWER_USE = BUILDER
			.comment("The amount of rf per tick the deleter uses.")
			.defineInRange("pollutionDeleterPowerUse", 1000, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POLLUTION_DELETER_WORK_SPEED = BUILDER
			.comment("The amount of ticks between deleting pollution.")
			.defineInRange("pollutionDeleterWorkSpeed", 2, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue POLLUTION_DELETER_HORIZONTAL_LIMIT = BUILDER
			.comment("The max width of the area the deleter can delete pollution. (doesnt limit y value)")
			.defineInRange("pollutionDeleterHorizontalLimit", 16, 1, 32767);




	static final ForgeConfigSpec SPEC = BUILDER.build();

//    public static boolean logDirtBlock;
//    public static int magicNumber;
//    public static String magicNumberIntroduction;
//    public static Set<Item> items;

	// incinerator
	public static int incineratorPowerUse;
	public static int incineratorMaxCapacity;
	public static int incineratorWorkSpeed;
	public static int incineratorWorkRange;
	public static boolean incineratorDeleteMovingPollution;

	// Iron powered filter
	public static int poweredFilterIronPowerUse;
	public static int poweredFilterIronPowerCapacity;
	public static int poweredFilterIronSpeed;

	// Gold powered filter
	public static int poweredFilterGoldPowerUse;
	public static int poweredFilterGoldPowerCapacity;
	public static int poweredFilterGoldSpeed;

	// Diamond powered filter
	public static int poweredFilterDiamondPowerUse;
	public static int poweredFilterDiamondPowerCapacity;
	public static int poweredFilterDiamondSpeed;

	// Void powered filter
	public static int poweredFilterVoidPowerUse;
	public static int poweredFilterVoidPowerCapacity;
	public static int poweredFilterVoidSpeed;

	// infinite filter
	public static Item infiniteFilterActivationItem;

	// pollution pump
	public static int pollutionPumpPowerCapacity;
	public static int pollutionPumpPowerUse;
	public static int pollutionPumpWorkSpeed;
	public static int pollutionPumpRange;

	// pollution deleter
	public static int pollutionDeleterPowerCapacity;
	public static int pollutionDeleterPowerUse;
	public static int pollutionDeleterWorkSpeed;
	public static int pollutionDeleterHorizontalLimit;

	private static boolean validateItemName(final Object obj) {
		return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
	}

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		incineratorPowerUse = INCINERATOR_POWERUSE.get();
		incineratorMaxCapacity = INCINERATOR_MAXCAP.get();
		incineratorWorkSpeed = INCINERATOR_WORK_SPEED.get();
		incineratorWorkRange = INCINERATOR_WORK_RANGE.get();
		incineratorDeleteMovingPollution = INCINERATOR_DELETE_MOVING_POLLUTION.get();

		// Iron powered filter
		poweredFilterIronPowerUse = POWERED_FILTER_IRON_POWER_USE.get();
		poweredFilterIronPowerCapacity = POWERED_FILTER_IRON_POWERCAP.get();
		poweredFilterIronSpeed = POWERED_FILTER_IRON_SPEED.get();

		// Gold powered filter
		poweredFilterGoldPowerUse = POWERED_FILTER_GOLD_POWER_USE.get();
		poweredFilterGoldPowerCapacity = POWERED_FILTER_GOLD_POWERCAP.get();
		poweredFilterGoldSpeed = POWERED_FILTER_GOLD_SPEED.get();

		// Diamond powered filter
		poweredFilterDiamondPowerUse = POWERED_FILTER_DIAMOND_POWER_USE.get();
		poweredFilterDiamondPowerCapacity = POWERED_FILTER_DIAMOND_POWERCAP.get();
		poweredFilterDiamondSpeed = POWERED_FILTER_DIAMOND_SPEED.get();

		// Void powered filter
		poweredFilterVoidPowerUse = POWERED_FILTER_VOID_POWER_USE.get();
		poweredFilterVoidPowerCapacity = POWERED_FILTER_VOID_POWERCAP.get();
		poweredFilterVoidSpeed = POWERED_FILTER_VOID_SPEED.get();

		// infinite filter
		infiniteFilterActivationItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(INFINITE_FILTER_ITEM.get()));

		// pollution pump
		pollutionPumpPowerCapacity = POLLUTION_PUMP_POWERCAP.get();
		pollutionPumpPowerUse = POLLUTION_PUMP_POWER_USE.get();
		pollutionPumpWorkSpeed = POLLUTION_PUMP_WORK_SPEED.get();
		pollutionPumpRange = POLLUTION_PUMP_RANGE.get();

		// pollution deleter
		pollutionDeleterPowerCapacity = POLLUTION_DELETER_POWERCAP.get();
		pollutionDeleterPowerUse = POLLUTION_DELETER_POWER_USE.get();
		pollutionDeleterWorkSpeed = POLLUTION_DELETER_WORK_SPEED.get();
		pollutionDeleterHorizontalLimit = POLLUTION_DELETER_HORIZONTAL_LIMIT.get();

//        logDirtBlock = LOG_DIRT_BLOCK.get();
//        BUILDER.push("floor");
//        magicNumber = MAGIC_NUMBER.get();
//        BUILDER.pop();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
	}
}
