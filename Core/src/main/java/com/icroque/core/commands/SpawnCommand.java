package com.icroque.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class SpawnCommand extends Command {
    public static Location spawn;

    public SpawnCommand() {
        super("spawn", "core.spawn", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player target;
        if(args.length > 0) {
            target = Bukkit.getPlayer(args[0]);
        }
        else if(sender instanceof Player) {
            target = (Player) sender;
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /spawn <player>");
            return;
        }

        if(spawn != null) {
            target.teleport(spawn);
        }
        else {
            target.teleport(target.getWorld().getSpawnLocation());
        }
    }
}
