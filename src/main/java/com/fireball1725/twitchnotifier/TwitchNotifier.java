package com.fireball1725.twitchnotifier;

import com.fireball1725.twitchnotifier.configwindow.ConfigButtonInjector;
import com.fireball1725.twitchnotifier.events.GuiEvents;
import com.fireball1725.twitchnotifier.events.TickEvents;
import com.fireball1725.twitchnotifier.config.ConfigurationFile;
import com.fireball1725.twitchnotifier.helper.NotificationHelper;
import com.fireball1725.twitchnotifier.lib.Log;
import com.fireball1725.twitchnotifier.lib.Reference;
import com.fireball1725.twitchnotifier.proxy.IProxy;
import com.fireball1725.twitchnotifier.util.StreamTip;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.util.Random;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_BUILD, guiFactory = Reference.GUI_FACTORY)
public class TwitchNotifier {
	@Mod.Instance
	public static TwitchNotifier instance;

	public static final Random random = new Random();
	public static Configuration configuration;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configuration = ConfigurationFile.init(event.getSuggestedConfigurationFile());

		//MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		MinecraftForge.EVENT_BUS.register(new GuiEvents());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ConfigButtonInjector.registerInjector();

		new TickEvents();

		StreamTip streamTip = new StreamTip();
		streamTip.Init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.registerGuis();
	}

	@Mod.EventHandler
	public void imcCallback(FMLInterModComms.IMCEvent event) {
		for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages()) {
			// Check to see if this message is for me
			if (imcMessage.key.equalsIgnoreCase("twitchnotifier")) {
				// Verify the message is in a NBTTagCompound
				if (imcMessage.isNBTMessage()) {
					Log.info(imcMessage.getSender() + " has sent us an IMC notification, validating message...");
					boolean valid = false;
					NBTTagCompound nbtTagCompound = imcMessage.getNBTValue();

					// TODO: Actually validate nbt message
					valid = true;

					if (valid) {
						Log.info("IMC Notification from " + imcMessage.getSender() + "has been validated, adding to notification queue");
						NotificationHelper.addNotification(nbtTagCompound);
					} else {
						Log.fatal("IMC Notification from " + imcMessage.getSender() + "has failed validation, please notify mod author to update their notification");
					}
				}
			}
		}
	}
}
