package com.therealspoljo.soups.enums;

import org.bukkit.command.CommandSender;

import com.therealspoljo.soups.Main;
import com.therealspoljo.soups.utilities.Utils;

public enum Lang {

    TITLE("title", "&7[&eSoups&7]&r "),
    ONLY_PLAYER_COMMAND("only-player-command", "&cThis command can only be run by a player."),
    NO_PERMISSION("no-permission", "&cYou don't have permission to perform this action."),
    CONFIGS_RELOADED("configs-reloaded", "&7Configs have been reloaded."),
    HEALTH_SET("health-set", "&7aYou have set health regeneration to &e%value&7."),
    HUNGER_SET("hunger-set", "&7You have set hunger regeneration to &e%value&7."),
    HEALTH_TOGGLED("health-toggled", "&7You have &e%value &7health regeneration."),
    HUNGER_TOGGLED("hunger-toggled", "&7You have &e%value &7hunger regeneration."),
    NOT_NUM("not-number", "&cInput string is not a number."),
    PLAYER_OFFLINE("player-offline", "&cPlayer &e%value &cnot found."),
    ON_COOLDOWN("on-cooldown", "&cYou are on a cooldown for &e%value &cmore seconds."),
    REFILLED("refilled", "&7You have been refilled with &e%value &7soups."),
    REFILLED_OTHER("refilled-other", "&7You have refilled &e%dName &7with &e%value &7soups."),
    FULL_INVENTORY("inventory-full", "&cYour inventory is full."),
    FULL_INVENTORY_OTHER("inventory-full-other", "&e%dName&c's inventory is full."),
    NOT_ENOUGH_MONEY("not-enough-money", "&cYou don't have enough money. Money needed: &e%money_needed&c.");

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