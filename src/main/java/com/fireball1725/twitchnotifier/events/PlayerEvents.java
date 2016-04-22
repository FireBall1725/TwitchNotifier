package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.helper.FireworkHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEvents {
	@SubscribeEvent
	public void onPlayerJoinEvent(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.getEntity();
			FireworkHelper.SpawnFireWork();
		}
	}
}
