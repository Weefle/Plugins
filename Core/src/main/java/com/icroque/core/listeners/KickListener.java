package com.icroque.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rémi on 09/01/2016.
 */
public class KickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKick(PlayerKickEvent e) {
        List<String> infos = Arrays.asList(e.getReason().split("/"));
        e.setReason("Vous avez été "+ (infos.size() > 2 ? "banni" : "kické") +" par "+ infos.get(0) + (infos.get(1).equalsIgnoreCase("null") ? ".": " pour "+ infos.get(1) +"."));
    }
}
