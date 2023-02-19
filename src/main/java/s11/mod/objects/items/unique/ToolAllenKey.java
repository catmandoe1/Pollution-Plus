package s11.mod.objects.items.unique;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import s11.mod.objects.items.ItemToolTipAuto;
import s11.mod.util.ArrayHelper;
import s11.mod.util.Print;

public class ToolAllenKey extends ItemToolTipAuto {

	public ToolAllenKey(String name) {
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {	
		int[] scanArea = {7, 7, 7}; // x, y, z area of the area that the tool scans
		
		//IBlockState[][] blockStructure2D = new IBlockState[3][3];
		int areaSize = ArrayHelper.maxIntInArray(scanArea);
		Block[][][] blockStructure3D = new Block[areaSize][areaSize][areaSize];
		
		System.out.println("hit block : " + world.getBlockState(pos));
		ITextComponent failMessage = (ITextComponent)new TextComponentTranslation("chat.allen_key.fail_message");
		ITextComponent successMessage = (ITextComponent)new TextComponentTranslation("chat.allen_key.success_message");
		player.swingArm(hand);
		
		EnumFacing facing = side.getOpposite();
		int blockX = pos.getX();
		int blockY = pos.getY();
		int blockZ = pos.getZ();
		
		// offsets are getting the starting cords to the bottom left of the scan area
		int xOffset = (scanArea[0]) / 2;
		int yOffset = (scanArea[1] - 1) / 2;
		int zOffset = (scanArea[2] - 1) / 2;
		
		blockY -= yOffset;
		
		// 3d area around where clicked
		if (facing == EnumFacing.NORTH) {
			blockX -= xOffset;
			for (int y = 0; y < scanArea[1]; y++) {
				for (int x = 0; x < scanArea[0]; x++) {
					for (int z = 0; z < scanArea[2]; z ++) { 
						blockStructure3D[y][x][z] = world.getBlockState(new BlockPos(blockX + x, blockY + y, blockZ - z)).getBlock();
					}
				}
			}
		} else if (facing == EnumFacing.SOUTH) {
			blockX += xOffset;
			for (int y = 0; y < scanArea[1]; y++) {
				for (int x = 0; x < scanArea[0]; x++) {
					for (int z = 0; z < scanArea[2]; z ++) {
						blockStructure3D[y][x][z] = world.getBlockState(new BlockPos(blockX - x, blockY + y, blockZ + z)).getBlock();
					}
				}
			}	
		} else if (facing == EnumFacing.EAST) {
			blockZ -= zOffset;
			for (int y = 0; y < scanArea[1]; y++) {
				for (int z = 0; z < scanArea[2]; z++) {
					for (int x = 0; x < scanArea[0]; x++) {
						blockStructure3D[y][z][x] = world.getBlockState(new BlockPos(blockX + x, blockY + y, blockZ + z)).getBlock();
					}
				}
			}
		} else if (facing == EnumFacing.WEST) {
			blockZ += zOffset;
			for (int y = 0; y < scanArea[1]; y++) {
				for (int z = 0; z < scanArea[2]; z++) {
					for (int x = 0; x < scanArea[0]; x++) {
						blockStructure3D[y][z][x] = world.getBlockState(new BlockPos(blockX - x, blockY + y, blockZ - z)).getBlock();
					}
				}
			}
		}
		
		
		Print.print("blocks found:");
		Print.printBlockArray(blockStructure3D);
		
		
		
		player.sendMessage(failMessage);	
		return EnumActionResult.PASS;
	}
	
	@Override
	public Item setNoRepair() {
		// TODO Auto-generated method stub
		return super.setNoRepair();
	}
	
	
	/**
	 * uses out dated version
	 */
	@Override
	public int getItemStackLimit() {
		return 1;
	}
	
	@Override
	public boolean isDamageable() {
		return super.isDamageable();
	}
	
	/**
	 * uses out dated version
	 */
	@Override
	public int getMaxDamage() {
		return 3;
	}
}
