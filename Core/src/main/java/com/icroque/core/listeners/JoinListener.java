package com.icroque.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Rémi on 09/01/2016.
 */
public class JoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        if(e.getPlayer().hasPermission("core.vanish")) {
            e.setJoinMessage(null);
        }
        else {
            e.setJoinMessage("§8[§2+§8] "+ e.getPlayer().getName() +" vient de se connecter.");
        }
    }
}
