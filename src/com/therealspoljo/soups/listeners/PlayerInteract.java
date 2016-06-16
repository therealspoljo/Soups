package com.therealspoljo.soups.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.therealspoljo.soups.enums.Permissions;
import com.therealspoljo.soups.utilities.ConfigUtils;
import com.therealspoljo.soups.utilities.Utils;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
	Player player = event.getPlayer();
	Action action = event.getAction();

	if (!ConfigUtils.getEnabledWorlds().contains(player.getWorld().getName())) {
	    return;
	}

	if (!(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
	    return;
	}

	if (player.getHealth() < player.getMaxHealth()) {
	    if (!ConfigUtils.isFastHealth()) {
		return;
	    }

	    if (!Permissions.isAllowed(player, Permissions.F_HEALTH)) {
		return;
	    }

	    if (player.getInventory().getItemInMainHand().getType() != Material.MUSHROOM_SOUP
		    || player.getInventory().getItemInMainHand().getType() != Material.RABBIT_STEW
		    || player.getInventory().getItemInMainHand().getType() != Material.BEETROOT_SOUP) {
		return;
	    }

	    if (ConfigUtils.isRegionBased()) {
		if (!Utils.isInRegion(player.getLocation())) {
		    return;
		}
	    }

	    double amount = ConfigUtils.getFastHealthAmount();

	    player.setHealth(player.getHealth() + amount > player.getMaxHealth() ? player.getMaxHealth() : player.getHealth() + amount);
	    event.getItem().setType(Material.BOWL);
	    player.getWorld().playSound(player.getLocation(), ConfigUtils.getFastHealthSound(), 1, 1);
	    event.setCancelled(true);
	    return;
	}

	if (player.getFoodLevel() < 20) {
	    if (!ConfigUtils.isFastHunger()) {
		return;
	    }

	    if (!Permissions.isAllowed(player, Permissions.F_HUNGER)) {
		return;
	    }

	    if (player.getInventory().getItemInMainHand().getType() != Material.MUSHROOM_SOUP
		    || player.getInventory().getItemInMainHand().getType() != Material.RABBIT_STEW
		    || player.getInventory().getItemInMainHand().getType() != Material.BEETROOT_SOUP) {
		return;
	    }

	    if (ConfigUtils.isRegionBased()) {
		if (!Utils.isInRegion(player.getLocation())) {
		    return;
		}
	    }

	    int amount = ConfigUtils.getFastHungerAmount();

	    player.setFoodLevel(player.getFoodLevel() + amount);
	    event.getItem().setType(Material.BOWL);
	    player.getWorld().playSound(player.getLocation(), ConfigUtils.getFastHungerSound(), 1, 1);
	    event.setCancelled(true);
	    return;
	}
    }
}