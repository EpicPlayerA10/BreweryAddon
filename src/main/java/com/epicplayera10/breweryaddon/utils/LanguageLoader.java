package com.epicplayera10.breweryaddon.utils;

import com.epicplayera10.breweryaddon.BreweryAddon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class LanguageLoader {
    private FileConfiguration translations;

    public LanguageLoader() {
        File languageDirectory = new File(BreweryAddon.getInstance().getDataFolder(), "languages/");
        File defaultLanguageFile = new File(BreweryAddon.getInstance().getDataFolder(), "languages/en_US.yml");
        if (!languageDirectory.isDirectory()) {
            languageDirectory.mkdir();
            try {
                InputStream stream = BreweryAddon.getInstance().getResource("en_US.yml");
                Files.copy(stream, defaultLanguageFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loadLang();
    }

    @Nullable
    public String get(@NotNull String path, @Nullable Object... args) {
        if (this.translations.getString(path) == null) return null;
        return String.format(this.translations.getString(path), args);
    }

    public void loadLang() {
        this.translations = YamlConfiguration.loadConfiguration(new File(BreweryAddon.getInstance().getDataFolder(), "languages/" + BreweryAddon.getInstance().getConfig().getString("locale") + ".yml"));
    }

}
