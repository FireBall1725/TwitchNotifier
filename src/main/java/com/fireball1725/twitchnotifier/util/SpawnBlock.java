package com.fireball1725.twitchnotifier.util;

import net.minecraft.item.ItemStack;

public class SpawnBlock {
    private ItemStack itemStack;
    private int timeTillSpawn;

    public SpawnBlock(ItemStack itemStack, int timeTillSpawn) {
        this.itemStack = itemStack;
        this.timeTillSpawn = timeTillSpawn;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int decrTimeTillSpawn() {
        timeTillSpawn--;
        if (timeTillSpawn < 0)
            timeTillSpawn = 0;
        return timeTillSpawn;
    }
}
