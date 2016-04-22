package com.fireball1725.twitchnotifier.config;

import com.fireball1725.twitchnotifier.TwitchNotifier;
import com.fireball1725.twitchnotifier.lib.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.Arrays;

public class TwitchNotifierConfig extends GuiConfig {
    public TwitchNotifierConfig(GuiScreen parentScreen) {
        super(parentScreen,
                Arrays.asList(new IConfigElement[] {
                        new ConfigElement(TwitchNotifier.configuration.getCategory("streamtip")),
                        new ConfigElement(TwitchNotifier.configuration.getCategory("twitch")),
                        new ConfigElement(TwitchNotifier.configuration.getCategory("alertbox")),
                        new ConfigElement(TwitchNotifier.configuration.getCategory("fireworks")),
                        new ConfigElement(TwitchNotifier.configuration.getCategory("spawnblock"))
                }),
                Reference.MOD_ID, false, false, "Twitch Notifier Configuration");
    }
}
