package com.therealspoljo.soups.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.therealspoljo.soups.Main;
import com.therealspoljo.soups.enums.Lang;
import com.therealspoljo.soups.enums.Permissions;
import com.therealspoljo.soups.utilities.ConfigUtils;
import com.therealspoljo.soups.utilities.TempStorage;
import com.therealspoljo.soups.utilities.Utils;

import net.milkbowl.vault.economy.Economy;

public class Refill implements CommandExecutor {

    private Economy economy;

    public Refill() {
	this.economy = Main.getInstance().getEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
	if (!command.getName().equalsIgnoreCase("refill"))
	    return true;

	if (!Permissions.isAllowed(sender, Permissions.REFILL)) {
	    Lang.NO_PERMISSION.send(sender);
	    return true;
	}

	if (args.length > 1) {
	    sender.sendMessage("§cUsage: §7" + command.getUsage().replaceAll("<command>", commandLabel));
	    return true;
	}

	if (args.length == 1) {
	    if (!Permissions.isAllowed(sender, Permissions.REFILL_OTHERS)) {
		Lang.NO_PERMISSION.send(sender);
		return true;
	    }

	    Player target = Main.getInstance().getServer().getPlayer(args[0]);

	    if (target == null) {
		String message = Lang.PLAYER_OFFLINE.toString();
		message = message.replaceAll("%value", args[0]);

		sender.sendMessage(message);
		return true;
	    }

	    int freeSpace = Utils.getFreeSpace(target.getInventory());

	    if (freeSpace <= 0) {
		String sMessage = Lang.FULL_INVENTORY_OTHER.toString();
		sMessage = sMessage.replaceAll("%dName", target.getDisplayName());

		sender.sendMessage(sMessage);
		return true;
	    }

	    int refillAmount = ConfigUtils.getRefillAmount();
	    int amount = freeSpace;

	    if (refillAmount < freeSpace) {
		amount = refillAmount;
	    }

	    refill(target, amount);

	    String tMessage = Lang.REFILLED.toString();
	    tMessage = tMessage.replaceAll("%value", amount + "");

	    target.sendMessage(tMessage);

	    String sMessage = Lang.REFILLED_OTHER.toString();
	    sMessage = sMessage.replaceAll("%dName", target.getDisplayName());
	    sMessage = sMessage.replaceAll("%value", amount + "");

	    sender.sendMessage(sMessage);
	    return true;
	}

	if (!(sender instanceof Player)) {
	    Lang.ONLY_PLAYER_COMMAND.send(sender);
	    return true;
	}

	Player player = (Player) sender;
	int freeSpace = Utils.getFreeSpace(player.getInventory());

	if (freeSpace <= 0) {
	    Lang.FULL_INVENTORY.send(sender);
	    return true;
	}

	if (!Permissions.isAllowed(player, Permissions.REFILL_NO_COOLDOWN)) {
	    if (ConfigUtils.getCooldown() > 0) {
		if (TempStorage.isOnCooldown(player.getUniqueId())) {
		    String message = Lang.ON_COOLDOWN.toString();
		    message = message.replaceAll("%value", TempStorage.getFormattedTimeLeft(player.getUniqueId()) + "");

		    sender.sendMessage(message);
		    return true;
		}

		TempStorage.updateCooldown(player.getUniqueId());
	    }
	}

	int refillAmount = ConfigUtils.getRefillAmount();
	int amount = freeSpace;

	if (refillAmount < freeSpace) {
	    amount = refillAmount;
	}

	if (ConfigUtils.isEconomy()) {
	    if (!Permissions.isAllowed(player, Permissions.REFILL_FREE)) {
		double cost = amount * ConfigUtils.getPerSoup();

		if (!economy.has(player, cost)) {
		    String message = Lang.NOT_ENOUGH_MONEY.toString();
		    message = message.replaceAll("%money_needed", cost + "");

		    player.sendMessage(message);
		    return true;
		}

		economy.withdrawPlayer(player, cost);
	    }
	}

	refill(player, amount);

	String tMessage = Lang.REFILLED.toString();
	tMessage = tMessage.replaceAll("%value", amount + "");

	player.sendMessage(tMessage);
	return true;
    }

    private void refill(Player player, int amount) {
	for (int i = 0; i < amount; i++) {
	    player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
	}
    }
}