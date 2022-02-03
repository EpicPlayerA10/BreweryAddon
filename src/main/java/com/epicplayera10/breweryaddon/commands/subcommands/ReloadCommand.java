package com.epicplayera10.breweryaddon.commands.subcommands;

import com.epicplayera10.breweryaddon.BreweryAddon;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends SubCommand {

    public ReloadCommand() {
        super("reload", false);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!sender.hasPermission("breweryaddon.reload")) {
            sender.sendMessage(ChatUtil.fixColors("&cYou don't have permission to do that."));
            return false;
        }

        BreweryAddon.getInstance().reloadConfigFiles();
        sender.sendMessage("Reloaded!");
        return true;
    }
}
