package com.fireball1725.twitchnotifier;

import com.fireball1725.twitchnotifier.events.ConfigEvents;
import com.fireball1725.twitchnotifier.events.GuiEvents;
import com.fireball1725.twitchnotifier.events.TickEvents;
import com.fireball1725.twitchnotifier.config.ConfigurationFile;
import com.fireball1725.twitchnotifier.helper.NotificationHelper;
import com.fireball1725.twitchnotifier.lib.Log;
import com.fireball1725.twitchnotifier.lib.Reference;
import com.fireball1725.twitchnotifier.network.PacketHandler;
import com.fireball1725.twitchnotifier.proxy.IProxy;
import com.fireball1725.twitchnotifier.util.StreamTip;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.util.Random;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_BUILD, guiFactory = Reference.GUI_FACTORY)
public class TwitchNotifier {
	@Mod.Instance
	public static TwitchNotifier instance;

	public static final Random random = new Random();
	public static Configuration configuration;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	public static SimpleNetworkWrapper network;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configuration = ConfigurationFile.init(event.getSuggestedConfigurationFile());

		proxy.registerEvents();
		FMLCommonHandler.instance().bus().register(new ConfigEvents());

		PacketHandler.init();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerConnectionEvents();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.registerGuis();
	}
}
