package com.therealspoljo.soups.utilities;

import java.util.UUID;
import java.util.WeakHashMap;

public final class TempStorage {

    private TempStorage() {
    }

    private static final WeakHashMap<UUID, Long> cooldowns = new WeakHashMap<>();

    public static boolean isOnCooldown(UUID uuid) {
	if (!cooldowns.containsKey(uuid))
	    return false;
	if (cooldowns.get(uuid) < System.currentTimeMillis()) {
	    removeCooldown(uuid);
	    return false;
	}
	return true;
    }

    public static void updateCooldown(UUID uuid) {
	long time = ConfigUtils.getCooldown() * 1000;
	if (time <= 0)
	    return;
	cooldowns.put(uuid, System.currentTimeMillis() + time);
    }

    public static void removeCooldown(UUID uuid) {
	cooldowns.remove(uuid);
    }

    public static long getTimeLeft(UUID uuid) {
	long timeLeft = -1;
	if (isOnCooldown(uuid)) {
	    long cooldownDone = cooldowns.get(uuid);
	    timeLeft = cooldownDone - System.currentTimeMillis();
	}
	return timeLeft;
    }

    public static double getFormattedTimeLeft(UUID uuid) {
	return getTimeLeft(uuid) / 1000.0;
    }
}