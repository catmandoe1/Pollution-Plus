package s11.mod.config;

import net.minecraft.item.Item;
import s11.mod.Main;

public class ValidateInfiniteFilterItem {
	public static boolean isFilterItemValid() {
		Item item = Item.getByNameOrId(PollutionPlusConfig.Machines.infiniteFilterItem);
		if (item == null) {
			Main.logger.error("Unrecognised infinite filter item! - {}", PollutionPlusConfig.Machines.infiniteFilterItem);
			return false;
		}
		Main.logger.debug("Infinite filter item: {}", item.getRegistryName().toString());
		
		return true;
	}
	
	public static Item getFilterItem() {
		return Item.getByNameOrId(PollutionPlusConfig.Machines.infiniteFilterItem);
	}
}
