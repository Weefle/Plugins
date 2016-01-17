package com.icroque.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Rémi on 09/01/2016.
 */
public class ConfigUtils {

    public static Location getLocation(FileConfiguration config, String path) {
        String world = config.getString(path +".world");
        if(world != null) {
            return new Location(
                    Bukkit.getWorld(world),
                    config.getDouble(path +".x"),
                    config.getDouble(path +".y"),
                    config.getDouble(path +".z"),
                    (float) config.getDouble(path +".yaw"),
                    (float) config.getDouble(path +".pitch")
            );
        }
        return null;
    }

    public static void setLocation(FileConfiguration config, String path, Location location) {
        config.set(path +".world", location.getWorld().getName());
        config.set(path +".x", Math.floor(location.getX()) + 0.5);
        config.set(path +".y", Math.floor(location.getY()));
        config.set(path +".z", Math.floor(location.getZ()) + 0.5);
        config.set(path +".yaw", location.getYaw());
        config.set(path +".pitch", location.getPitch());
    }
}
