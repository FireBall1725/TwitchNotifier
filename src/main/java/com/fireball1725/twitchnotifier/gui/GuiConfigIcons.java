package com.fireball1725.twitchnotifier.gui;

public enum GuiConfigIcons {
    TWITCH(0, 20),
    WARNING(40, 20),
    CRITICAL(60, 20),

    ;

    public final int textureU;
    public final int textureV;

    private GuiConfigIcons(int textureU, int textureV) {
        this.textureU = textureU;
        this.textureV = textureV;
    }

    public static final GuiConfigIcons[] VALUES = values();
}
