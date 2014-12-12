package com.fireball1725.twitchnotifier.configwindow;

import com.fireball1725.twitchnotifier.gui.GuiMainMenuAddon;
import com.fireball1725.twitchnotifier.lib.Log;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

public class ConfigButtonInjector {
    @SideOnly(Side.CLIENT)
    public static class FallbackReplacer {
        @SubscribeEvent
        public void onGuiOpen(GuiOpenEvent event) {
            if (event.gui != null && event.gui.getClass() == GuiMainMenu.class) event.gui = new GuiMainMenuAddon();
        }
    }

    @SideOnly(Side.CLIENT)
    public static class EventBasedReplacer {
        @SubscribeEvent
        public void onGuiInit(GuiScreenEvent.InitGuiEvent event) {
            if (event.gui instanceof GuiMainMenu) GuiMainMenuAddon.onGuiInit(event.gui, event.buttonList);
        }

        @SubscribeEvent
        public void onGuiInit(GuiScreenEvent.DrawScreenEvent.Post event) {
            if (event.gui instanceof GuiMainMenu) GuiMainMenuAddon.onScreenDraw(event.gui);
        }

        @SubscribeEvent
        public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent event) {
            if (event.gui instanceof GuiMainMenu) GuiMainMenuAddon.onActionPerformed(event.gui.mc, event.gui, event.button);
        }
    }

    public static void registerInjector() {
        try {
            Class.forName("net.minecraftforge.client.event.GuiScreenEvent");
            Log.info("GuiScreen Configuration Button Registered");
            MinecraftForge.EVENT_BUS.register(new EventBasedReplacer());
        } catch (ClassNotFoundException ex) {
            Log.info("Old forge version detected, using fallback GUI modification method");
            MinecraftForge.EVENT_BUS.register(new FallbackReplacer());
        }
    }

}
