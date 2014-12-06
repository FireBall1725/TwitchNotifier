package com.fireball1725.twitchnotifier.helper;

import com.fireball1725.twitchnotifier.lib.Log;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FireworkHelper {

	public static void SpawnFireWork(EntityPlayer player) {
		if (player.getEntityWorld().isRemote) {
			Byte[] flight = new Byte[] {0, 0, 0, 1};
			for (int i = 4; i-- > 0;) {
				NBTTagCompound fireworkTag = new NBTTagCompound();
				fireworkTag.setByte("Flight", flight[i]);
				NBTTagList explosionsTag = new NBTTagList();
				NBTTagCompound explosion = new NBTTagCompound();
				int[] colors = {2437522, 3887386};
				explosion.setIntArray("Colors", colors);
				explosion.setByte("Type", (byte) 0);
				explosionsTag.appendTag(explosion);
				fireworkTag.setTag("Explosions", explosionsTag);

				ItemStack itemStack = new ItemStack(Items.fireworks);
				itemStack.setTagCompound(fireworkTag);

				EntityFireworkRocket entityFireworkRocket = new EntityFireworkRocket(
						player.getEntityWorld(), player.posX, player.posY, player.posZ, itemStack);
				player.getEntityWorld().spawnEntityInWorld(entityFireworkRocket);
			}
		}
	}
}
