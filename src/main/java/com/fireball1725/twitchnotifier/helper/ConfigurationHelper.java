package com.fireball1725.twitchnotifier.helper;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHelper {
	public static String getString(Configuration configuration, String name, String category, String defaultValue, String comment, String[] validValues) {
		Property property = configuration.get(category, name, defaultValue);
		property.setValidValues(validValues);
		property.comment = comment + " [default: " + defaultValue + "]";
		String value = property.getString();

		for (int i = 0; i < validValues.length; i++) {
			if (value.equalsIgnoreCase(validValues[i])) {
				return validValues[i];
			}
		}

		return defaultValue;
	}

	public static String getString(Configuration configuration, String name, String category, String defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue);
		property.comment = comment + " [default: " + defaultValue + "]";
	    return property.getString();
	}

	public static boolean getBoolean(Configuration configuration, String name, String category, boolean defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue);
		property.comment = comment + " [default: " + defaultValue + "]";
		return property.getBoolean(defaultValue);
	}

	public static int getInt(Configuration configuration, String name, String category, int defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue);
		property.comment = comment + " [default: " + defaultValue + "]";
		return property.getInt(defaultValue);
	}

	public static int[] getInt(Configuration configuration, String name, String category, int[] defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue);
		property.comment = comment;
		return property.getIntList();
	}
}
