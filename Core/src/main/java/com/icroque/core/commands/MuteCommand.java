package com.icroque.core.commands;

import com.icroque.core.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 10/01/2016.
 */
public class MuteCommand extends Command {

    public MuteCommand() {
        super("mute", "core.mute", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                long time = -1;
                if(args.length > 1) {
                    try {
                        time = System.currentTimeMillis() + (Long.parseLong(args[1]) * 1000);
                    }
                    catch (Exception e) {
                        help(sender);
                    }
                }
                PlayerData.findByName(target.getName()).setMute(time);
                sender.sendMessage("§f[§aInformation§f] §aLe joueur "+ target.getName() +" vient d'être muté.");
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            help(sender);
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage("§f[§cErreur§f] §cUsage: /mute <player> [time]");
    }
}
