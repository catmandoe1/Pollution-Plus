package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import s11.mod.objects.items.unique.BindingAgent;
import s11.mod.objects.items.unique.DiamondSpeck;
import s11.mod.objects.items.unique.GoldDust;
import s11.mod.objects.items.unique.IronDust;
import s11.mod.objects.items.unique.PrimedBindingAgent;
import s11.mod.objects.items.unique.RefinedVoidClump;
import s11.mod.objects.items.unique.RefinedVoidIngot;
import s11.mod.objects.items.unique.RefinedVoidShard;
import s11.mod.objects.items.unique.RefinementGel;
import s11.mod.objects.items.unique.ToolChipper;
import s11.mod.objects.items.unique.VoidIngot;
import s11.mod.objects.items.unique.VoidShard;
import s11.mod.objects.items.unique.VoidStar;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	// Ingots
	public static final Item INGOT_VOID = new VoidIngot("ingot_void");
	public static final Item INGOT_REFINED_VOID = new RefinedVoidIngot("ingot_refined_void");
	
	// Shards
	public static final Item SHARD_VOID = new VoidShard("shard_void");
	public static final Item SHARD_REFINED_VOID = new RefinedVoidShard("shard_refined_void");
	
	// Stars
	public static final Item STAR_VOID = new VoidStar("star_void");
	
	// Dusts
	public static final Item DUST_IRON = new IronDust("dust_iron");
	public static final Item DUST_GOLD = new GoldDust("dust_gold");
	
	// Tiny dusts
	public static final Item SPECK_DIAMOND = new DiamondSpeck("speck_diamond");
	
	// Tools
	//public static final Item TOOL_CHIPPER = new ToolChipper("tool_chipper"); replaced my hydraulic press
	
	// Other
	public static final Item REFINED_CLUMP_VOID = new RefinedVoidClump("refined_clump_void");
	public static final Item BINDING_AGENT = new BindingAgent("binding_agent");
	public static final Item PRIMED_BINDING_AGENT = new PrimedBindingAgent("primed_binding_agent");
	public static final Item REFINEMENT_GEL = new RefinementGel("refinement_gel");
}
