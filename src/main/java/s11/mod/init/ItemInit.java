package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import s11.mod.objects.items.ItemBase;
import s11.mod.objects.items.ItemInfused;
import s11.mod.objects.items.ItemToolTip;
import s11.mod.objects.items.ItemToolTipAuto;
import s11.mod.objects.items.unique.VoidStar;
import s11.mod.util.TextHelper;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	// Ingots
	public static final Item INGOT_VOID = new ItemBase("ingot_void");
	public static final Item INGOT_REFINED_VOID = new ItemBase("ingot_refined_void");
	public static final Item INGOT_STEEL = new ItemBase("ingot_steel");
	
	// Shards
	public static final Item SHARD_VOID = new ItemBase("shard_void");
	public static final Item SHARD_REFINED_VOID = new ItemBase("shard_refined_void");
		
	// Dusts
	public static final Item DUST_IRON = new ItemBase("dust_iron");
	public static final Item DUST_GOLD = new ItemBase("dust_gold");
	
	// Tiny dusts
	public static final Item SPECK_DIAMOND = new ItemBase("speck_diamond");
	
	// Tools
	//public static final Item TOOL_CHIPPER = new ToolChipper("tool_chipper"); replaced by hydraulic press (ish)
	
	// Infused
	public static final Item STAR_VOID = new VoidStar("star_void");
	public static final Item SHARD_VOID_INFUSED = new ItemInfused("shard_void_infused");
	public static final Item SHARD_REFINED_VOID_INFUSED = new ItemInfused("shard_refined_void_infused"); // aka infused refined void shard
	
	// Other
	public static final Item STAR_VOID_OFF = new ItemToolTipAuto("star_void_off");
	public static final Item REFINED_CLUMP_VOID = new ItemToolTipAuto("refined_clump_void");
	public static final Item BINDING_AGENT = new ItemToolTipAuto("binding_agent");
	public static final Item PRIMED_BINDING_AGENT = new ItemBase("primed_binding_agent");
	public static final Item REFINEMENT_GEL = new ItemBase("refinement_gel");
}
