package com.catmandoe1.pollutionplus.item;

import com.catmandoe1.pollutionplus.Main;
import com.catmandoe1.pollutionplus.PPSounds;
import com.catmandoe1.pollutionplus.block.BlockPollutionDeleter;
import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ItemLocationMarker extends Item {
	public ItemLocationMarker(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		// if pollution deleter was right clicked
		if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof BlockPollutionDeleter) {
			this.doCooldown(context.getPlayer());
			if (context.getLevel().isClientSide) {
				this.playUseSound(context.getLevel(), context.getPlayer());
				return InteractionResult.SUCCESS;
			}

			// get tile entity
			BlockEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
			if (tileEntity instanceof TileEntityPollutionDeleter deleter) {
				// if item has position saved
				if (context.getItemInHand().hasTag() && Objects.requireNonNull(context.getItemInHand().getTag()).contains("clickedPos", Tag.TAG_LONG)) {
					BlockPos pos = BlockPos.of(context.getItemInHand().getTag().getLong("clickedPos"));

					if (context.getPlayer().isShiftKeyDown()) {
						deleter.setDeletionAreaBB(pos); // set second position
						context.getPlayer().displayClientMessage(Component.translatable("actionbar.pollutionplus.location_marker.applyBB"), true);
					} else {
						deleter.setDeletionAreaAA(pos); // set first position
						context.getPlayer().displayClientMessage(Component.translatable("actionbar.pollutionplus.location_marker.applyAA"), true);
					}
					return InteractionResult.SUCCESS;
				}

				// if shift clicked
			}
		}

		// handle right clicking other blocks
		if (!context.getPlayer().isShiftKeyDown()) {
			return InteractionResult.PASS;
		}
		this.doCooldown(context.getPlayer());

		if (context.getLevel().isClientSide) {
			this.playUseSound(context.getLevel(), context.getPlayer());
			return InteractionResult.SUCCESS; // important for not activated use()
		}


		ItemStack newItem = new ItemStack(PPItems.LOCATION_MARKER.get());

		// add clicked location to new item
		newItem.getOrCreateTag().contains("clickedPos");
		newItem.getTag().putLong("clickedPos", context.getClickedPos().asLong());

		context.getPlayer().displayClientMessage(Component.translatable("actionbar.pollutionplus.location_marker.set", context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ()), true);

		context.getPlayer().setItemInHand(context.getHand(), newItem);

		return InteractionResult.SUCCESS;
	}

	@OnlyIn(Dist.CLIENT)
	private void playUseSound(Level world, Player player) {
		world.playLocalSound(player.blockPosition(), PPSounds.ITEM_LOCATION_MARKER_USE.get(), SoundSource.PLAYERS, 1, 1, false);
	}

	private void doCooldown(Player player) {
		player.getCooldowns().addCooldown(this, 10); // cooldown
	}

	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player player, @NotNull InteractionHand usedHand) {
		ItemStack item = player.getItemInHand(usedHand);
		if (!player.isShiftKeyDown() || player.getCooldowns().isOnCooldown(item.getItem())) {
			return InteractionResultHolder.pass(item);
		}

		this.doCooldown(player);
		if (pLevel.isClientSide) {
			this.playUseSound(pLevel, player);
		}

		if (!pLevel.isClientSide) {
			ItemStack newItem = new ItemStack(PPItems.LOCATION_MARKER.get());

			// add player position to new item
			newItem.getOrCreateTag().contains("clickedPos");
			BlockPos target = player.getOnPos().above();
			newItem.getTag().putLong("clickedPos", target.asLong());

			player.displayClientMessage(Component.translatable("actionbar.pollutionplus.location_marker.set", target.getX(), target.getY(), target.getZ()), true);

			player.setItemInHand(usedHand, newItem);
		}

		return InteractionResultHolder.sidedSuccess(item, pLevel.isClientSide());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
		if (this.hasLocationSaved(stack)) {
			BlockPos pos = this.getSavedLocation(stack);

			components.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.location_marker")));
			components.add(Component.literal(ChatFormatting.AQUA + String.format("X: %d, Y: %d, Z: %d", pos.getX(), pos.getY(), pos.getZ())));
		} else {
			components.add(Component.literal(ChatFormatting.AQUA + I18n.get("tooltip.pollutionplus.location_marker.hint")));
		}
	}

	public boolean hasLocationSaved(ItemStack handItem) {
		if (!handItem.hasTag()) {
			return false;
		}

		CompoundTag tag = handItem.getTag();
		if (tag == null) {
			return false;
		}

		return tag.contains("clickedPos", Tag.TAG_LONG);
	}

	@Nullable
	public BlockPos getSavedLocation(ItemStack handItem) {
		if (!hasLocationSaved(handItem)){
			return null;
		}

		return BlockPos.of(handItem.getTag().getLong("clickedPos"));
	}
}
