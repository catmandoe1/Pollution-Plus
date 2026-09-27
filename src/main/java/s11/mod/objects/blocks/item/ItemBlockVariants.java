package s11.mod.objects.blocks.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockVariants extends ItemBlock {

	public ItemBlockVariants(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
