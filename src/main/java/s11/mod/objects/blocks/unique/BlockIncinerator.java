package s11.mod.objects.blocks.unique;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.objects.tileEntities.TileIncinerator;

public class BlockIncinerator extends BlockBase {
	public static final PropertyBool POWERED = PropertyBool.create("powered_incinerator");
	
	public BlockIncinerator(String name, Material material) {
		super(name, material);
		this.setHardness(3.0F);
		this.setResistance(10.0F);
		setDefaultState(this.blockState.getBaseState().withProperty(this.POWERED, true));;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, POWERED);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		System.out.println(state.getValue(POWERED));
		int i= state.getValue(POWERED) ? 1 : 0;
		System.out.println(i + " - meta from state");
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		System.out.println(meta + " - state from meta");
		return this.getDefaultState().withProperty(POWERED, meta != 0);
	}
	
//	@Override
//	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
//		System.out.println(meta + "getstateforplacement");
//		return getDefaultState().withProperty(POWERED, true);
//	}
	
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
