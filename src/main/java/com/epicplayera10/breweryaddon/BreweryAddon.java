package com.epicplayera10.breweryaddon;

import com.epicplayera10.breweryaddon.commands.BreweryAddonCommand;
import com.epicplayera10.breweryaddon.listeners.CheckIngredientsListener;
import com.epicplayera10.breweryaddon.utils.LanguageLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BreweryAddon extends JavaPlugin {
    private static BreweryAddon instance;
    private static LanguageLoader languageLoader;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        languageLoader = new LanguageLoader();

        getCommand("ba").setExecutor(new BreweryAddonCommand());
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new CheckIngredientsListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static BreweryAddon getInstance() {
        return instance;
    }

    public static LanguageLoader getLanguageLoader() {
        return languageLoader;
    }

    public void reloadConfigFiles() {
        reloadConfig();
        languageLoader.loadLang();
    }
}
