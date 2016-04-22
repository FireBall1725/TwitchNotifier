package com.fireball1725.twitchnotifier.proxy;

import com.fireball1725.twitchnotifier.gui.GuiOverlayWindow;
import com.fireball1725.twitchnotifier.lib.Log;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

public class CommonProxy implements IProxy {
	public void registerEvents() {

	}

	public Minecraft getClient() {
		return FMLClientHandler.instance().getClient();
	}

	public void registerGuis() {

	}

	public void registerConnectionEvents() {

	}
}
