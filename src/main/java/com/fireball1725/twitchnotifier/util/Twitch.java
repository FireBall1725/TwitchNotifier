package com.fireball1725.twitchnotifier.util;

import com.fireball1725.twitchnotifier.config.ConfigTwitchSettings;
import com.fireball1725.twitchnotifier.lib.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Twitch {
    public static final URL TWITCH_SUB_URL;
    public static final URL TWITCH_FOLLOWER_URL;

    public static ArrayList<String> twitchSubscribers = new ArrayList<String>();
    public static ArrayList<String> twitchFollowers = new ArrayList<String>();
    public static boolean twitchInitSubscribers = true;
    public static boolean twitchInitFollowers = true;

    static {
        URL tempURL;
        try {
            tempURL = new URL("https://api.twitch.tv/kraken/channels/" + ConfigTwitchSettings.twitchChannelName + "/subscriptions?limit=" + Integer.toString(ConfigTwitchSettings.twitchQueryLength) + "&direction=desc&oauth_token=" + ConfigTwitchSettings.twitchOAuthToken);
        } catch (Exception ex) {
            Log.fatal("Failed to create URL!");
            Log.fatal(ex);
            tempURL = null;
        }
        TWITCH_SUB_URL = tempURL;
    }

    static {
        URL tempURL;
        try {
            tempURL = new URL("https://api.twitch.tv/kraken/channels/" + ConfigTwitchSettings.twitchChannelName + "/follows?limit=" + Integer.toString(ConfigTwitchSettings.twitchQueryLength) + "&direction=desc&oauth_token=" + ConfigTwitchSettings.twitchOAuthToken);
        } catch (Exception ex) {
            Log.fatal("Failed to create URL!");
            Log.fatal(ex);
            tempURL = null;
        }
        TWITCH_FOLLOWER_URL = tempURL;
    }

    public static void updateTwitch() {
        if (ConfigTwitchSettings.twitchEnabled && (ConfigTwitchSettings.twitchShowAlertBoxSubscribe || ConfigTwitchSettings.twitchShowFireworksSubscribe)) {
            new Thread(new TwitchSubscribers()).start();
        }

        if (ConfigTwitchSettings.twitchEnabled && (ConfigTwitchSettings.twitchShowAlertBoxFollow || ConfigTwitchSettings.twitchShowFireworksFollow)) {
            new Thread(new TwitchFollowers()).start();
        }
    }
}
