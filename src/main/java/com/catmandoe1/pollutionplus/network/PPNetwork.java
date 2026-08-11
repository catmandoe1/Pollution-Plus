package com.catmandoe1.pollutionplus.network;

import com.catmandoe1.pollutionplus.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PPNetwork {
	public static final ResourceLocation channelName = new ResourceLocation(Main.MODID, "network");
	public static final String networkVersion = new ResourceLocation(Main.MODID, "1").toString();

	public static SimpleChannel getNetworkChannel() {
		final SimpleChannel channel = NetworkRegistry.ChannelBuilder.named(channelName)
				.clientAcceptedVersions(version -> true)
				.serverAcceptedVersions(version -> true)
				.networkProtocolVersion(() -> networkVersion)
				.simpleChannel();

		channel.messageBuilder(MessagePollutionDeleter.class, 0, NetworkDirection.PLAY_TO_SERVER)
				.decoder(MessagePollutionDeleter::decode)
				.encoder(MessagePollutionDeleter::encode)
				.consumerNetworkThread(MessagePollutionDeleter::handle)
				.add();

		return channel;
	}
}
