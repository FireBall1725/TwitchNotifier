package com.fireball1725.twitchnotifier.helper;

public class MathHelper {
    public static int getLargestInt(int... ints) {
        int intValue = 0;
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] > intValue) {
                intValue = ints[i];
            }
        }

        return intValue;
    }

    public static int getSmallestInt(int... ints) {
        int intValue = ints[0];
        for (int i = 0; i < ints.length; i++) {
            if (ints[i] < intValue) {
                intValue = ints[i];
            }
        }

        return intValue;
    }
}
