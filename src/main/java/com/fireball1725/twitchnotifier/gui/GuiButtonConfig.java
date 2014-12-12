package com.fireball1725.twitchnotifier.gui;

import com.fireball1725.twitchnotifier.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonConfig extends GuiButton {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/buttons.png");
    private GuiConfigIcons icon;
    private boolean blink;
    private int count;

    public GuiButtonConfig(int id, int x, int y) {
        super (id, x, y, 20, 20, "");
    }

    public void setBlinking(boolean blinking) {
        this.blink = blinking;
    }

    public void setIcon(GuiConfigIcons icon) {
        this.icon = icon;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        final boolean mouseOverButton = isMouseOverButton(mouseX, mouseY);

        int textureU;

        if (blink && ((count++ & 0x10) != 0)) textureU = 40;
        else if (mouseOverButton) textureU = 20;
        else textureU = 0;

        drawTexturedModalRect(xPosition, yPosition, textureU, 0, width, height);
        drawTexturedModalRect(xPosition + 2, yPosition + 2, icon.textureU + 2, icon.textureV + 2, 16, 16);
    }

    protected boolean isMouseOverButton(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }
}
