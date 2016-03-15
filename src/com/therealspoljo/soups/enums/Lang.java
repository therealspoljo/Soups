package com.therealspoljo.soups.enums;

import org.bukkit.command.CommandSender;

import com.therealspoljo.soups.Main;
import com.therealspoljo.soups.utilities.Utils;

public enum Lang {

    TITLE("title", "&4[&6Soups&4]&r "),
    ONLY_PLAYER_COMMAND("only-player-command", "&cThis command can only be run by a player."),
    NO_PERMISSION("no-permission", "&cYou don't have permission to perform this action."),
    CONFIGS_RELOADED("configs-reloaded", "&aConfigs have been reloaded."),
    HEALTH_SET("health-set", "&aYou have set health regeneration to &7%value&a."),
    HUNGER_SET("hunger-set", "&aYou have set hunger regeneration to &7%value&a."),
    HEALTH_TOGGLED("health-toggled", "&aYou have &7%value &ahealth regeneration."),
    HUNGER_TOGGLED("hunger-toggled", "&aYou have &7%value &ahunger regeneration."),
    NOT_NUM("not-number", "&cInput string is not a number."),
    NOT_ENOUGH_MONEY("not-enough-money", "&cYou don't have enough money. Money needed: &7%money_needed&c."),
    PLAYER_OFFLINE("player-offline", "&cPlayer &7%value &cnot found."),
    ON_COOLDOWN("on-cooldown", "&cYou are on a cooldown for &7%value &cmore seconds."),
    FULL_INVENTORY("inventory-full", "&cYour inventory is full."),
    FULL_INVENTORY_OTHER("inventory-full-other", "&7%dName&c's inventory is full."),
    REFILLED("refilled", "&aYou have been refilled with &7%value &asoups."),
    REFILLED_OTHER("refilled-other", "&aYou have refilled &7%dName &awith &7%value &asoups.");

    private String message;

    Lang(String path, String defaultMessage) {
	this.message = Main.getInstance().getLangConfig().getString(path, defaultMessage);
    }

    public String getMessage() {
	return message;
    }

    @Override
    public String toString() {
	return Utils.colorize(TITLE.getMessage()) + Utils.colorize(getMessage());
    }

    public void send(CommandSender sender) {
	sender.sendMessage(this.toString());
    }
}