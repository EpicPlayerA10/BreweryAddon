package com.epicplayera10.breweryaddon.listeners;

import com.dre.brewery.BCauldron;
import com.dre.brewery.BIngredients;
import com.dre.brewery.api.BreweryApi;
import com.dre.brewery.recipe.CustomItem;
import com.dre.brewery.recipe.Ingredient;
import com.dre.brewery.recipe.RecipeItem;
import com.dre.brewery.recipe.SimpleItem;
import com.epicplayera10.breweryaddon.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.lang.reflect.Field;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() != null && e.getItem().getType() == Material.CLOCK) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (p.isSneaking()) {
                    if (e.getClickedBlock().getType() == Material.CAULDRON) {
                        BCauldron cauldron = BreweryApi.getCauldron(e.getClickedBlock());

                        if (cauldron == null) {
                            p.sendMessage(ChatUtil.fixColors("&2[BreweryAddon] &cThere aren't any ingredients in this cauldron"));
                            return;
                        }

                        try {
                            Field f = cauldron.getClass().getDeclaredField("ingredients");
                            f.setAccessible(true);
                            BIngredients ingredients = (BIngredients) f.get(cauldron);

                            StringBuilder sb = new StringBuilder();
                            boolean first = true;
                            sb.append(ChatUtil.fixColors("&aIngredients: "));
                            for (Ingredient ingredient : ingredients.getIngredientList()) {
                                RecipeItem item = (RecipeItem) ingredient;
                                if (first) {
                                    sb.append(ChatUtil.fixColors("&e" + item.getAmount() + " " + ChatUtil.format(item.getMaterials().get(0).getKey().getKey())));
                                    first = false;
                                } else {
                                    sb.append(ChatUtil.fixColors("&e, " + item.getAmount() + " " + ChatUtil.format(item.getMaterials().get(0).getKey().getKey())));
                                }
                            }

                            p.sendMessage(sb.toString());
                        } catch (NoSuchFieldException | IllegalAccessException noSuchFieldException) {
                            noSuchFieldException.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
