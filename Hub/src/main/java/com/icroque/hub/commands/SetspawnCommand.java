package com.icroque.hub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 02/01/2016.
 */
public class SetspawnCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	Player player = (Player) sender;
        if(!(sender instanceof Player)) {
            player.sendMessage("§f[§cHub§f] §cSeul un joueur peut définir le spawn du serveur.");
            return true;
        }

        return true;
    }
}
