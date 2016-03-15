package com.therealspoljo.soups.commands;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.therealspoljo.soups.Main;
import com.therealspoljo.soups.enums.Lang;
import com.therealspoljo.soups.enums.Permissions;
import com.therealspoljo.soups.utilities.ConfigUtils;
import com.therealspoljo.soups.utilities.Utils;

public class Soups implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
	if (!command.getName().equalsIgnoreCase("soups"))
	    return true;

	if (!Permissions.isAllowed(sender, Permissions.ADMIN)) {
	    Lang.NO_PERMISSION.send(sender);
	    return true;
	}

	if (args.length == 1) {
	    switch (args[0].toLowerCase()) {
	    case "reload":
	    case "rl": {
		Main.getInstance().getConfig().reload();
		Main.getInstance().getLangConfig().reload();
		Lang.CONFIGS_RELOADED.send(sender);
		return true;
	    }

	    case "health":
	    case "he": {
		boolean value = !ConfigUtils.isFastHealth();

		Main.getInstance().getConfig().set("health-regeneration.enabled", value);
		Main.getInstance().getConfig().save();

		String message = Lang.HEALTH_TOGGLED.toString();
		message = message.replaceAll("%value", value ? "enabled" : "disabled");

		sender.sendMessage(message);
		return true;
	    }

	    case "hunger":
	    case "hu": {
		boolean value = !ConfigUtils.isFastHunger();

		Main.getInstance().getConfig().set("hunger-regeneration.enabled", value);
		Main.getInstance().getConfig().save();

		String message = Lang.HUNGER_TOGGLED.toString();
		message = message.replaceAll("%value", value ? "enabled" : "disabled");

		sender.sendMessage(message);
		return true;
	    }
	    }

	    sender.sendMessage("§cUsage: §7" + command.getUsage().replaceAll("<command>", commandLabel));
	    return true;
	} else if (args.length == 2) {
	    if (args[0].equalsIgnoreCase("health") || args[0].equalsIgnoreCase("he")) {
		if (!NumberUtils.isNumber(args[1])) {
		    Lang.NOT_NUM.send(sender);
		    return true;
		}

		double amount = Double.parseDouble(args[1]);

		Main.getInstance().getConfig().set("health-regeneration.amount", amount);
		Main.getInstance().getConfig().save();

		String message = Lang.HEALTH_SET.toString();
		message = message.replaceAll("%value", amount + "");

		sender.sendMessage(message);
		return true;
	    } else if (args[0].equalsIgnoreCase("hunger") || args[0].equalsIgnoreCase("hu")) {
		if (!NumberUtils.isNumber(args[1])) {
		    Lang.NOT_NUM.send(sender);
		    return true;
		}

		int amount = Integer.parseInt(args[1]);

		Main.getInstance().getConfig().set("hunger-regeneration.amount", amount);
		Main.getInstance().getConfig().save();

		String message = Lang.HUNGER_SET.toString();
		message = message.replaceAll("%value", amount + "");

		sender.sendMessage(message);
		return true;
	    }

	    Utils.sendHelpPage(sender, commandLabel);
	}

	Utils.sendHelpPage(sender, commandLabel);
	return true;
    }
}