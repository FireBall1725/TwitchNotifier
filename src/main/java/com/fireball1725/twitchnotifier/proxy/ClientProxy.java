package com.fireball1725.twitchnotifier.proxy;

import com.fireball1725.twitchnotifier.gui.GuiOverlayWindow;
import com.fireball1725.twitchnotifier.lib.Log;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    public Minecraft getClient() {
        return FMLClientHandler.instance().getClient();
    }

    public void RegisterGuis() {
        Log.debug("Register GUI");
        Minecraft client = getClient();
        MinecraftForge.EVENT_BUS.register(new GuiOverlayWindow(client));
    }
}
