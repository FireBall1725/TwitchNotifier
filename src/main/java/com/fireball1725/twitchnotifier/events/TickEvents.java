package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.config.ConfigTwitchSettings;
import com.fireball1725.twitchnotifier.helper.BlockSpawnHelper;
import com.fireball1725.twitchnotifier.helper.NotificationHelper;
import com.fireball1725.twitchnotifier.lib.Log;
import com.fireball1725.twitchnotifier.util.Twitch;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickEvents {
    private static final int UPDATE_TIME = ConfigTwitchSettings.twitchCheckTimer * 20;
    private int counter = 0;

    public TickEvents() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientTick(TickEvent.ClientTickEvent event)  {
        if (event.phase == TickEvent.Phase.START) {
            NotificationHelper.updateTick();
            BlockSpawnHelper.updateTick();
        } else if ((event.phase == TickEvent.Phase.END) && (++this.counter > UPDATE_TIME)) {
            this.counter = 0;
            Twitch.updateTwitch();
        }
    }
}
