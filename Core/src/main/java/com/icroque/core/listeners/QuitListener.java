package com.icroque.core.listeners;

import com.icroque.core.utils.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Rémi on 09/01/2016.
 */
public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        PlayerData data = PlayerData.findByName(e.getPlayer().getName());
        if(data != null) {
            data.save();
            data.unload();
        }
        if(e.getPlayer().hasPermission("core.vanish")) {
            e.setQuitMessage(null);
        }
        else {
            e.setQuitMessage("§8[§4-§8] "+ e.getPlayer().getName() +" vient de se déconnecter.");
        }
    }
}
