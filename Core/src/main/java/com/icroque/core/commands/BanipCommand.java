package com.icroque.core.commands;

import com.icroque.core.utils.TextUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Rémi on 09/01/2016.
 */
public class BanipCommand extends Command {

    public BanipCommand() {
        super("banip", "core.banip", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            String reason = null;
            if(args.length > 1) {
                reason = TextUtils.recompile(1, args).replace("/", "");
            }

            Bukkit.getBanList(BanList.Type.IP).addBan(args[0], reason, null, sender.getName());
            sender.sendMessage("§f[§aInformation§f] §aL'ip "+ args[0] +" a bien été banni.");
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /banip <ip> [reason]");
        }
    }
}
