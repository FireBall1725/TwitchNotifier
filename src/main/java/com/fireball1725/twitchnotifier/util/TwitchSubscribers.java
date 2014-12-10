package com.fireball1725.twitchnotifier.util;

import com.fireball1725.twitchnotifier.config.ConfigTwitchSettings;
import com.fireball1725.twitchnotifier.helper.NotificationHelper;
import com.fireball1725.twitchnotifier.lib.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

public class TwitchSubscribers implements Runnable {
    private static final JsonParser parser = new JsonParser();
    private static boolean checking = false;

    public void run() {
        if (checking) {
            return;
        }

        checking = true;

        try {
            JsonElement node;
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(Twitch.TWITCH_SUB_URL.openStream()));
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

            JsonArray twitchSubscriptions = base.getAsJsonArray("subscriptions");
            if (twitchSubscriptions == null) {
                Log.warn("Twitch Subscriptions not found!");
                checking = false;
                return;
            }

            for (Iterator<JsonElement> iterator = twitchSubscriptions.iterator(); iterator.hasNext();) {
                JsonElement sub = iterator.next();

                if (sub.isJsonObject()) {
                    String userName = sub.getAsJsonObject().getAsJsonObject("user").get("display_name").getAsString();

                    // Check to see if we need to initialise the followers list
                    if (Twitch.twitchInitSubscribers) {
                        Twitch.twitchSubscribers.add(userName);
                    } else {
                        if (!Twitch.twitchSubscribers.contains(userName)) {
                            Twitch.twitchSubscribers.add(userName);

                            String notificationMessage = ConfigTwitchSettings.twitchSubscriberNotificationMessage;
                            notificationMessage = notificationMessage.replace("%USERNAME%", userName);
                            String finalMessage[] = notificationMessage.split("%%");

                            NotificationHelper.addNotification(ConfigTwitchSettings.twitchShowFireworksSubscribe, ConfigTwitchSettings.twitchShowAlertBoxSubscribe, finalMessage);
                        }
                    }
                }

            }

            checking = false;
            Twitch.twitchInitSubscribers = false;
        } catch (Exception ex) {
            Log.fatal("Unexpected error checking subscription info");
            Log.fatal(ex);
        }

    }
}
