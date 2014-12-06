package com.fireball1725.twitchnotifier.util;

import com.fireball1725.twitchnotifier.helper.FireworkHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

public class StreamEvents {
	@SideOnly(Side.CLIENT)
	public static void newTip(String Amount, String From, String Message) {
		FireworkHelper.SpawnFireWork(Minecraft.getMinecraft().thePlayer);
	}

	public static void newFollower(String From) {

	}

	public static void newSubscriber(String From) {

	}
}
