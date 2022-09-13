package s11.mod.objects.blocks.unique;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.config.IncineratorConfig;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.tileEntities.TileIncinerator;
import s11.mod.util.PlayerPressing;

public class BlockIncinerator extends BlockBase {
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	//public static final PropertyBool HASREDSTONE = PropertyBool.create("has_redstone");
	
	public BlockIncinerator(String name, Material material) {
		super(name, material);
		this.setHardness(3.0F);
		this.setResistance(10.0F);
		setHarvestLevel("pickaxe", 0);
		setDefaultState(this.blockState.getBaseState().withProperty(this.POWERED, false));
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isCrtlDown()) {
			tooltip.add("Deletes all pollution around the incinerator in a 5 block radius for " + (float)(IncineratorConfig.incineratorPowerUse) / 1000000 + "MRF");
			tooltip.add(TextFormatting.RED + "Appling redstone power to the incinerator turns it off");
		} else {
			tooltip.add(TextFormatting.RED + "Hold Ctrl for help");
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i= state.getValue(POWERED) ? 1 : 0;
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, (meta & 1) != 0);
	}
	
/*	@Override
 *	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
 *		System.out.println(meta + "getstateforplacement");
 *		return getDefaultState().withProperty(POWERED, true);
 *	bits: 					0 0 = 0
 *	powered 				1 0 = 1
 * 	hasredstone 			0 1 = 2
 * 	powered and hasredstone	1 1 = 3
 * 
 * int i= state.getValue(POWERED) ? 1 : 0 | (state.getValue(HASREDSTONE) ? 2 : 0);
 * 		0111
		&
		0100
		
		0011
 *
 *	}
 * 
 */
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileIncinerator();
	}
}
