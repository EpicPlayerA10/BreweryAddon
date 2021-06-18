package com.epicplayera10.breweryaddon.commands.subcommands;

import com.epicplayera10.breweryaddon.BreweryAddon;
import com.epicplayera10.breweryaddon.utils.BookUtil;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.block.Lectern;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LecternInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class UpdateBookCommand {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))
        {
            sender.sendMessage("You are not a player!");
            return false;
        }

        Player p = (Player)sender;
        if(p.getOpenInventory().getType() == InventoryType.LECTERN)
        {
            LecternInventory inv = (LecternInventory) p.getOpenInventory().getTopInventory();
            Lectern lectern = inv.getHolder();
            ItemStack book = lectern.getInventory().getItem(0);
            BookMeta meta = (BookMeta) book.getItemMeta();
            if(book.getType() == Material.WRITTEN_BOOK && meta != null && meta.getPersistentDataContainer().has(BreweryAddon.getNamespacedKey(), PersistentDataType.STRING) && meta.getPersistentDataContainer().get(BreweryAddon.getNamespacedKey(), PersistentDataType.STRING).equals("ba_recipebook"))
            {
                p.sendMessage(ChatUtil.fixColors("&7Updating book"));
                //p.getOpenInventory().close();
                lectern.getInventory().setItem(0, BookUtil.createbook());
                p.sendMessage(ChatUtil.fixColors("&aUpdated"));
                return true;
            }
        } else if(p.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK)
        {
            ItemStack book = p.getInventory().getItemInMainHand();
            BookMeta meta = (BookMeta) book.getItemMeta();

            if(book.getType() == Material.WRITTEN_BOOK && meta != null && meta.getPersistentDataContainer().has(BreweryAddon.getNamespacedKey(), PersistentDataType.STRING) && meta.getPersistentDataContainer().get(BreweryAddon.getNamespacedKey(), PersistentDataType.STRING).equals("ba_recipebook"))
            {
                p.sendMessage(ChatUtil.fixColors("&7Updating book"));
                //p.getOpenInventory().close();
                p.getInventory().setItemInMainHand(BookUtil.createbook());
                p.sendMessage(ChatUtil.fixColors("&aUpdated"));
                return true;
            }

        } else {
            p.sendMessage(ChatUtil.fixColors("&cError!"));
            return false;
        }
        return false;
    }
}
