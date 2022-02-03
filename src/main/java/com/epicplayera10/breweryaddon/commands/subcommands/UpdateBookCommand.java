package com.epicplayera10.breweryaddon.commands.subcommands;

import com.epicplayera10.breweryaddon.BreweryAddon;
import com.epicplayera10.breweryaddon.utils.RecipeBookCreator;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.block.Lectern;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LecternInventory;
import org.jetbrains.annotations.NotNull;

public class UpdateBookCommand extends SubCommand {
    public UpdateBookCommand() {
        super("updatebook", true);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!BreweryAddon.getInstance().getConfig().getBoolean("book.updatebook")) return false;

        if (!(sender instanceof Player)) {
            sender.sendMessage("You are not a player!");
            return false;
        }

        Player p = (Player) sender;
        if (p.getOpenInventory().getType() == InventoryType.LECTERN) {
            LecternInventory inv = (LecternInventory) p.getOpenInventory().getTopInventory();
            Lectern lectern = inv.getHolder();
            ItemStack book = lectern.getInventory().getItem(0);
            if (RecipeBookCreator.isRecipeBook(book)) {
                p.sendMessage(ChatUtil.fixColors("&7Updating book"));
                lectern.getInventory().setItem(0, RecipeBookCreator.createRecipeBook());
                p.sendMessage(ChatUtil.fixColors("&aUpdated"));
                return true;
            }
        } else if (p.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK) {
            ItemStack book = p.getInventory().getItemInMainHand();

            if (RecipeBookCreator.isRecipeBook(book)) {
                p.sendMessage(ChatUtil.fixColors("&7Updating book"));
                p.getInventory().setItemInMainHand(RecipeBookCreator.createRecipeBook());
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
