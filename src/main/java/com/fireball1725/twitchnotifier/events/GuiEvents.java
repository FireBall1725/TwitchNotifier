package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.lib.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEvents {
    @SubscribeEvent
    public void onScreenInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiOptions) {
            event.getButtonList().add(new GuiButton(1725, event.getGui().width / 2 - 155, event.getGui().height / 6 + 48 - 10 - 20, 150, 20, I18n.format("Twitch Notifier Settings...")));
        }
    }

    @SubscribeEvent
    public void onButtonClickPost(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiOptions) {
            if (event.getButton().id == 1725) {
                try {
                    IModGuiFactory guiFactory = FMLClientHandler.instance().getGuiFactoryFor(Loader.instance().getIndexedModList().get(Reference.MOD_ID));
                    GuiScreen newScreen = guiFactory.mainConfigGuiClass().getConstructor(GuiScreen.class).newInstance(event.getGui());
                    event.getGui().mc.displayGuiScreen(newScreen);
                } catch (Exception ex) {}
            }
        }
    }
}
