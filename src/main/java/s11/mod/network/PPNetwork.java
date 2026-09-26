package s11.mod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import s11.mod.util.Reference;

public class PPNetwork {
	public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	public static void registerNetwork() {
		NETWORK.registerMessage(MessagePollutionDeleter.class, MessagePollutionDeleter.class, 0, Side.SERVER);
	}
}
