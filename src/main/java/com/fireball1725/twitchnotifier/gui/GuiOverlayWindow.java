package com.fireball1725.twitchnotifier.gui;

import com.fireball1725.twitchnotifier.config.ConfigAlertBoxSettings;
import com.fireball1725.twitchnotifier.helper.FireworkHelper;
import com.fireball1725.twitchnotifier.helper.MathHelper;
import com.fireball1725.twitchnotifier.helper.OverlayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import sun.security.krb5.Config;

@SideOnly(Side.CLIENT)
public class GuiOverlayWindow extends Gui {
    private final Minecraft mc;
    private OverlayHelper overlayHelper = new OverlayHelper();

    public GuiOverlayWindow(Minecraft client) {
        this.mc = client;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void afterRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        // Get the first message to display
        NBTTagCompound nbtTagCompound = OverlayHelper.overlayAlert;

        if (nbtTagCompound.hasNoTags()) { return; }

        // Get the messages to display from the NBT tag
        NBTTagList nbtTagList = (NBTTagList)nbtTagCompound.getTag("messages");


        // Get Screen Resolution
        int screenWidth = event.getResolution().getScaledWidth();
        int screenHeight = event.getResolution().getScaledHeight();

        // Reset GL Colors
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int messageStartY = (screenHeight >> 1) - (nbtTagList.tagCount() * 12 >> 1);

        int[] messageLength = new int[nbtTagList.tagCount()];
        int[] messageLeft = new int[nbtTagList.tagCount()];

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound messageTag = nbtTagList.getCompoundTagAt(i);
            String message = messageTag.getString("text");

            int messageWidth = this.mc.fontRendererObj.getStringWidth(message);
            int messageX = (screenWidth >> 1) - (messageWidth >> 1);

            messageLength[i] = messageWidth;
            messageLeft[i] = messageX;
        }

        int windowWidth = MathHelper.getLargestInt(messageLength);
        int windowLeft = MathHelper.getSmallestInt(messageLeft);

        overlayHelper.DrawWindowWithBorder(windowLeft, messageStartY, windowWidth, nbtTagList.tagCount() * 12, ConfigAlertBoxSettings.alertBox_BackgroundColor, ConfigAlertBoxSettings.alertBox_BorderColor);

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound messageTag = nbtTagList.getCompoundTagAt(i);
            String message = messageTag.getString("text");

            int messageWidth = this.mc.fontRendererObj.getStringWidth(message);
            int messageX = (screenWidth >> 1) - (messageWidth >> 1);
            int messageY = messageStartY + (i * 12);

            this.mc.fontRendererObj.drawStringWithShadow(message, messageX, messageY + 2, ConfigAlertBoxSettings.alertBox_TextColor);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
