package com.icroque.bungeecore.utils;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by Rémi on 16/01/2016.
 */
public class TextUtils {
    /**
     * Return plural if count > 1.
     * @param count int
     * @param singular String
     * @return String
     */
    public static String pluralize(int count, String singular) {
        if(count > 1) return singular +"s";
        else return singular;
    }

    /**
     * Return plural if count > 1.
     * @param count int
     * @param singular String
     * @param plural String
     * @return String
     */
    public static String pluralize(int count, String singular, String plural) {
        if(count > 1) return plural;
        else return singular;
    }

    /**
     * Return string with color translated.
     * @param text String
     * @return String
     */
    public static String translateColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Return array to string
     * @param start int
     * @param args String[]
     * @return String
     */
    public static String recompile(int start, String[] args) {
        StringBuilder bldr = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start) {
                bldr.append(" ");
            }
            bldr.append(args[i]);
        }
        return bldr.toString();
    }
}
