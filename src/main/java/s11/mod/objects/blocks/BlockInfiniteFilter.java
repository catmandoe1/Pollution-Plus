package s11.mod.objects.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.config.ValidateInfiniteFilterItem;
import s11.mod.objects.blocks.poweredfilters.BlockPoweredFilterBase;
import s11.mod.objects.tileEntities.TileInfiniteFilter;
import s11.mod.util.PlayerPressing;
import s11.mod.util.PollutionSounds;

public class BlockInfiniteFilter extends BlockPoweredFilterBase {

	public BlockInfiniteFilter(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, hardness, resistance, harvestTool, harvestLevel);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileInfiniteFilter();
	}
	
	private static void playWorldSound(World world, BlockPos pos) {
		world.playSound(null, pos, PollutionSounds.BLOCK_INFINITE_FILTER_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		// if not filter not active and config has a valid filter item
		if (!state.getValue(this.ACTIVE) && ValidateInfiniteFilterItem.isFilterItemValid()) {
			// if held item is the activation item
			if (playerIn.getHeldItem(hand).getItem() == ValidateInfiniteFilterItem.getFilterItem()) {
				if (!worldIn.isRemote) {					
					this.playWorldSound(worldIn, pos);
					
					worldIn.setBlockState(pos, state.withProperty(this.ACTIVE, true)); // set block to active
					
					if (!playerIn.isCreative()) {
						playerIn.getHeldItem(hand).shrink(1); // use item
					}
				}
				
				return true; // consume
			}
		}
		
		return false; // return false is pass, true is consume
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isCrtlDown()) {
			if (ValidateInfiniteFilterItem.isFilterItemValid()) {
				tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.infinite_filter", I18n.format(ValidateInfiniteFilterItem.getFilterItem().getItemStackDisplayName(ValidateInfiniteFilterItem.getFilterItem().getDefaultInstance()))));				
			} else {
				// bad filter item set in config
				tooltip.add(TextFormatting.RED + TextFormatting.BOLD.toString() + I18n.format("tooltip.pollutionplus.infinite_filter.error"));
			}
		} else {
			tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.pollutionplus.hold_ctrl"));
		}
		//super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	protected int getPowerUse() {
		return 0;
	}

	@Override
	protected int getFilterSpeed() {
		return 0;
	}
	
}
