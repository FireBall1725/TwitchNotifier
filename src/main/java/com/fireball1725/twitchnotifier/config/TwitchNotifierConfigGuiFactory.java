package com.fireball1725.twitchnotifier.config;

import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

public class TwitchNotifierConfigGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft mc) {

    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return TwitchNotifierConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
