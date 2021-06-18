package com.epicplayera10.breweryaddon.commands;

import com.epicplayera10.breweryaddon.BreweryAddon;
import com.epicplayera10.breweryaddon.commands.subcommands.CreateBookCommand;
import com.epicplayera10.breweryaddon.commands.subcommands.ReloadCommand;
import com.epicplayera10.breweryaddon.commands.subcommands.UpdateBookCommand;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BreweryAddonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length >= 1)
        {
            if(args[0].equalsIgnoreCase("book"))
            {
                return new CreateBookCommand().onCommand(sender, command, label, args);
            } else if(args[0].equalsIgnoreCase("updatebook") && BreweryAddon.getInstance().getConfig().getBoolean("book.updatebook")) {
                return new UpdateBookCommand().onCommand(sender, command, label, args);
            } else if(args[0].equalsIgnoreCase("reload"))
            {
                return new ReloadCommand().onCommand(sender, command, label, args);
            } else {
                sender.sendMessage(ChatUtil.fixColors("&c/ba (book)"));
            }
        } else {
            sender.sendMessage(ChatUtil.fixColors("&c/ba (book)"));
        }
        return true;
    }
}
