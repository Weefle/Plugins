package com.icroque.core.listeners;

import com.icroque.core.utils.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

/**
 * Created by Rémi on 09/01/2016.
 */
public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        PlayerData data = new PlayerData(e.getPlayer());
        if(e.getPlayer().hasPermission("core.vanish")) {
            e.setJoinMessage(null);
        }
        else {
            e.setJoinMessage("§8[§2+§8] "+ e.getPlayer().getName() +" vient de se connecter.");
        }
    }
}
