package com.fireball1725.twitchnotifier.helper;

import com.fireball1725.twitchnotifier.config.ConfigAlertBoxSettings;
import com.fireball1725.twitchnotifier.config.ConfigBlockSpawnSettings;
import com.fireball1725.twitchnotifier.lib.Log;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class NotificationHelper {
    private static List<NBTTagCompound> overlayAlerts = new ArrayList<NBTTagCompound>();

    /***
     * Add Notification to the Alert Queue
     * @param notification
     */
    public static void addNotification(NBTTagCompound notification) {
        overlayAlerts.add(notification);
    }

    /***
     * Add Notification to the Alert Queue
     * @param notificationMessages
     */
    public static void addNotification(String... notificationMessages) {
        NBTTagCompound notificationTag = new NBTTagCompound();
        NBTTagList messages = new NBTTagList();
        NBTTagCompound messageTag;

        for (int i = 0; i < notificationMessages.length; i++) {
            messageTag = new NBTTagCompound();
            messageTag.setString("text", notificationMessages[i]);
            messages.appendTag(messageTag);
        }

        notificationTag.setTag("messages", messages);
        notificationTag.setBoolean("showFireworks", true);
        notificationTag.setBoolean("showAlertbox", true);

        addNotification(notificationTag);
    }

    public static void addNotification(Boolean showAlertBox, Boolean showFireworks, String... notificationMessages) {
        NBTTagCompound notificationTag = new NBTTagCompound();
        NBTTagList messages = new NBTTagList();
        NBTTagCompound messageTag;

        for (int i = 0; i < notificationMessages.length; i++) {
            messageTag = new NBTTagCompound();
            messageTag.setString("text", notificationMessages[i]);
            messages.appendTag(messageTag);
        }

        notificationTag.setTag("messages", messages);
        notificationTag.setBoolean("showFireworks", showFireworks);
        notificationTag.setBoolean("showAlertbox", showAlertBox);
        notificationTag.setBoolean("spawnBlock", ConfigBlockSpawnSettings.spawn_block_enabled);

        addNotification(notificationTag);
    }

    /***
     * Remove the top notification from the Queue
     */
    public static void removeTopNotification() {
        if (getNotificationCount() > 0) {
            overlayAlerts.remove(0);
        }
    }

    /***
     * Get the top notification
     * @return
     */
    public static NBTTagCompound getTopNotification() {
        if (getNotificationCount() > 0) {
            return overlayAlerts.get(0);
        }
        return null;
    }

    /***
     * Get the number of alerts in the queue
     * @return
     */
    public static int getNotificationCount() {
        return overlayAlerts.size();
    }

    /***
     * Called every tick, used to handle the alerts
     */
    public static void updateTick() {
        if (Minecraft.getMinecraft().isGamePaused()) { return; }
        if (Minecraft.getMinecraft().thePlayer == null) { return; }
        if (Minecraft.getMinecraft().thePlayer.worldObj == null) { return; }

        if (getNotificationCount() == 0) { return; }

        NBTTagCompound nbtTagCompound = getTopNotification();

        boolean showAlertBox = false;
        boolean showFireworks = false;
        int alertboxNotificationTime = ConfigAlertBoxSettings.alertBox_ShowTime;
        boolean spawnBlock = false;
        int spawnBlockWaitTime = ConfigBlockSpawnSettings.spawn_block_waitTime;

        // Get if we are showing the fireworks
        if (nbtTagCompound.hasKey("showFireworks")) {
            showFireworks = nbtTagCompound.getBoolean("showFireworks");
        }

        // Get if we are showing the alertbox
        if (nbtTagCompound.hasKey("showAlertbox")) {
            showAlertBox = nbtTagCompound.getBoolean("showAlertbox");
        }

        // Get the alertbox show time, if set
        if (nbtTagCompound.hasKey("alertboxShowTime")) {
            alertboxNotificationTime = nbtTagCompound.getInteger("alertboxShowTime");
        }

        // Get if we are spawning a Block
        if (nbtTagCompound.hasKey("spawnBlock")) {
            spawnBlock = nbtTagCompound.getBoolean("spawnBlock");
        }

        if (nbtTagCompound.hasKey("spawnBlockWaitTime")) {
            spawnBlockWaitTime = nbtTagCompound.getInteger("spawnBlockWaitTime");
        }

        if (!showAlertBox) {
            alertboxNotificationTime = 0;
        }

        int maxNotificationTime = (Math.max(alertboxNotificationTime + ConfigAlertBoxSettings.alertBox_CooldownTime, ConfigBlockSpawnSettings.spawn_block_waitTime)) * 20;

        // Check to see if notification has an age, if not create it
        if (!nbtTagCompound.hasKey("messageAge")) {
            nbtTagCompound.setInteger("messageAge", 0);

            if (showFireworks) {
                FireworkHelper.SpawnFireWork();
            }

            if (showAlertBox) {
                OverlayHelper.overlayAlert = nbtTagCompound;
            }
        }

        int messageAge = nbtTagCompound.getInteger("messageAge");

        if (showAlertBox && messageAge >= alertboxNotificationTime * 20) {
            OverlayHelper.overlayAlert = new NBTTagCompound();
        }

        if (spawnBlock && messageAge == spawnBlockWaitTime * 20) {
            SpawnBlockHelper.SpawnBlock();
        }

        if (messageAge >= maxNotificationTime) {
            removeTopNotification();
            return;
        }

        messageAge = messageAge + 1;
        nbtTagCompound.setInteger("messageAge", messageAge);
    }
}
