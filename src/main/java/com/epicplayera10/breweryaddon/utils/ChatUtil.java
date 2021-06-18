package com.epicplayera10.breweryaddon.utils;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String fixColors(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String format(String text)
    {
        String newtext;

        newtext = text.replace('_',' ');

        newtext = newtext.toLowerCase();

        newtext = newtext.substring(0,1).toUpperCase() + newtext.substring(1);
        return newtext;
    }

    public static String format(int number, String text1, String text4, String text5)
    {
        if(number == 1)
            return number + " " + text1;
        else if(number <= 4)
            return number + " " + text4;
        else return number + " " + text5;
    }
}
