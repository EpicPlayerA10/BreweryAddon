package com.epicplayera10.breweryaddon.utils;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String formatText(String text) {
        String newtext;

        newtext = text.replace('_', ' ');

        newtext = newtext.toLowerCase();

        newtext = newtext.substring(0, 1).toUpperCase() + newtext.substring(1);
        return newtext;
    }

    public static String formatNumericText(int number, String number1, String number2, String number5) {
        if (number == 1)
            return number + " " + number1;
        else if (number <= 4)
            return number + " " + number2;
        else return number + " " + number5;
    }
}
