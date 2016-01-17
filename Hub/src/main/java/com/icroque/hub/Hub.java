package com.icroque.hub;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Rémi on 02/01/2016.
 */
public class Hub extends JavaPlugin {
    public static Hub instance;

    @Override
    public void onEnable() {
        instance = this;
    }
}
