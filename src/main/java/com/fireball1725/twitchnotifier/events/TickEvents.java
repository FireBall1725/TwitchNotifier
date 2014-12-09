package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.lib.Log;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickEvents {
    private static final int UPDATE_TIME = 60;
    private int counter = 0;

    public TickEvents() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientTick(TickEvent.ClientTickEvent event)  {
        if (event.phase == TickEvent.Phase.START) {

        } else if ((event.phase == TickEvent.Phase.END) && (++this.counter > UPDATE_TIME)) {
            this.counter = 0;
            // Update from Twitch...
            Log.debug("Update Tick...");
        }
    }
}
