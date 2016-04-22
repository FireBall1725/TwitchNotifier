package com.fireball1725.twitchnotifier.network;

import com.fireball1725.twitchnotifier.TwitchNotifier;
import com.fireball1725.twitchnotifier.lib.Reference;
import com.fireball1725.twitchnotifier.network.message.PacketSpawnBlock;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static void init() {
        TwitchNotifier.network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        TwitchNotifier.network.registerMessage(PacketSpawnBlock.Handler.class, PacketSpawnBlock.class, 0, Side.SERVER);
    }
}
