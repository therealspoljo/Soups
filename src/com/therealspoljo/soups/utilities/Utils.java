package com.therealspoljo.soups.utilities;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.therealspoljo.soups.Main;

public class Utils {

    public static String colorize(String string) {
	return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static int getFreeSpace(Inventory inventory) {
	int amount = 0;

	for (ItemStack itemStack : inventory.getStorageContents()) {
	    if (itemStack == null || itemStack.getType() == null || itemStack.getType() == Material.AIR) {
		amount++;
	    }
	}

	return amount;
    }

    public static void sendHelpPage(CommandSender sender, String label) {
	sender.sendMessage("§7==================[ §e" + Main.getInstance().getName() + " v" + Main.getInstance().getDescription().getVersion()
		+ " §7]==================");
	sender.sendMessage("§7/" + label + " <sub_commands>");
	sender.sendMessage(" ");
	sender.sendMessage("§7Sub commands:");
	sender.sendMessage("§7reload(rl)" + " §e- §7" + "Reloads config files.");
	sender.sendMessage("§7health(he)" + " §e- §7" + "Toggles health regeneration.");
	sender.sendMessage("§7health(he) <amount>" + " §e- §7" + "Changes health regeneration amount.");
	sender.sendMessage("§7hunger(hu)" + " §e- §7" + "Toggles hunger regen.");
	sender.sendMessage("§7hunger(hu) <amount>" + " §e- §7" + "Changes hunger regeneration amount.");
    }

    public static boolean isInRegion(Location location) {
	WorldGuardPlugin worldGuard = Main.getInstance().getWorldGuard();

	if (worldGuard == null) {
	    return false;
	}

	RegionManager regionManager = worldGuard.getRegionManager(location.getWorld());
	ApplicableRegionSet set = regionManager.getApplicableRegions(toWEVector(location.toVector()));

	LinkedList<String> regions = new LinkedList<String>();

	for (ProtectedRegion region : set) {
	    String id = region.getId();
	    regions.add(id);
	}

	if (regions.size() == 0) {
	    return false;
	}

	List<String> enabledRegions = ConfigUtils.getEnabledRegions();

	for (String regionName : regions) {
	    if (enabledRegions.contains(regionName)) {
		return true;
	    }
	}

	return false;
    }

    private static Vector toWEVector(org.bukkit.util.Vector vector) {
	return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }
}