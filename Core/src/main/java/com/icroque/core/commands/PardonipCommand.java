package com.icroque.core.commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Rémi on 09/01/2016.
 */
public class PardonipCommand extends Command {

    public PardonipCommand() {
        super("pardonip", "core.pardonip", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            Bukkit.getBanList(BanList.Type.IP).pardon(args[0]);
            sender.sendMessage("§f[§aInformation§f] §aL'ip "+ args[0] +" vient d'être débanni.");
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /pardonip <ip>");
        }
    }
}
