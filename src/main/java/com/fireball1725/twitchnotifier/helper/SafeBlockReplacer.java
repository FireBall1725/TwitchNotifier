package com.fireball1725.twitchnotifier.helper;

import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class SafeBlockReplacer {


    private static boolean CheckBlock(World world, BlockPos blockPos, boolean forceAir) {
        IBlockState blockState = world.getBlockState(blockPos);

        if (blockState == null)
            return true;

        // Check to make sure it is an air block, or replaceable...
        if ((blockState.getBlock() instanceof BlockAir && forceAir) || (blockState.getBlock().isReplaceable(world, blockPos) && forceAir))
            return true;
        else if (forceAir)
            return false;

        // Check to make sure it is not an Tile Entity
        if (blockState.getBlock().hasTileEntity(blockState))
            return false;

        // Make sure the block isn't unbreakable
        if (blockState.getBlock().getBlockHardness(world.getBlockState(blockPos), world, blockPos) == -1.0F) {
            return false;
        }

        // Make sure the block is on the whitelist
        //return BreakableWhiteListHelper.checkBlock(blockState);
        return false;
    }

    private static boolean CheckGraveSite(World world, BlockPos blockPos, EnumFacing facing) {
        BlockPos MasterGraveTile = blockPos;

        boolean placeGrave = true;

        if (!CheckBlock(world, MasterGraveTile, true))
            placeGrave = false;

        return placeGrave;
    }

    // This is whats called.
    public static BlockPos GetSafeGraveSite(World world, BlockPos blockPos, EnumFacing facing) {
        BlockPos finalBlockPos = CheckSafeGraveSite(world, blockPos, facing);

        if (finalBlockPos == null) {
            //LogHelper.info("[Graves] Unable to find place to put grave...");
            //LogHelper.info("[Graves] Trying to place the grave on the surface...");

            finalBlockPos = world.getTopSolidOrLiquidBlock(blockPos);
            finalBlockPos = CheckSafeGraveSite(world, finalBlockPos, facing);
        }

        // Check 5 Blocks
        if (finalBlockPos == null) {
            //LogHelper.info("[Graves] Placing on the surface failed (Maybe fell down a 1x1 hole?");
            //LogHelper.info("[Graves] Trying to place the grave on the surface 5 blocks north");

            finalBlockPos = world.getTopSolidOrLiquidBlock(blockPos.north(5));
            finalBlockPos = CheckSafeGraveSite(world, finalBlockPos, facing);
        }

        if (finalBlockPos == null) {
            //LogHelper.info("[Graves] Placing on the surface failed (Maybe fell down a 1x1 hole?");
            //LogHelper.info("[Graves] Trying to place the grave on the surface 5 blocks east");

            finalBlockPos = world.getTopSolidOrLiquidBlock(blockPos.east(5));
            finalBlockPos = CheckSafeGraveSite(world, finalBlockPos, facing);
        }

        if (finalBlockPos == null) {
            //LogHelper.info("[Graves] Placing on the surface failed (Maybe fell down a 1x1 hole?");
            //LogHelper.info("[Graves] Trying to place the grave on the surface 5 blocks south");

            finalBlockPos = world.getTopSolidOrLiquidBlock(blockPos.south(5));
            finalBlockPos = CheckSafeGraveSite(world, finalBlockPos, facing);
        }

        if (finalBlockPos == null) {
            //LogHelper.info("[Graves] Placing on the surface failed (Maybe fell down a 1x1 hole?");
            //LogHelper.info("[Graves] Trying to place the grave on the surface 5 blocks west");

            finalBlockPos = world.getTopSolidOrLiquidBlock(blockPos.west(5));
            finalBlockPos = CheckSafeGraveSite(world, finalBlockPos, facing);
        }

        // Last ditch effort... just place it
        if (finalBlockPos == null) {
            //LogHelper.info("[Graves] Sorry, still can't find a good place...");

            // Make sure Y is ok
            int y = blockPos.getY();

            if (y < 1) {
                y = 3;
            } else if (y > 255) {
                y = 253;
            }

            blockPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());

            finalBlockPos = blockPos;
        }

        return finalBlockPos;
    }

    public static BlockPos CheckSafeGraveSite(World world, BlockPos blockPos, EnumFacing facing) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();

        if (y < 11) {
            y = 11;
        }

        if (y > 245) {
            y = 245;
        }

        int blockX = x;
        int blockY = y;
        int blockZ = z;
        for (int searchY = 0; searchY < 17; searchY++) {
            if (searchY != 0 && searchY % 2 == 0) {
                blockY = y - searchY / 2;
            } else if (searchY != 0) {
                blockY = y + Math.round(searchY / 2) + 1;
            } else {
                blockY = y;
            }

            for (int searchX = 0; searchX < 17; searchX++) {
                if (searchX != 0 && searchX % 2 == 0) {
                    blockX = x - searchX / 2;
                } else if (searchX != 0) {
                    blockX = x + Math.round(searchX / 2) + 1;
                } else {
                    blockX = x;
                }

                for (int searchZ = 0; searchZ < 17; searchZ++) {
                    if (searchZ != 0 && searchZ % 2 == 0) {
                        blockZ = z - searchZ / 2;
                    } else if (searchZ != 0) {
                        blockZ = z + Math.round(searchZ / 2) + 1;
                    } else {
                        blockZ = z;
                    }

                    if (CheckGraveSite(world, new BlockPos(blockX, blockY, blockZ), facing))
                        return new BlockPos(blockX, blockY, blockZ);
                }
            }
        }

        return null;
    }

}
