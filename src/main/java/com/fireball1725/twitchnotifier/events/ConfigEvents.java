package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.config.ConfigurationFile;
import com.fireball1725.twitchnotifier.lib.Reference;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigEvents {
    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ConfigurationFile.loadConfiguration();
        }
    }
}
