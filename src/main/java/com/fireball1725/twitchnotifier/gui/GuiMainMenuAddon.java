package com.fireball1725.twitchnotifier.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import scala.collection.parallel.ParIterableLike;

import java.util.List;

public class GuiMainMenuAddon extends GuiMainMenu {
    private static final int BUTTON_CONFIG_ID = 1725;

    @Override
    public void initGui() {
        super.initGui();
        onGuiInit(this, buttonList);
    }

    public static void onGuiInit(GuiScreen screen, List buttonList) {
        //TODO: Init

        boolean blinking = true;
        GuiButtonConfig buttonConfig = getOrCreateConfigButton(screen, buttonList);
        buttonConfig.setBlinking(blinking);
        buttonConfig.setIcon(GuiConfigIcons.TWITCH);
    }

    protected static GuiButtonConfig getOrCreateConfigButton(GuiScreen screen, List<GuiButton> buttonList) {
        for (GuiButton button : buttonList)
            if (button instanceof GuiButtonConfig) return (GuiButtonConfig)button;

        GuiButtonConfig buttonConfig = new GuiButtonConfig(BUTTON_CONFIG_ID, screen.width / 2 - 124, screen.height / 4 + 48 + 24 * 2);
        buttonList.add(buttonConfig);
        return buttonConfig;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicktime) {
        super.drawScreen(mouseX, mouseY, partialTicktime);
        onScreenDraw(this);
    }

    public static void onScreenDraw(GuiScreen screen) {
        //screen.drawCenteredString(CompatiblityAdapter.getFontRenderer(), notification.getFormatted(), screen.width / 2, screen.height / 4 + 48 + 24 * 3, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == BUTTON_CONFIG_ID) onActionPerformed(mc, this);
        else super.actionPerformed(button);
    }

    public static void onActionPerformed(Minecraft mc, GuiScreen screen, GuiButton button) {
        if (button.id == BUTTON_CONFIG_ID) onActionPerformed(mc, screen);
    }

    public static void onActionPerformed(Minecraft mc, GuiScreen screen) {
        //
    }
}
