package com.fireball1725.twitchnotifier.helper;

import com.fireball1725.twitchnotifier.TwitchNotifier;
import com.fireball1725.twitchnotifier.entity.EntityFakeFireworkRocket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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
				if (firework.stackTagCompound == null) {
					firework.setTagCompound(new NBTTagCompound());
				}
				firework.stackTagCompound.setTag("Fireworks", fireworkTag);

				EntityFakeFireworkRocket fireworkRocket = new EntityFakeFireworkRocket(player.worldObj, player.posX, player.posY, player.posZ, firework);

				player.worldObj.spawnEntityInWorld(fireworkRocket);
			}
		}
	}
}
