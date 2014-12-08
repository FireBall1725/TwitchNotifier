package com.fireball1725.twitchnotifier.events;

import com.fireball1725.twitchnotifier.helper.FireworkHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class PlayerEvents {
	@SubscribeEvent
	public void onPlayerJoinEvent(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			FireworkHelper.SpawnFireWork();
		}
	}
}
