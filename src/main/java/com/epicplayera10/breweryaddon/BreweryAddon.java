package com.epicplayera10.breweryaddon;

import com.epicplayera10.breweryaddon.commands.BreweryAddonCommand;
import com.epicplayera10.breweryaddon.listeners.PlayerInteractListener;
import com.epicplayera10.breweryaddon.utils.EffectUtil;
import com.epicplayera10.breweryaddon.utils.LanguageLoader;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BreweryAddon extends JavaPlugin {
    private static BreweryAddon instance;
    private static LanguageLoader languageLoader;
    private static NamespacedKey namespacedKey;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        languageLoader = new LanguageLoader();
        namespacedKey = new NamespacedKey(BreweryAddon.getInstance(), "breweryaddon");

        new EffectUtil();
        getCommand("ba").setExecutor(new BreweryAddonCommand());
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BreweryAddon getInstance()
    {
        return instance;
    }

    public static LanguageLoader getLanguageLoader() {
        return languageLoader;
    }

    public static NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }

    public void reloadConfigFiles()
    {
        reloadConfig();
        languageLoader.loadLang();
    }
}
