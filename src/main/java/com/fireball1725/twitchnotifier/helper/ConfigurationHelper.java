package com.fireball1725.twitchnotifier.helper;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHelper {
	public static String getString(Configuration configuration, String name, String category, String defaultValue, String comment, String[] validValues) {
		Property property = configuration.get(category, name, defaultValue);
		property.setValidValues(validValues);
		property.setComment(comment + " [default: " + defaultValue + "]");
		String value = property.getString();

		for (int i = 0; i < validValues.length; i++) {
			if (value.equalsIgnoreCase(validValues[i])) {
				return validValues[i];
			}
		}

		return defaultValue;
	}

	public static String getString(Configuration configuration, String name, String category, String defaultValue, String comment, boolean show) {
		Property property = configuration.get(category, name, defaultValue);
		property.setComment(comment = comment + " [default: " + defaultValue + "]");
		property.setShowInGui(show);
	    return property.getString();
	}

	public static String[] getString(Configuration configuration, String name, String category, String[] defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue, comment);
		return property.getStringList();
	}

	public static boolean getBoolean(Configuration configuration, String name, String category, boolean defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue);
		property.setComment(comment = comment + " [default: " + defaultValue + "]");
		return property.getBoolean(defaultValue);
	}

	public static int getInt(Configuration configuration, String name, String category, int defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue);
		property.setComment(comment = comment + " [default: " + defaultValue + "]");
		return property.getInt(defaultValue);
	}

	public static int[] getInt(Configuration configuration, String name, String category, int[] defaultValue, String comment) {
		Property property = configuration.get(category, name, defaultValue);
		property.setComment(comment);
		return property.getIntList();
	}
}
