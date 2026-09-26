package s11.mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import s11.mod.Main;
import s11.mod.objects.tileEntities.TilePollutionDeleter;

public class MessagePollutionDeleter implements IMessage, IMessageHandler<MessagePollutionDeleter, MessagePollutionDeleter>{
	public int dimension;
	public int entitiyId;
	public int buttonId;
	public boolean holdingCtrl;
	public BlockPos tileEntityPosition;
	
	public MessagePollutionDeleter() {} // required
	
	public MessagePollutionDeleter(EntityPlayer player, int buttonId, boolean holdingCtrl, BlockPos tileEntityPosition) {
		this.dimension = player.dimension;
		this.entitiyId = player.getEntityId();
		this.buttonId = buttonId;
		this.holdingCtrl = holdingCtrl;
		this.tileEntityPosition = tileEntityPosition;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.dimension);
		buf.writeInt(this.entitiyId);
		buf.writeInt(this.buttonId);
		buf.writeBoolean(this.holdingCtrl);
		buf.writeLong(this.tileEntityPosition.toLong());
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// order must match the write order
		this.dimension = buf.readInt();
		this.entitiyId = buf.readInt();
		this.buttonId = buf.readInt();
		this.holdingCtrl = buf.readBoolean();
		this.tileEntityPosition = BlockPos.fromLong(buf.readLong());
	}
	
	@Override
	public MessagePollutionDeleter onMessage(MessagePollutionDeleter message, MessageContext ctx) {
		final World world = DimensionManager.getWorld(message.dimension);
		if (world == null) {
			return null;
		}
		
		// boot client out
		if (world.isRemote) {
			return null;
		}
		
		// if player doesnt match the player from the message
		if (ctx.getServerHandler().player.getEntityId() != message.entitiyId) {
			return null;
		}
		
		final EntityPlayer player = ctx.getServerHandler().player;
		player.getServer().addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				TilePollutionDeleter deleter = (TilePollutionDeleter)world.getTileEntity(message.tileEntityPosition);
				//Main.logger.info("doing message {}", message.buttonId);
				if (deleter != null) {
					if (message.holdingCtrl) {
						deleter.updateDeletionArea(message.buttonId, 10);
					} else {
						deleter.updateDeletionArea(message.buttonId);
					}
					
					IBlockState state = world.getBlockState(message.tileEntityPosition);
					world.notifyBlockUpdate(message.tileEntityPosition, state, state, 3); // the magical 3 again
				}
			}
		});
		
		return null;
	}
}
