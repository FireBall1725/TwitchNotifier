package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.config.ConfigurationFile;
import com.fireball1725.twitchnotifier.lib.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigEvents {
    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(Reference.MOD_ID)) {
            ConfigurationFile.loadConfiguration();
        }
    }
}
