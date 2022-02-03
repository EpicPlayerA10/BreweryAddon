package com.epicplayera10.breweryaddon.utils;

import com.dre.brewery.recipe.BEffect;
import com.dre.brewery.recipe.BRecipe;
import com.dre.brewery.recipe.PluginItem;
import com.dre.brewery.recipe.RecipeItem;
import com.epicplayera10.breweryaddon.BreweryAddon;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class RecipeBookCreator {
    private static WeakReference<ItemStack> cachedBook;
    private static final NamespacedKey bookKey = new NamespacedKey(BreweryAddon.getInstance(), "breweryaddon");

    public static ItemStack createRecipeBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setAuthor(BreweryAddon.getInstance().getConfig().getString("book.author"));
        meta.setTitle(ChatUtil.fixColors(BreweryAddon.getInstance().getConfig().getString("book.title")));
        meta.setPages(createPages());
        meta.getPersistentDataContainer().set(bookKey, PersistentDataType.STRING, "ba_recipebook");

        if (BreweryAddon.getInstance().getConfig().getBoolean("book.updatebook")) {
            BaseComponent[] page = new ComponentBuilder(ChatUtil.fixColors("     &a&l&nUPDATE BOOK"))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ba updatebook"))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatUtil.fixColors("&eClick!"))))
                    .create();

            meta.spigot().addPage(page);
        }

        book.setItemMeta(meta);

        return book;
    }

    private static List<String> createPages() {
        List<String> pages = new ArrayList<>();

        for (BRecipe recipe : BRecipe.getConfigRecipes()) {

            StringBuilder bookBuilder = new StringBuilder();
            bookBuilder.append(ChatColor.stripColor(recipe.getName(5) + ":"));

            // Ingredients
            {
                StringBuilder ingredientsBuilder = new StringBuilder();
                boolean first = true;
                for (RecipeItem item : recipe.getIngredients()) {
                    if (item instanceof PluginItem) {
                        if (first) {
                            ingredientsBuilder.append(item.getAmount() + " " + ChatUtil.formatText(((PluginItem) item).getItemId()));
                            first = false;
                        } else {
                            ingredientsBuilder.append(", " + item.getAmount() + " " + ChatUtil.formatText(((PluginItem) item).getItemId()));
                        }
                    } else if (item.hasMaterials()) {
                        if (first) {
                            ingredientsBuilder.append(item.getAmount() + " (");
                            boolean first2 = true;
                            for (Material material : item.getMaterials()) {
                                if (first2) {
                                    ingredientsBuilder.append(ChatUtil.formatText(material.getKey().getKey()));
                                    first2 = false;
                                } else {
                                    ingredientsBuilder.append(", " + ChatUtil.formatText(material.getKey().getKey()));
                                }
                            }

                            ingredientsBuilder.append(")");

                            first = false;
                        } else {
                            ingredientsBuilder.append(", " + item.getAmount() + " (");
                            boolean first2 = true;
                            for (Material material : item.getMaterials()) {
                                if (first2) {
                                    ingredientsBuilder.append(ChatUtil.formatText(material.getKey().getKey()));
                                    first2 = false;
                                } else {
                                    ingredientsBuilder.append(", " + ChatUtil.formatText(material.getKey().getKey()));
                                }
                            }

                            ingredientsBuilder.append(")");
                        }
                    } else {
                        if (first) {
                            ingredientsBuilder.append(item.getAmount() + " " + ChatUtil.formatText(item.getMaterials().get(0).getKey().getKey()));
                            first = false;
                        } else {
                            ingredientsBuilder.append(", " + item.getAmount() + " " + ChatUtil.formatText(item.getMaterials().get(0).getKey().getKey()));
                        }
                    }
                }

                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.ingredients", ingredientsBuilder.toString()));
            }

            bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.cookingtime", recipe.getCookingTime()));
            if (recipe.needsDistilling()) {
                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.distillation", ChatUtil.formatNumericText(recipe.getDistillRuns(), BreweryAddon.getLanguageLoader().get("recipesbook.distillruns.1"), BreweryAddon.getLanguageLoader().get("recipesbook.distillruns.2"), BreweryAddon.getLanguageLoader().get("recipesbook.distillruns.5"))));
            } else {
                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.distillation", BreweryAddon.getLanguageLoader().get("recipesbook._no")));
            }

            if (recipe.needsToAge()) {
                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.aging", ChatUtil.formatNumericText((int) recipe.getAge(), BreweryAddon.getLanguageLoader().get("recipesbook.years.1"), BreweryAddon.getLanguageLoader().get("recipesbook.years.2"), BreweryAddon.getLanguageLoader().get("recipesbook.years.5"))));
                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.wood", convertIdToWood(recipe.getWood())));
            } else {
                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.aging", BreweryAddon.getLanguageLoader().get("recipesbook._no")));
            }

            if (recipe.getAlcohol() != 0) {
                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.alcohol", recipe.getAlcohol()));
            }

            if (!recipe.getEffects().isEmpty()) {
                StringBuilder effectsBuilder = new StringBuilder();
                for (BEffect bEffect : recipe.getEffects()) {
                    PotionEffect effect = bEffect.generateEffect(10);
                    if (effect != null) {
                        int duration = effect.getDuration();
                        int amplifier = effect.getAmplifier() + 1;
                        String name = EffectUtil.fixEffect(effect.getType());

                        effectsBuilder.append("\n- " + name + " " + amplifier + "/" + duration / 20 + "s");
                    }
                }

                bookBuilder.append("\n" + BreweryAddon.getLanguageLoader().get("recipesbook.effects", effectsBuilder.toString()));
            }

            pages.add(bookBuilder.toString());
        }


        return pages;
    }

    private static String convertIdToWood(byte wood) {
        String woodName = "";
        if (wood == 0)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.any");
        else if (wood == 1)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.birch");
        else if (wood == 2)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.oak");
        else if (wood == 3)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.jungle");
        else if (wood == 4)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.spruce");
        else if (wood == 5)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.acacia");
        else if (wood == 6)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.dark_oak");
        else if (wood == 7)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.crimson");
        else if (wood == 8)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.warped");

        return woodName;
    }

    public static boolean isRecipeBook(@Nullable ItemStack book) {
        return book != null && book.getType() == Material.WRITTEN_BOOK && book.hasItemMeta() && book.getItemMeta().getPersistentDataContainer().has(bookKey, PersistentDataType.STRING) && book.getItemMeta().getPersistentDataContainer().get(bookKey, PersistentDataType.STRING).equals("ba_recipebook");
    }
}
