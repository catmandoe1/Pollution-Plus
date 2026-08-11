package com.catmandoe1.pollutionplus.network;

import com.catmandoe1.pollutionplus.tileentities.TileEntityPollutionDeleter;
import com.google.common.graph.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class MessagePollutionDeleter {
	public ResourceKey<Level> dimension;
	public int entityId;
	public int buttonId;
	public boolean holdingCtrl;
	public BlockPos tileEntityPosition;

	public MessagePollutionDeleter(Player player, int buttonId, boolean holdingCtrl, BlockPos tileEntityPosition) {
		this.dimension = player.getCommandSenderWorld().dimension();
		this.entityId = player.getId();
		this.buttonId = buttonId;
		this.holdingCtrl = holdingCtrl;
		this.tileEntityPosition = tileEntityPosition;
	}

	public MessagePollutionDeleter(ResourceLocation dimension, int entityId, int buttonId, boolean holdingCtrl, BlockPos tileEntityPosition) {
		this.dimension = ResourceKey.create(Registries.DIMENSION, dimension);
		this.entityId = entityId;
		this.buttonId = buttonId;
		this.holdingCtrl = holdingCtrl;
		this.tileEntityPosition = tileEntityPosition;
	}

	public static void encode(final MessagePollutionDeleter message, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(message.dimension.location());
		buffer.writeInt(message.entityId);
		buffer.writeInt(message.buttonId);
		buffer.writeBoolean(message.holdingCtrl);
		buffer.writeBlockPos(message.tileEntityPosition);
	}

	public static MessagePollutionDeleter decode(final FriendlyByteBuf buffer) {
		return new MessagePollutionDeleter(buffer.readResourceLocation(), buffer.readInt(), buffer.readInt(), buffer.readBoolean(), buffer.readBlockPos());
	}

	public static void handle(final MessagePollutionDeleter message, final Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayer player = context.get().getSender();
			if (player != null) {
				ServerLevel world = Objects.requireNonNull(player.getServer()).getLevel(message.dimension);

				// check player
				if (world != null && !world.isClientSide && player.getId() == message.entityId) {
					TileEntityPollutionDeleter deleter = (TileEntityPollutionDeleter)world.getBlockEntity(message.tileEntityPosition);

					if (deleter != null) {
//						deleter.hithere();
						if (message.holdingCtrl){
							deleter.updateDeletionArea(message.buttonId, 10);
						} else {
							deleter.updateDeletionArea(message.buttonId);
						}

						BlockState state = world.getBlockState(message.tileEntityPosition);
						world.sendBlockUpdated(message.tileEntityPosition, state, state, 3);
					}
				}
			}
		});
		context.get().setPacketHandled(true);
	}
}
