package com.fireball1725.twitchnotifier.gui;

import com.fireball1725.twitchnotifier.helper.FireworkHelper;
import com.fireball1725.twitchnotifier.helper.MathHelper;
import com.fireball1725.twitchnotifier.helper.OverlayHelper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiOverlayWindow extends Gui {
    private final Minecraft mc;
    private OverlayHelper overlayHelper = new OverlayHelper();

    public GuiOverlayWindow(Minecraft client) {
        this.mc = client;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void afterRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        // If there are no overlays to show, exit...
        if (OverlayHelper.overlayAlerts.isEmpty()) {
            return;
        }

        // If game is paused
        if (Minecraft.getMinecraft().isGamePaused()) {
            return;
        }

        // mc.thePlayer.worldObj.getTotalWorldTime();

        // Get the first message to display
        NBTTagCompound nbtTagCompound = OverlayHelper.overlayAlerts.get(0);

        // Get the messages to display from the NBT tag
        NBTTagList nbtTagList = (NBTTagList)nbtTagCompound.getTag("messages");

        // Create Message Age Tag
        if (!nbtTagCompound.hasKey("messageAge")) {

            nbtTagCompound.setInteger("messageAge", 0);
        }

        int messageAge = nbtTagCompound.getInteger("messageAge");

        // Handle Message Age
        if (!nbtTagCompound.hasKey("messageMaxAge")) {
            nbtTagCompound.setInteger("messageMaxAge", 400);
        }
        if (messageAge >= nbtTagCompound.getInteger("messageMaxAge")) {
            overlayHelper.overlayAlerts.remove(0);
            return;
        } else{
            messageAge++;
            nbtTagCompound.setInteger("messageAge", messageAge);
        }

        // Set firework off at 40
        if (messageAge < 40) {
            // Cooldown on message
            return;
        } else if (messageAge == 40) {
            FireworkHelper.SpawnFireWork();
        }

        // Get Screen Resolution
        int screenWidth = event.resolution.getScaledWidth();
        int screenHeight = event.resolution.getScaledHeight();

        // Reset GL Colors
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int messageStartY = (screenHeight >> 1) - (nbtTagList.tagCount() * 12 >> 1);

        int[] messageLength = new int[nbtTagList.tagCount()];
        int[] messageLeft = new int[nbtTagList.tagCount()];

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound messageTag = nbtTagList.getCompoundTagAt(i);
            String message = messageTag.getString("text");

            int messageWidth = this.mc.fontRenderer.getStringWidth(message);
            int messageX = (screenWidth >> 1) - (messageWidth >> 1);

            messageLength[i] = messageWidth;
            messageLeft[i] = messageX;
        }

        int windowWidth = MathHelper.getLargestInt(messageLength);
        int windowLeft = MathHelper.getSmallestInt(messageLeft);

        overlayHelper.DrawWindowWithBorder(windowLeft, messageStartY, windowWidth, nbtTagList.tagCount() * 12, 0xF0100010, 0xFF00CC00);

        for (int i = 0; i < nbtTagList.tagCount(); i++) {
            NBTTagCompound messageTag = nbtTagList.getCompoundTagAt(i);
            String message = messageTag.getString("text");

            int messageWidth = this.mc.fontRenderer.getStringWidth(message);
            int messageX = (screenWidth >> 1) - (messageWidth >> 1);
            int messageY = messageStartY + (i * 12);

            this.mc.fontRenderer.drawStringWithShadow(message, messageX, messageY + 2, 0x00CC00);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
