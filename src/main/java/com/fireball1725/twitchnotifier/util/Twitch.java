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

    static {
        URL tempURL;
        try {
            tempURL = new URL("https://api.twitch.tv/kraken/channels/" + ConfigTwitchSettings.twitchChannelName + "/subscriptions?limit=" + Integer.toString(1) + "&direction=desc&oauth_token=" + ConfigTwitchSettings.twitchOAuthToken);
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
            tempURL = new URL("https://api.twitch.tv/kraken/channels/" + ConfigTwitchSettings.twitchChannelName + "/follows?limit=" + Integer.toString(1) + "&direction=desc&oauth_token=" + ConfigTwitchSettings.twitchOAuthToken);
        } catch (Exception ex) {
            Log.fatal("Failed to create URL!");
            Log.fatal(ex);
            tempURL = null;
        }
        TWITCH_FOLLOWER_URL = tempURL;
    }

    private static class twitchSubInfo implements Runnable {
        private static final JsonParser parser = new JsonParser();
        private static boolean checking = false;

        public void run() {
            if (checking) { return; }

            checking = true;

            try {
                JsonElement node;
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(TWITCH_SUB_URL.openStream()));
                    node = parser.parse(in);
                    in.close();
                } catch (Exception ex) {
                    node = null;
                    Log.fatal("Invalid data received from Twitch");
                    Log.fatal(ex);
                }

                if ((node == null) || (!node.isJsonObject())) {
                    checking = false;
                    return;
                }

                JsonObject base = node.getAsJsonObject();

                JsonArray twitchSubscribers = base.getAsJsonArray("subscriptions");
                if (twitchSubscribers == null) {
                    Log.warn("Twitch Subscriptions not found!");
                    checking = false;
                    return;
                }

                ArrayList<String> addSubs = new ArrayList();
                String newLastSubId = null;
                for (Iterator<JsonElement> iterator = twitchSubscribers.iterator(); iterator.hasNext();) {
                    JsonElement sub = (JsonElement)iterator.next();
                    if (sub.isJsonObject()) {
                        String id = sub.getAsJsonObject().get("_id").getAsString();
                        if (id == null) {
                            checking = false;
                            return;
                        }
                        if (newLastSubId == null) {
                            newLastSubId = id;
                        }

                    }
                }
            } catch (Exception ex) {
                Log.fatal("Unexpected error checking subscription info");
                Log.fatal(ex);
            }
        }
    }
}
