package com.fireball1725.twitchnotifier.network.message;


import com.fireball1725.twitchnotifier.helper.SafeBlockReplacer;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSpawnBlock implements IMessage {
    private ItemStack spawnItem;
    private int x;
    private int y;
    private int z;

    public PacketSpawnBlock(ItemStack spawnItem, int x, int y, int z) {
        this.spawnItem = spawnItem;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketSpawnBlock() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.spawnItem = ByteBufUtils.readItemStack(buf);
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.spawnItem);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<PacketSpawnBlock, IMessage> {

        @Override
        public IMessage onMessage(final PacketSpawnBlock message, final MessageContext ctx) {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    EntityPlayer player = ctx.getServerHandler().playerEntity;
                    World world = player.worldObj;

                    BlockPos pos = new BlockPos(message.x, message.y, message.z);
                    IBlockState block = Block.getBlockFromItem(message.spawnItem.getItem()).getStateFromMeta(message.spawnItem.getItemDamage());

                    BlockPos realPos = SafeBlockReplacer.GetSafeGraveSite(world, pos, null);
                    world.setBlockState(realPos, block);
                }
            });
            return null;
        }
    }
}
