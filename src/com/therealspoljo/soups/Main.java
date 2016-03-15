package com.therealspoljo.soups;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.therealspoljo.soups.commands.Refill;
import com.therealspoljo.soups.commands.Soups;
import com.therealspoljo.soups.listeners.PlayerInteract;
import com.therealspoljo.soups.utilities.Config;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

    private static Main instance;
    WorldGuardPlugin worldGuard = null;

    private Config config, langConfig;
    private Economy economy = null;

    @Override
    public void onEnable() {
	if (!setupEconomy()) {
	    getLogger().warning("You need to install Vault for this plugin to work.");
	    getServer().getPluginManager().disablePlugin(this);
	    return;
	}

	instance = this;

	config = Config.createConfig(this, "config");
	langConfig = Config.createConfig(this, "lang");

	registerCommands();
	registerListeners();
    }

    @Override
    public void onDisable() {
	worldGuard = null;
	
	instance = null;
    }

    public static Main getInstance() {
	return instance;
    }

    @Override
    public Config getConfig() {
	return config;
    }

    public Config getLangConfig() {
	return langConfig;
    }

    public Economy getEconomy() {
	return economy;
    }

    private boolean setupEconomy() {
	if (getServer().getPluginManager().getPlugin("Vault") == null) {
	    return false;
	}

	RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);

	if (rsp == null) {
	    return false;
	}

	economy = rsp.getProvider();

	return economy != null;
    }

    private void registerCommands() {
	getCommand("refill").setExecutor(new Refill());
	getCommand("soups").setExecutor(new Soups());
    }

    private void registerListeners() {
	PluginManager manager = getServer().getPluginManager();

	manager.registerEvents(new PlayerInteract(), this);
    }

    public WorldGuardPlugin getWorldGuard() {
	if (worldGuard == null) {
	    worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");

	    if (worldGuard == null) {
		getLogger().warning("WorldGuard not found. We need WG to determine if player is inside an enabled region.");
		return null;
	    }

	    return worldGuard;
	} else {
	    return worldGuard;
	}
    }
}