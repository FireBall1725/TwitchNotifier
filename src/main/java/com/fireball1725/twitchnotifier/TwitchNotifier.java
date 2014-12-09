package com.fireball1725.twitchnotifier;

import com.fireball1725.twitchnotifier.events.TickEvents;
import com.fireball1725.twitchnotifier.lib.ConfigurationHandler;
import com.fireball1725.twitchnotifier.lib.Reference;
import com.fireball1725.twitchnotifier.proxy.IProxy;
import com.fireball1725.twitchnotifier.util.StreamTip;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.util.Random;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_BUILD)
public class TwitchNotifier {
	@Mod.Instance
	public static TwitchNotifier instance;

	public static final Random random = new Random();

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());

		//MinecraftForge.EVENT_BUS.register(new PlayerEvents());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		new TickEvents();

		StreamTip streamTip = new StreamTip();
		streamTip.Init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.registerGuis();
	}
}
