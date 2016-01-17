package com.icroque.core.utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Rémi on 10/01/2016.
 */
public class Scoreboard {
    private PlayerData data;
    private String name;
    private String[] lines;

    public Scoreboard(PlayerData data) {
        this.data = data;
        this.lines = new String[16];
    }

    public Scoreboard(Player player) {
        this.data = PlayerData.findByName(player.getName());
    }

    public void setName(String name) {
        PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();
        try {
            ReflectionUtils.setValue(packet, true, "a", data.getPlayerName());
            ReflectionUtils.setValue(packet, true, "d", 2);
            ReflectionUtils.setValue(packet, true, "b", name);
            ReflectionUtils.setValue(packet, true, "c", IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
            this.name = name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendPacket(packet);
    }

    public void setLine(int line, String text) {
        PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore(text);
        try {
            ReflectionUtils.setValue(packet, true, "b", data.getPlayerName());
            ReflectionUtils.setValue(packet, true, "c", line);
            ReflectionUtils.setValue(packet, true, "d", PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE);
            lines[line] = text;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        sendPacket(packet);
    }

    public String[] getLines() {
        return lines;
    }

    private void sendPacket(Packet<?> packet) {
        Player player = Bukkit.getPlayer(data.getPlayerName());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
