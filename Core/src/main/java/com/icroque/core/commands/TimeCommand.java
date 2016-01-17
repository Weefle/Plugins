package com.icroque.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class TimeCommand extends Command {

    public TimeCommand() {
        super("time", "core.time", true);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        long time;
        if(label.equalsIgnoreCase("day")) {
            time = 600L;
        }
        else if(label.equalsIgnoreCase("night")) {
            time = 16000L;
        }
        else {
            if(args.length > 0) {
                try {
                    time = Long.parseLong(args[0]);
                }
                catch (Exception e) {
                    sender.sendMessage("§f[§cErreur§f] §cLe temps doit être en chiffre.");
                    return;
                }
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cUsage: /time <time>");
                return;
            }
        }
        player.getWorld().setTime(time);
        player.sendMessage("§f[§aInformation§f] §aL'heure a été mise à jour.");
    }
}
