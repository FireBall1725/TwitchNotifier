package com.fireball1725.twitchnotifier.lib;

import com.fireball1725.twitchnotifier.helper.ConfigurationHelper;
import com.fireball1725.twitchnotifier.reference.GuiSettings;
import com.fireball1725.twitchnotifier.reference.StreamTipSettings;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {
	public static Configuration configuration;

	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {
		// Stream Tip Configuration
		StreamTipSettings.STREAM_TIP_ENABLE = ConfigurationHelper.getBoolean(configuration, "Enable Stream Tips", "StreamTip", false, "Enable StreamTip Integration");
		StreamTipSettings.STREAM_TIP_CLIENT_ID = ConfigurationHelper.getString(configuration, "StreamTip Client ID", "StreamTip", "", "Client ID for Stream Tips");
		StreamTipSettings.STREAM_TIP_ACCESS_TOKEN = ConfigurationHelper.getString(configuration, "StreamTip Access Token", "StreamTip", "", "Access Token for Stream Tips");

		// Gui Configuration
		GuiSettings.NEW_TIP_TITLE = ConfigurationHelper.getString(configuration, "New Tip - Window Title", "WindowConfig", "", "Window Title for New Tips");
		GuiSettings.NEW_TIP_MESSAGE = ConfigurationHelper.getString(configuration, "New Tip - Message", "WindowConfig", "", "New Tip Message");

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
}
