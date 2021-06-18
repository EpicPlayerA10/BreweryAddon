package com.epicplayera10.breweryaddon.commands.subcommands;

import com.epicplayera10.breweryaddon.utils.BookUtil;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateBookCommand {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("breweryaddon.getbook"))
        {
            sender.sendMessage(ChatUtil.fixColors("&cYou dont have permissiom to do that!"));
            return false;
        }
        if(args.length == 2) {
            Player p = Bukkit.getPlayer(args[1]);

            if(p == null)
            {
                sender.sendMessage("Player not found!");
                return false;
            }

            long time = System.currentTimeMillis();

            p.sendMessage(ChatUtil.fixColors("&8Generating..."));

            p.getInventory().addItem(BookUtil.createbook());

            long timediff = System.currentTimeMillis() - time;

            p.sendMessage(ChatUtil.fixColors("&aGenerated in " + timediff + "ms!"));
            return true;
        }

        if(!(sender instanceof Player))
        {
            sender.sendMessage("You are not a player!");
            return false;
        }

        Player p = (Player)sender;

        long time = System.currentTimeMillis();

        p.sendMessage(ChatUtil.fixColors("&8Generating..."));

        p.getInventory().addItem(BookUtil.createbook());

        long timediff = System.currentTimeMillis() - time;

        p.sendMessage(ChatUtil.fixColors("&aGenerated in " + timediff + "ms!"));
        return true;
    }
}
