package com.fireball1725.twitchnotifier.util;

import com.fireball1725.twitchnotifier.config.ConfigStreamTipSettings;
import com.fireball1725.twitchnotifier.lib.Log;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONObject;

public class StreamTip {
	public boolean Init() {
		if (!ConfigStreamTipSettings.streamTipEnabled) { return false; }

		IO.Options options = new IO.Options();
		options.query = "client_id=" + ConfigStreamTipSettings.streamTipClientID + "&access_token=" + ConfigStreamTipSettings.streamTipAccessToken;

		try {
			Socket socket = IO.socket("https://streamtip.com", options);

			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override public void call(Object... args) {
					Log.info("Connected to streamtip.com");
				}
			})

			.on("authenticated", new Emitter.Listener() {
				@Override public void call(Object... args) {
					Log.info("Authenticated to StreamTip.com");
				}
			})

			.on("error", new Emitter.Listener() {
				@Override public void call(Object... args) {
					Log.fatal("Error returned from StreamTip.com, the error was: " + args[0]);
				}
			})

			.on("newTip", new Emitter.Listener() {
				@Override public void call(Object... args) {
					JSONObject newTip = (JSONObject) args[0];
					String tipAmount = "";
					String tipFrom = "";
					String tipMessage = "";
					try {
						tipAmount = newTip.getString("currencySymbol") + newTip.getString("amount");
						tipFrom = newTip.getString("username");
						tipMessage = newTip.getString("note");
					} catch (Exception ex) {
						Log.fatal("Error while parsing new tip");
						Log.fatal(ex);
					}

					Log.info("New TIP => From: " + tipFrom + " Amount: " + tipAmount + " Note: " + tipMessage);
					StreamEvents.newTip(tipAmount, tipFrom, tipMessage);
				}
			});

			socket.connect();

		} catch (Exception ex) {
			Log.fatal("Error");
			Log.fatal(ex);
			return false;
		}

		return true;
	}
}
