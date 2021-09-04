package com.epicplayera10.breweryaddon.commands.subcommands;

import com.epicplayera10.breweryaddon.BreweryAddon;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("breweryaddon.reload"))
        {
            sender.sendMessage(ChatUtil.fixColors("&cYou dont have permission to do that."));
            return false;
        }

        BreweryAddon.getInstance().reloadConfigFiles();
        sender.sendMessage("Reloaded!");
        return true;
    }
}
