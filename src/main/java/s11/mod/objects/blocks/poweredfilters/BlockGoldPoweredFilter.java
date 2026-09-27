package s11.mod.objects.blocks.poweredfilters;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.Main;
import s11.mod.config.PollutionPlusConfig;
import s11.mod.objects.tileEntities.powered_filters.TileGoldPoweredFilter;
import s11.mod.util.PlayerPressing;
import s11.mod.util.TextHelper;

public class BlockGoldPoweredFilter extends BlockPoweredFilterBase {
	
	public BlockGoldPoweredFilter(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, resistance, resistance, harvestTool, harvestLevel);
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileGoldPoweredFilter();
	}

	@Override
	protected int getPowerUse() {
		return PollutionPlusConfig.PoweredFilters.gold.filterPowerUse;
	}

	@Override
	protected int getFilterSpeed() {
		return PollutionPlusConfig.PoweredFilters.gold.filterSpeed;
	}
}
