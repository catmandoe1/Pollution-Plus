package s11.mod.objects.tileEntities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.EnergyStorage;
import s11.mod.objects.blocks.unique.BlockIncinerator;

public class TileIncinerator extends TileEntity implements ITickable {
	private static final int maxCapacity = 5000000;
	private static final int maxReceive = 5000;
	private static final int maxTransfer = 5000;
	
	private final EnergyStorage energy = new EnergyStorage(maxCapacity, maxReceive);
	private boolean isPowered = false; //is saved to nbt
	private int counter = 0; //isn't saved
	private boolean firstStart = true; //isn't saved

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		System.out.println("i updated!");
		
		if (firstStart == true) {
			updateState(isPowered);
			firstStart = false;
		}
		
		if (counter == 100) {
			isPowered = !isPowered;
			updateState(isPowered);
			counter = 0;
		} else {
			counter++;
		}
	}
	
	private void updateState(boolean isPowered) {
		if (world.getBlockState(pos) != null) {
			world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockIncinerator.POWERED, isPowered));
		}
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("isActivated", isPowered);
		boolean x = world.getBlockState(pos).getValue(BlockIncinerator.POWERED);
		System.out.println(isPowered + " write nbt	" + x);
        return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		isPowered = compound.getBoolean("isActivated");
		//updateState(isPowered);
		counter = 0;
		System.out.println(isPowered + " read nbt");
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() != newSate.getBlock();
	}
}
