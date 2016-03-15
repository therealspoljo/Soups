package com.therealspoljo.soups.enums;

import org.bukkit.command.CommandSender;

public enum Permissions {

    ADMIN("soups.admin"),
    F_HEALTH("soups.fhealth"),
    F_HUNGER("soups.fhunger"),
    REFILL("soups.refill"),
    REFILL_FREE("soups.refill.free"),
    REFILL_NO_COOLDOWN("soups.refill.no-cooldown"),
    REFILL_OTHERS("soups.refill.others");

    private String node;

    Permissions(String node) {
	this.node = node;
    }

    public String getNode() {
	return node;
    }

    public static boolean isAllowed(CommandSender sender, Permissions type) {
	return sender.isOp() || sender.hasPermission(type.getNode());
    }
}