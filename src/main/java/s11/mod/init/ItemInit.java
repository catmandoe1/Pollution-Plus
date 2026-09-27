package s11.mod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import s11.mod.objects.items.ItemBase;
import s11.mod.objects.items.ItemLocationMarker;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final ItemLocationMarker LOCATION_MARKER = new ItemLocationMarker("location_marker");
	public static final Item INGOT_VOID = new ItemBase("ingot_void");
	public static final Item FILTER_CORE = new ItemBase("filter_core");
	public static final Item FILTER_CORE_WEAK = new ItemBase("filter_core_weak");
	public static final Item FAN = new ItemBase("fan");
}
