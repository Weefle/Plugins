package com.icroque.core.listeners;

import com.icroque.core.utils.DateUtils;
import com.icroque.core.utils.PlayerData;
import com.icroque.core.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rémi on 09/01/2016.
 */
public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        PlayerData data = PlayerData.findByName(e.getPlayer().getName());
        if(data.isMuted()) {
            if(data.getMuteTime() == -1) {
                player.sendMessage("§cErreur: vous avez été mute à vie.");
            }
            else {
                Calendar calendar = DateUtils.getCalendar(data.getMuteTime());
                player.sendMessage("§cErreur: vous avez été mute jusqu'au "+ DateUtils.getDay(calendar.getTime()) +"/"+ DateUtils.getMonth(calendar.getTime()) +" à "+ DateUtils.getHour(calendar.getTime()) +"h"+ DateUtils.getMinute(calendar.getTime()) +".");
            }
            e.setCancelled(true);
            return;
        }
        e.setFormat(TextUtils.translateColor(data.getGroup().getChatFormat().replace("{PLAYER}", player.getName()).replace("{MESSAGE}", e.getMessage())));
    }
}
