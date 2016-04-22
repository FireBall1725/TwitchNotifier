package com.fireball1725.twitchnotifier.proxy;

import com.fireball1725.twitchnotifier.events.GuiEvents;
import com.fireball1725.twitchnotifier.events.TickEvents;
import com.fireball1725.twitchnotifier.gui.GuiOverlayWindow;
import com.fireball1725.twitchnotifier.lib.Log;
import com.fireball1725.twitchnotifier.util.StreamTip;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new GuiEvents());
    }

    @Override
    public void registerGuis() {
        Log.debug("Register GUI");
        Minecraft client = getClient();
        MinecraftForge.EVENT_BUS.register(new GuiOverlayWindow(client));
    }

    @Override
    public void registerConnectionEvents() {
        new TickEvents();

        StreamTip streamTip = new StreamTip();
        streamTip.Init();
    }
}
