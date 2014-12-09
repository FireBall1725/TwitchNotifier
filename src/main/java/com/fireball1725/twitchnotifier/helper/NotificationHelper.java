package com.fireball1725.twitchnotifier.helper;

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

    }
}
