package com.icroque.core.commands;

import com.icroque.core.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 10/01/2016.
 */
public class UnmuteCommand extends Command {

    public UnmuteCommand() {
        super("unmute", "core.mute", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                PlayerData.findByName(target.getName()).setMute(System.currentTimeMillis());
                sender.sendMessage("§f[§aInformation§f] §aLe joueur "+ target.getName() +" a vient être démuté.");
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /unmute <player>");
        }
    }
}
