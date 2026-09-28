package s11.mod.objects.blocks.poweredfilters;

import java.util.List;

import com.endertech.minecraft.forge.api.ISmokeContainer;
import com.endertech.minecraft.forge.configs.ColorARGB;
import com.endertech.minecraft.forge.configs.ForgeConfig;
import com.endertech.minecraft.forge.math.AABB;
import com.endertech.minecraft.forge.units.UnitId;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.objects.blocks.BlockBase;
import s11.mod.util.PlayerPressing;
import s11.mod.util.TextHelper;

public abstract class BlockPoweredFilterBase extends BlockBase implements ISmokeContainer {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	static final List<AxisAlignedBB> AABB_WALLS = AABB.getWalls(0.125F); // cheers ender
	
	public BlockPoweredFilterBase(String name, Material material, float hardness, float resistance, String harvestTool, int harvestLevel) {
		super(name, material, hardness, resistance, harvestTool, harvestLevel);
		setDefaultState(blockState.getBaseState().withProperty(ACTIVE, false));
		setLightOpacity(0); // 0 opacity means 100% transparent (bit odd)

	}
	
	abstract protected int getPowerUse();
	abstract protected int getFilterSpeed();
	@Override
	public abstract TileEntity createTileEntity(World world, IBlockState state);
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (PlayerPressing.isLeftCrtlDown()) {
			tooltip.add(TextHelper.getLang("tooltip.pollutionplus.powered_filter.disc"));
			tooltip.add("");
			tooltip.add(TextFormatting.DARK_PURPLE + TextHelper.getLang("tooltip.pollutionplus.stats"));
			tooltip.add(TextFormatting.BLUE + "" + this.getPowerUse() + " RF/t");
			tooltip.add(TextFormatting.BLUE + "" + this.getFilterSpeed() + " " + TextHelper.getLang("tooltip.pollutionplus.cooldown"));
		} else {
			tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.pollutionplus.hold_ctrl"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(ACTIVE) ? 1 : 0;
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, (meta & 1) != 0);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return isSideSolid(state, worldIn, pos, face) ? BlockFaceShape.SOLID : BlockFaceShape.BOWL;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	
	
	
	/**
	 * credit to EnderLanky for this code!
	 */
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		for (AxisAlignedBB wall : AABB_WALLS) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, wall);
		}
		//super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
	}
	
	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
		return true;
	}
	
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		switch(side) {
			case DOWN:
			case UP:
				return false;
			default:
				return true;
		}
	}

	@Override
	public UnitId getRelatedId() {
		return null;
	}

	@Override
	public ForgeConfig getConfig() {
		return null;
	}

	@Override
	public ColorARGB getColor() {
		//return ColorARGB.from(-16121856); // #FF0A0000
		return ColorARGB.DEFAULT;
	}

	@Override
	public boolean isActive(World arg0, BlockPos arg1) {
		return true;
	}

	@Override
	public boolean isChimney(IBlockState arg0) {
		return true;
	}

	@Override
	public boolean isPump(IBlockState arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVent(IBlockState arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
