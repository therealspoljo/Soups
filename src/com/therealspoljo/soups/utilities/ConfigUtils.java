package com.therealspoljo.soups.utilities;

import java.util.List;

import org.bukkit.Sound;

import com.therealspoljo.soups.Main;

public final class ConfigUtils {

    private ConfigUtils() {
    }

    public static List<String> getEnabledWorlds() {
	return Main.getInstance().getConfig().getStringList("enabled-worlds");
    }

    public static boolean isRegionBased() {
	return Main.getInstance().getConfig().getBoolean("region-based.enabled", false);
    }

    public static List<String> getEnabledRegions() {
	return Main.getInstance().getConfig().getStringList("region-based.regions");
    }

    public static boolean isFastHealth() {
	return Main.getInstance().getConfig().getBoolean("health-regeneration.enabled", true);
    }

    public static double getFastHealthAmount() {
	return Main.getInstance().getConfig().getDouble("health-regeneration.amount", 6);
    }

    public static Sound getFastHealthSound() {
	return Sound.valueOf(Main.getInstance().getConfig().getString("health-regeneration.sound", "ENTITY_PLAYER_BURP").toUpperCase());
    }

    public static boolean isFastHunger() {
	return Main.getInstance().getConfig().getBoolean("hunger-regeneration.enabled", true);
    }

    public static int getFastHungerAmount() {
	return Main.getInstance().getConfig().getInt("hunger-regeneration.amount", 6);
    }

    public static int getCooldown() {
	return Main.getInstance().getConfig().getInt("refill.cooldown", 30);
    }

    public static int getRefillAmount() {
	return Main.getInstance().getConfig().getInt("refill.amount", 20);
    }

    public static boolean isEconomy() {
	return Main.getInstance().getConfig().getBoolean("refill.economy.enabled", true);
    }

    public static double getPerSoup() {
	return Main.getInstance().getConfig().getDouble("refill.economy.cost-per-soup", 0.5);
    }

    public static Sound getFastHungerSound() {
	return Sound.valueOf(Main.getInstance().getConfig().getString("hunger-regeneration.sound", "ENTITY_PLAYER_BURP").toUpperCase());
    }
}