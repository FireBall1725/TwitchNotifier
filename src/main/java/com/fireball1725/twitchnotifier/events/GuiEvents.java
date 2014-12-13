package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.lib.Reference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;

@SideOnly(Side.CLIENT)
public class GuiEvents {
    @SubscribeEvent
    public void onScreenInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiOptions) {
            event.buttonList.add(new GuiButton(1725, event.gui.width / 2 - 155, event.gui.height / 6 + 48 - 6, 150, 20, I18n.format("Twitch Notifier Settings...")));
        }
    }

    @SubscribeEvent
    public void onButtonClickPost(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.gui instanceof GuiOptions) {
            if (event.button.id == 1725) {
                try {
                    IModGuiFactory guiFactory = FMLClientHandler.instance().getGuiFactoryFor(Loader.instance().getIndexedModList().get(Reference.MOD_ID));
                    GuiScreen newScreen = guiFactory.mainConfigGuiClass().getConstructor(GuiScreen.class).newInstance(event.gui);
                    event.gui.mc.displayGuiScreen(newScreen);
                } catch (Exception ex) {}
            }
        }
    }
}
