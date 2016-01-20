package com.icroque.core.listeners;

import com.icroque.core.utils.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Rémi on 10/01/2016.
 */
public class TeleportListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        PlayerData.findByName(player.getName()).setLastTeleport(e.getFrom());
    }
}
