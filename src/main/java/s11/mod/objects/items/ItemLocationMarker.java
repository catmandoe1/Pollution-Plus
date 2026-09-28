package s11.mod.objects.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import s11.mod.Main;
import s11.mod.init.ItemInit;
import s11.mod.objects.blocks.BlockPollutionDeleter;
import s11.mod.objects.tileEntities.TilePollutionDeleter;
import s11.mod.sounds.PollutionSounds;

public class ItemLocationMarker extends ItemBase {

	public ItemLocationMarker(String name) {
		super(name);
	}

	
	@SideOnly(Side.CLIENT)
	private void playUseSound(World world, EntityPlayer player) {
		//world.playSound(1d, 2d, 3d, PollutionSounds.ITEM_LOCATION_MARKER_USE, SoundCategory.PLAYERS, 1f, 1f, 0f);
		world.playSound(player, player.getPosition(), PollutionSounds.ITEM_LOCATION_MARKER_USE, SoundCategory.PLAYERS, 1f, 1f);
	}
	
	public void doCooldown(EntityPlayer player) {
		NBTTagCompound nbt = player.getEntityData();
		nbt.setLong("pollutionPluslocationMarkerCooldown", player.world.getTotalWorldTime() + 10);
	}
	
	public boolean isOnCooldown(EntityPlayer player) {
		NBTTagCompound nbt = player.getEntityData();
		if (nbt.hasKey("pollutionPluslocationMarkerCooldown")) {			
			return player.world.getTotalWorldTime() < nbt.getLong("pollutionPluslocationMarkerCooldown");
		}
		return false; // if theres no tag then the marker has never been used before
	}
	
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
//		ItemStack heldStack = playerIn.getHeldItem(handIn);
//		RayTraceResult ray = this.rayTrace(worldIn, playerIn, true); // ray trace to see what block the player is looking at
//		
//		this.doCooldown(playerIn);
//		
//		if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
//			BlockPos clickedPos = ray.getBlockPos();
//			Block clickedBlock = worldIn.getBlockState(clickedPos).getBlock();
//			
//			// just play sound for client and return
//			if (worldIn.isRemote) {
//				this.playUseSound(worldIn, playerIn);
//				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldStack);
//			}
//			
//		}
//		return new ActionResult<ItemStack>(EnumActionResult.PASS, heldStack);
//	}
//	
	
	// this is use(), when item is clicked. not called if onItemUseFirst() is a success
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		ItemStack item = player.getHeldItem(handIn);
		if (!player.isSneaking() || this.isOnCooldown(player)) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, item);
		}
		
		this.doCooldown(player);
		// play sound for client
		if (world.isRemote) {
			this.playUseSound(world, player);
		}
		
		if (!world.isRemote) {
			ItemStack newItem = new ItemStack(ItemInit.LOCATION_MARKER);
			
			// add player position to new item
			newItem.getOrCreateSubCompound("clickedPos");
			BlockPos target = player.getPosition();
			newItem.getTagCompound().setLong("clickedPos", target.toLong());
			
			player.sendStatusMessage(new TextComponentTranslation("actionbar.pollutionplus.location_marker.set", target.getX(), target.getY(), target.getZ()), true);
			
			player.setHeldItem(handIn, newItem);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);
	}
	
	// this is useOn(), when a block is right clicked
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (this.isOnCooldown(player) ) {
			return EnumActionResult.PASS;
		}
		
		// if pollution deleter was right clicked
		if (world.getBlockState(pos).getBlock() instanceof BlockPollutionDeleter && this.hasLocationSaved(player.getHeldItem(hand))) {
			this.doCooldown(player);
			if (world.isRemote) {
				this.playUseSound(world, player);
				return EnumActionResult.SUCCESS;
			}

			// get tile entity
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof TilePollutionDeleter) {
				TilePollutionDeleter deleter = (TilePollutionDeleter)tileEntity;
				
				ItemStack heldItemStack = player.getHeldItem(hand);
				
				// if item has position saved
				if (heldItemStack.hasTagCompound() && heldItemStack.getTagCompound().hasKey("clickedPos")) {
					BlockPos savedPos = BlockPos.fromLong(heldItemStack.getTagCompound().getLong("clickedPos"));
					Main.logger.debug("location marker saved location: " + savedPos.toString());

					if (player.isSneaking()) {
						deleter.setDeletionAreaBB(savedPos); // set second position
						player.sendStatusMessage(new TextComponentTranslation("actionbar.pollutionplus.location_marker.applyBB"), true);
					} else {
						deleter.setDeletionAreaAA(savedPos); // set first position
						player.sendStatusMessage(new TextComponentTranslation("actionbar.pollutionplus.location_marker.applyAA"), true);
					}
					return EnumActionResult.SUCCESS;
				}

				// if shift clicked
			}
		}

		// handle right clicking other blocks
		if (!player.isSneaking()) {
			// player must be sneaking to save a location
			return EnumActionResult.PASS;
		}
		this.doCooldown(player);

		if (world.isRemote) {
			this.playUseSound(world, player);
			return EnumActionResult.SUCCESS; // important for not activated use()
		}


		ItemStack newItem = new ItemStack(ItemInit.LOCATION_MARKER);

		// add clicked location to new item
		newItem.getOrCreateSubCompound("clickedPos");
		newItem.getTagCompound().setLong("clickedPos", pos.toLong());

		player.sendStatusMessage(new TextComponentTranslation("actionbar.pollutionplus.location_marker.set", pos.getX(), pos.getY(), pos.getZ()), true);

		player.setHeldItem(hand, newItem);

		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this.hasLocationSaved(stack)) {
			BlockPos pos = this.getSavedLocation(stack);
			
			// display saved coords
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.location_marker"));
			tooltip.add(TextFormatting.AQUA + String.format("X: %d, Y: %d, Z: %d", pos.getX(), pos.getY(), pos.getZ()));
		} else {
			tooltip.add(TextFormatting.AQUA + I18n.format("tooltip.pollutionplus.location_marker.hint"));
		}
	}
	
	public boolean hasLocationSaved(ItemStack handItem) {
		if (!handItem.hasTagCompound()) {
			return false;
		}

		NBTTagCompound tag = handItem.getTagCompound();
		if (tag == null) {
			return false;
		}

		return tag.hasKey("clickedPos");
	}

	@Nullable
	public BlockPos getSavedLocation(ItemStack handItem) {
		if (!hasLocationSaved(handItem)){
			return null;
		}

		return BlockPos.fromLong(handItem.getTagCompound().getLong("clickedPos"));
	}
}
