package com.icroque.core.commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Rémi on 09/01/2016.
 */
public class PardonCommand extends Command {

    public PardonCommand() {
        super("pardon", "core.pardon", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);
            sender.sendMessage("§f[§aInformation§f] §aLe joueur "+ args[0] +" vient d'être débanni.");
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /pardon <player>");
        }
    }
}
