package com.fireball1725.twitchnotifier.helper;

import com.fireball1725.twitchnotifier.TwitchNotifier;
import com.fireball1725.twitchnotifier.config.ConfigBlockSpawnSettings;
import com.fireball1725.twitchnotifier.network.message.PacketSpawnBlock;
import com.fireball1725.twitchnotifier.util.SpawnBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlockSpawnHelper {
    private static List<SpawnBlock> spawnBlockList = new ArrayList<SpawnBlock>();

    public static void addSpawnBlock(ItemStack itemStack) {
        if (ConfigBlockSpawnSettings.spawn_block_enabled)
            spawnBlockList.add(new SpawnBlock(itemStack, ConfigBlockSpawnSettings.spawn_block_waitTime * 20));
    }

    public static void updateTick() {
        Iterator<SpawnBlock> iterator = spawnBlockList.iterator();
        while (iterator.hasNext()) {
            SpawnBlock spawnBlock = iterator.next();
            if (spawnBlock.decrTimeTillSpawn() == 0) {
                spawnBlock(spawnBlock.getItemStack());
                iterator.remove();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static void spawnBlock(ItemStack itemStack) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        TwitchNotifier.network.sendToServer(new PacketSpawnBlock(itemStack, (int) player.posX, (int) player.posY, (int) player.posZ));
    }
}
