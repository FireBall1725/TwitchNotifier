package com.fireball1725.twitchnotifier.helper;

import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public class OverlayHelper extends Gui {
    public static NBTTagCompound overlayAlert = new NBTTagCompound();

    public void OverlayHelper() {}

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
