package com.icroque.core.commands;

import com.icroque.core.utils.TextUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class BanCommand extends Command {

    public BanCommand() {
        super("ban", "core.ban", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                String reason = null;
                if(args.length > 1) {
                    reason = TextUtils.recompile(1, args).replace("/", "");
                }

                Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, sender.getName());
                target.kickPlayer(sender.getName() +"/"+ reason +"/"+ true);
                sender.sendMessage("§f[§aInformation§f] §aLe joueur "+ args[0] +" a bien été banni.");
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /ban <player> [reason]");
        }
    }
}
