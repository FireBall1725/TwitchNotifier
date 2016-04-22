package com.fireball1725.twitchnotifier.helper;

import com.fireball1725.twitchnotifier.TwitchNotifier;
import com.fireball1725.twitchnotifier.config.ConfigBlockSpawnSettings;
import com.fireball1725.twitchnotifier.entity.EntityFakeFireworkRocket;
import com.fireball1725.twitchnotifier.network.PacketHandler;
import com.fireball1725.twitchnotifier.network.message.PacketSpawnBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import sun.security.krb5.Config;

import java.util.Random;

public class FireworkHelper {

	public static void SpawnFireWork() {
		EntityPlayer player;
		byte[] flight;

		player = Minecraft.getMinecraft().thePlayer;

		if (player.getEntityWorld().isRemote) {
			flight = new byte[] {-2, -1, 0, 1};
			for (int i = flight.length; i-- > 0; ) {
				NBTTagCompound fireworkTag = new NBTTagCompound();
				fireworkTag.setByte("Flight", flight[i]);
				NBTTagList explosionsTag = new NBTTagList();
				NBTTagCompound explosion = new NBTTagCompound();
				int[] fireworkColors = {0x00FF00, 0x2227B5, 0x377337, 0xE0FF7A, 0xFF00F7, 0xC96634, 0x492775, 0x48D4A3};
				explosion.setIntArray("Colors", new int[] {fireworkColors[TwitchNotifier.random.nextInt(fireworkColors.length)], fireworkColors[TwitchNotifier.random.nextInt(fireworkColors.length)]});
				explosionsTag.appendTag(explosion);
				fireworkTag.setTag("Explosions", explosionsTag);

				ItemStack firework = new ItemStack(Items.fireworks);

				if (!firework.hasTagCompound()) {
					NBTTagCompound nbtTagCompound = new NBTTagCompound();
					nbtTagCompound.setTag("Fireworks", fireworkTag);
					firework.setTagCompound(nbtTagCompound);
				}

				EntityFakeFireworkRocket fireworkRocket = new EntityFakeFireworkRocket(player.worldObj, player.posX, player.posY, player.posZ, firework);

				player.worldObj.spawnEntityInWorld(fireworkRocket);
			}
		}

        //todo: make block spawn...
        if (ConfigBlockSpawnSettings.spawn_block_enabled) {
            int possibleBlocks = ConfigBlockSpawnSettings.spawn_block_blockNames.length;
            Random rnd = new Random();
            int selectedBlock = rnd.nextInt(possibleBlocks);

            String blockName = ConfigBlockSpawnSettings.spawn_block_blockNames[selectedBlock];

            //System.out.println(">>>> BLOCK " + blockName);

            blockName = blockName.trim();

            String[] test = blockName.split(" ");

            Block block = Block.getBlockFromName(test[0]);
            if (block != null) {
                ItemStack itemStack;
                if (test.length > 1) {
                    int blockMeta = Integer.parseInt(test[1]);
                    itemStack = new ItemStack(block, 1, blockMeta);
                } else {
                    itemStack = new ItemStack(block);
                }
                TwitchNotifier.network.sendToServer(new PacketSpawnBlock(itemStack, (int) player.posX, (int) player.posY, (int) player.posZ));
            }
        }
	}
}
