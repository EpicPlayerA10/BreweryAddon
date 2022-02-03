package com.epicplayera10.breweryaddon.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class SubCommand {
    private final String name;
    private final boolean hidden;

    public SubCommand(String name, boolean hidden) {
        this.name = name;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return hidden;
    }

    public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args);
}
