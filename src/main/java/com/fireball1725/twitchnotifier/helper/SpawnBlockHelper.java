package com.fireball1725.twitchnotifier.helper;

import com.fireball1725.twitchnotifier.TwitchNotifier;
import com.fireball1725.twitchnotifier.config.ConfigBlockSpawnSettings;
import com.fireball1725.twitchnotifier.network.message.PacketSpawnBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class SpawnBlockHelper {
	public static void SpawnBlock() {
		EntityPlayer player;

		player = Minecraft.getMinecraft().thePlayer;

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
