package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.config.TwitchNotifierConfig;
import com.fireball1725.twitchnotifier.lib.Log;
import com.fireball1725.twitchnotifier.lib.Reference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;

@SideOnly(Side.CLIENT)
public class GuiEvents {
    @SubscribeEvent
    public void onScreenInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiMainMenu) {
            event.buttonList.add(new GuiButton(1725, (event.gui.width / 2) - 35, event.gui.height - 25, 70, 20, I18n.format("gui.button.config")));
        }
    }

    @SubscribeEvent
    public void onButtonClickPre(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.gui instanceof GuiMainMenu) {
            try {
                IModGuiFactory guiFactory = FMLClientHandler.instance().getGuiFactoryFor(Loader.instance().getIndexedModList().get(Reference.MOD_ID));
                GuiScreen newScreen = guiFactory.mainConfigGuiClass().getConstructor(TwitchNotifierConfig.class).newInstance();
                event.gui.mc.displayGuiScreen(newScreen);
            } catch (Exception ex) {
                Log.fatal("Error showing Configuration Screen");
                Log.fatal(ex);
            }
            event.setCanceled(true);
        }
    }
}
