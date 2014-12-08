package com.fireball1725.twitchnotifier.helper;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class OverlayHelper extends Gui {
    public static List<NBTTagCompound> overlayAlerts = new ArrayList<NBTTagCompound>();

    public void OverlayHelper() {}

    public static void addOverlay(String... messages) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        NBTTagList nbtTagList = new NBTTagList();
        NBTTagCompound nbtTagCompound1;

        for (int i = 0; i < messages.length; i++) {
            nbtTagCompound1 = new NBTTagCompound();
            nbtTagCompound1.setString("text", messages[i]);
            nbtTagCompound1.setInteger("color", 0x000000);
            nbtTagList.appendTag(nbtTagCompound1);
        }

        nbtTagCompound.setTag("messages", nbtTagList);

        overlayAlerts.add(nbtTagCompound);
    }

    public void DrawWindow(int x, int y, int w, int h, int bgColor) {
        drawRect(x - 3, y - 4, x + w + 3, y - 3, bgColor);
        drawRect(x - 3, y + h + 3, x + w + 3, y + h + 4, bgColor);
        drawRect(x - 3, y - 3, x + w + 3, y + h + 3, bgColor);
        drawRect(x - 4, y - 3, x - 3, y + h + 3, bgColor);
        drawRect(x + w + 3, y - 3, x + w + 4, y + h + 3, bgColor);
    }

    public void DrawWindowWithBorder(int x, int y, int w, int h, int bgColor, int frameColor) {
        DrawWindow(x, y, w, h, bgColor);
        int frameFade = frameColor;
        drawGradientRect(x - 3, y - 3 + 1, x - 3 + 1, y + h + 3 - 1, frameColor, frameFade);
        drawGradientRect(x + w + 2, y - 3 + 1, x + w + 3, y + h + 3 - 1, frameColor, frameFade);
        drawGradientRect(x - 3, y - 3, x + w + 3, y - 3 + 1, frameColor, frameColor);
        drawGradientRect(x - 3, y + h + 2, x + w + 3, y + h + 3, frameFade, frameFade);
    }
}
