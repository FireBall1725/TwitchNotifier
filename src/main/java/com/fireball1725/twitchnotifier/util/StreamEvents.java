package com.fireball1725.twitchnotifier.util;

import com.fireball1725.twitchnotifier.helper.FireworkHelper;
import com.fireball1725.twitchnotifier.helper.OverlayHelper;
import com.fireball1725.twitchnotifier.lib.Log;

public class StreamEvents {
	public static void newTip(String Amount, String From, String Message) {
		OverlayHelper.addOverlay("New Tip!", "New tip from " + From + " for " + Amount, "Message: " + Message);
	}

	public static void newFollower(String From) {

	}

	public static void newSubscriber(String From) {

	}
}
