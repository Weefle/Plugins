package com.icroque.core;

import com.icroque.core.commands.*;
import com.icroque.core.listeners.*;
import com.icroque.core.utils.BungeeUtils;
import com.icroque.core.utils.ConfigUtils;
import com.icroque.core.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.TimeZone;

/**
 * Created by Rémi on 02/01/2016.
 */
public class Core extends JavaPlugin implements Listener {
    public static Core instance;

    @Override
    public void onEnable() {
        instance = this;
        BungeeUtils.getInstance();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        SpawnCommand.spawn = ConfigUtils.getLocation(getConfig(), "spawn");
        Group.load();

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new KickListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportListener(), this);

        getCommand("inventory").setExecutor(new InventoryCommand());
        getCommand("clear").setExecutor(new ClearCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("msg").setExecutor(new MsgCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("banip").setExecutor(new BanipCommand());
        getCommand("tempban").setExecutor(new TempbanCommand());
        getCommand("pardonip").setExecutor(new PardonipCommand());
        getCommand("pardon").setExecutor(new PardonCommand());
        getCommand("about").setExecutor(new AboutCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("time").setExecutor(new TimeCommand());
        getCommand("weather").setExecutor(new WeatherCommand());
        getCommand("setspawn").setExecutor(new SetspawnCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("group").setExecutor(new GroupCommand());
        getCommand("back").setExecutor(new BackCommand());
        getCommand("killall").setExecutor(new KillallCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnmuteCommand());
        getCommand("help").setExecutor(new HelpCommand());

        for(Player target : Bukkit.getOnlinePlayers()) {
            new PlayerData(target);
        }
    }

    @Override
    public void onDisable() {
        try {
            Package.getPackage("com.mongodb");
            Class.forName("org.bson.util.StringRangeSet");
            Class.forName("com.mongodb.LazyDBObject");

            Group.save();
            for(Player target : Bukkit.getOnlinePlayers()) {
                PlayerData data = PlayerData.findByName(target.getName());
                if(data != null) {
                    data.save();
                    data.unload();
                }
            }
        }
        catch (ClassNotFoundException e) {}
        catch (Exception e) {}
    }

    public boolean load() {

        return false;
    }
}
