package com.fireball1725.twitchnotifier.config;

import com.fireball1725.twitchnotifier.helper.ConfigurationHelper;
import com.fireball1725.twitchnotifier.lib.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationFile {
	public static net.minecraftforge.common.config.Configuration configuration;

	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new net.minecraftforge.common.config.Configuration(configFile);
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {
		// StreamTip Settings
		ConfigStreamTipSettings.streamTipEnabled = ConfigurationHelper.getBoolean(configuration, "Enable StreamTip", "streamtip", false, "Enable StreamTip Integration");
		ConfigStreamTipSettings.streamTipClientID = ConfigurationHelper.getString(configuration, "StreamTip API ClientID", "streamtip", "", "API Client ID for StreamTip");
		ConfigStreamTipSettings.streamTipAccessToken = ConfigurationHelper.getString(configuration, "StreamTip API Access Token", "streamtip", "", "API Access Token for StreamTip");
		ConfigStreamTipSettings.streamTipMinAmmountForAlert = ConfigurationHelper.getInt(configuration, "Minimum Amount for Alert", "streamtip", 1, "Minimum Amount for Alert to Show");
		ConfigStreamTipSettings.streamTipShowFireworks = ConfigurationHelper.getBoolean(configuration, "Show Fireworks", "streamtip", true, "Show Fireworks on new StreamTip");
		ConfigStreamTipSettings.streamTipShowAlertBox = ConfigurationHelper.getBoolean(configuration, "Show Alert Box", "streamtip", true, "Show Alert Box on new StreamTip");
		ConfigStreamTipSettings.streamTipNotificationMessage = ConfigurationHelper.getString(configuration, "Notification Message", "streamtip", "New Tip!%%%USERNAME% Just donated %AMOUNT%!%%Message: %MESSAGE%", "Message shown on Alert Box (See readme for variables)");

		// Twitch Settings
		ConfigTwitchSettings.twitchEnabled = ConfigurationHelper.getBoolean(configuration, "Enable Twitch", "twitch", false, "Enable Twitch Integration");
		ConfigTwitchSettings.twitchOAuthToken = ConfigurationHelper.getString(configuration, "oAuth Token", "twitch", "See readme for help getting oAuth token", "Twitch oAuth Token");
		ConfigTwitchSettings.twitchCheckTimer = ConfigurationHelper.getInt(configuration, "Check Timer", "twitch", 3, "Number of seconds to check for new Twitch events");
		ConfigTwitchSettings.twitchShowFireworksFollow = ConfigurationHelper.getBoolean(configuration, "Show Fireworks on Follow", "twitch", true, "Show Fireworks on new Follower");
		ConfigTwitchSettings.twitchShowAlertBoxFollow = ConfigurationHelper.getBoolean(configuration, "Show Alert Box on Follow", "twitch", true, "Show Alert Box on new Follower");
		ConfigTwitchSettings.twitchShowFireworksSubscribe = ConfigurationHelper.getBoolean(configuration, "Show Fireworks on Subscribe", "twitch", false, "Show Fireworks on new Subscriber (Only will work if Twitch partner)");
		ConfigTwitchSettings.twitchShowAlertBoxSubscribe = ConfigurationHelper.getBoolean(configuration, "Show Alert Box on Subscribe", "twitch", false, "Show Alert Box on new Subscriber (Only will work if Twitch partner)");
		ConfigTwitchSettings.twitchFollowerNotificationMessage = ConfigurationHelper.getString(configuration, "New Follower Notification Message", "twitch", "New Follower!%%%USERNAME% is now following the channel!", "Message shown on Alert Box (See readme for variables)");
		ConfigTwitchSettings.twitchSubscriberNotificationMessage = ConfigurationHelper.getString(configuration, "New Subscriber Notification Message", "twitch", "New Subscriber!%%%USERNAME% just subscribed to the channel!", "Message shown on Alert Box (See readme for variables)");

		// AlertBox Settings
		ConfigAlertBoxSettings.alertBox_CloseGUI = ConfigurationHelper.getBoolean(configuration, "Close GUI if open", "alertbox", true, "Close any open GUI Windows if there is a new Alert");
		ConfigAlertBoxSettings.alertBox_CooldownTime = ConfigurationHelper.getInt(configuration, "Cool Down Timer", "alertbox", 2, "Cool down timer in seconds between alerts");
		ConfigAlertBoxSettings.alertBox_ShowTime = ConfigurationHelper.getInt(configuration, "Time to show Alert", "alertbox", 8, "Time to show Alert in seconds");
		ConfigAlertBoxSettings.alertBox_ShowBackground = ConfigurationHelper.getBoolean(configuration, "Show Background", "alertbox", true, "Show Background Color");
		ConfigAlertBoxSettings.alertBox_BackgroundColor = ConfigurationHelper.getInt(configuration, "Background Color", "alertbox", 0xF0100010, "Background Color (argb)");
		ConfigAlertBoxSettings.alertBox_ShowBorder = ConfigurationHelper.getBoolean(configuration, "Show Border", "alertbox", true, "Show Border Color");
		ConfigAlertBoxSettings.alertBox_BorderColor = ConfigurationHelper.getInt(configuration, "Border Color", "alertbox", 0xFF00CC00, "Border Color (argb)");
		ConfigAlertBoxSettings.alertBox_TextColor = ConfigurationHelper.getInt(configuration, "Text Color", "alertbox", 0x00CC00, "Text Color (rgb)");

		// Firework Settings
		ConfigFireworkSettings.fireworkColors = ConfigurationHelper.getInt(configuration, "Firework Colors", "fireworks", ConfigFireworkSettings.FireworkColors_Default, "Firework Colors (rbg list)");
		ConfigFireworkSettings.fireworkLocations = ConfigurationHelper.getInt(configuration, "Firework Locations", "fireworks", ConfigFireworkSettings.FireworkLocations_Default, "Firework Locations (Y Level to player)");

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
