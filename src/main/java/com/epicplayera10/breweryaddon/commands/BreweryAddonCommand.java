package com.epicplayera10.breweryaddon.commands;

import com.epicplayera10.breweryaddon.commands.subcommands.CreateBookCommand;
import com.epicplayera10.breweryaddon.commands.subcommands.ReloadCommand;
import com.epicplayera10.breweryaddon.commands.subcommands.SubCommand;
import com.epicplayera10.breweryaddon.commands.subcommands.UpdateBookCommand;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BreweryAddonCommand implements CommandExecutor {
    private final List<SubCommand> subCommands;

    public BreweryAddonCommand() {
        this.subCommands = new ArrayList<>();
        this.subCommands.add(new CreateBookCommand());
        this.subCommands.add(new ReloadCommand());
        this.subCommands.add(new UpdateBookCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            for (SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    return subCommand.onCommand(sender, args);
                }
            }
        }
        sender.sendMessage(ChatUtil.fixColors("&c/ba (book)"));

        return true;
    }
}
