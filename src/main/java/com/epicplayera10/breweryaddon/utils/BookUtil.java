package com.epicplayera10.breweryaddon.utils;

import com.dre.brewery.integration.item.SlimefunPluginItem;
import com.dre.brewery.recipe.BEffect;
import com.dre.brewery.recipe.BRecipe;
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

import java.util.ArrayList;
import java.util.List;

public class BookUtil {
    public static ItemStack createbook()
    {
        /*return new BookBuilder()
                .setTitle("Przepisy")
                .setAuthor("BreweryAddon")
                .setPages(createpages())
                .build();*/

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setAuthor(BreweryAddon.getInstance().getConfig().getString("book.author"));
        meta.setTitle(ChatUtil.fixColors(BreweryAddon.getInstance().getConfig().getString("book.title")));
        meta.setPages(createpages());
        meta.getPersistentDataContainer().set(BreweryAddon.getNamespacedKey(), PersistentDataType.STRING, "ba_recipebook");

        if(BreweryAddon.getInstance().getConfig().getBoolean("book.updatebook"))
        {
            BaseComponent[] page = new ComponentBuilder(ChatUtil.fixColors("     &a&l&nUPDATE BOOK"))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ba updatebook"))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatUtil.fixColors("&eClick!"))))
                    .create();

            meta.spigot().addPage(page);
        }

        book.setItemMeta(meta);

        return book;
    }

    public static List<String> createpages()
    {
        List<String> pages = new ArrayList<String>();

        for(BRecipe recipe : BRecipe.getConfigRecipes())
        {

            StringBuilder sb = new StringBuilder();
            sb.append(ChatColor.stripColor(recipe.getName(5)+":"));
            sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.ingredients"));
            boolean first = true;
            for(RecipeItem item : recipe.getIngredients())
            {
                if(item instanceof SlimefunPluginItem)
                {
                    if (first) {
                        sb.append(" "+item.getAmount() + " " + ChatUtil.format(((SlimefunPluginItem)item).getItemId()));
                        first = false;
                    } else {
                        sb.append(", " + item.getAmount() + " " + ChatUtil.format(((SlimefunPluginItem)item).getItemId()));
                    }
                } else if(item.getMaterials().size() != 1) {
                    if(first)
                    {
                        sb.append(" "+item.getAmount()+" (");
                        boolean first2 = true;
                        for(Material material : item.getMaterials())
                        {
                            if (first2) {
                                sb.append(ChatUtil.format(material.getKey().getKey()));
                                first2 = false;
                            } else {
                                sb.append(", "+ChatUtil.format(material.getKey().getKey()));
                            }
                        }

                        sb.append(")");

                        first = false;
                    } else {
                        sb.append(", "+item.getAmount()+" (");
                        boolean first2 = true;
                        for(Material material : item.getMaterials())
                        {
                            if (first2) {
                                sb.append(ChatUtil.format(material.getKey().getKey()));
                                first2 = false;
                            } else {
                                sb.append(", "+ChatUtil.format(material.getKey().getKey()));
                            }
                        }

                        sb.append(")");

                        first = false;
                    }
                } else {
                    if (first) {
                        sb.append(" "+item.getAmount() + " " + ChatUtil.format(item.getMaterials().get(0).getKey().getKey()));
                        first = false;
                    } else {
                        sb.append(", " + item.getAmount() + " " + ChatUtil.format(item.getMaterials().get(0).getKey().getKey()));
                    }
                }
            }

            sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.cookingtime")+recipe.getCookingTime()+" min");
            if(recipe.needsDistilling())
            {
                sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.distillation")+ChatUtil.format((int)recipe.getDistillRuns(), BreweryAddon.getLanguageLoader().get("recipesbook.distillruns.1"), BreweryAddon.getLanguageLoader().get("recipesbook.distillruns.2"), BreweryAddon.getLanguageLoader().get("recipesbook.distillruns.5")));
            } else {
                sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.distillation")+BreweryAddon.getLanguageLoader().get("recipesbook._no"));
            }

            if(recipe.needsToAge())
            {
                sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.aging")+ChatUtil.format((int)recipe.getAge(), BreweryAddon.getLanguageLoader().get("recipesbook.years.1"), BreweryAddon.getLanguageLoader().get("recipesbook.years.2"), BreweryAddon.getLanguageLoader().get("recipesbook.years.5")));
                //sb.append("\n"+(int)recipe.getAge()+" Lat");
                sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.wood")+convertidtowood(recipe.getWood()));
            } else {
                sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.aging")+BreweryAddon.getLanguageLoader().get("recipesbook._no"));
            }

            if(recipe.getAlcohol() != 0) {
                sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.alcohol") + recipe.getAlcohol()+"ml");
            }

            if(recipe.getEffects().size() != 0) {
                sb.append("\n"+BreweryAddon.getLanguageLoader().get("recipesbook.effects"));
                for (BEffect bEffect : recipe.getEffects()) {
                    PotionEffect effect = bEffect.generateEffect(10);
                    if(effect != null) {
                        int duration = effect.getDuration();
                        int amplifier = effect.getAmplifier()+1;
                        String name = EffectUtil.fixeffect(effect.getType().getName());

                        sb.append("\n- " + name + " " + amplifier + "/" + duration / 20 + "s");
                    }

                }
            }

            pages.add(sb.toString());
        }



        return pages;
    }

    public static String convertidtowood(byte wood)
    {
        String woodName = "";
        if(wood == 0)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.any");
        else if(wood == 1)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.birch");
        else if(wood == 2)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.oak");
        else if(wood == 3)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.jungle");
        else if(wood == 4)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.spruce");
        else if(wood == 5)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.acacia");
        else if(wood == 6)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.dark_oak");
        else if(wood == 7)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.crimson");
        else if(wood == 8)
            woodName = BreweryAddon.getLanguageLoader().get("woodName.warped");

        return woodName;
    }
}
