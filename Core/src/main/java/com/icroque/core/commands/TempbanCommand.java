package com.icroque.core.commands;

import com.icroque.core.utils.TextUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

/**
 * Created by Rémi on 09/01/2016.
 */
public class TempbanCommand extends Command {

    public TempbanCommand() {
        super("tempban", "core.tempban", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                String reason = null;
                if(args.length > 2) {
                    reason = TextUtils.recompile(2, args).replace("/", "");
                }

                Date date = new Date();
                date.setTime(date.getTime() + (Integer.parseInt(args[1]) * 1000));
                Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, date, sender.getName());
                target.kickPlayer(sender.getName() +"/"+ reason +"/"+ true);
                sender.sendMessage("§f[§aInformation§f] §aLe joueur "+ args[0] +" a bien été banni.");
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /tempban <player> <time> [reason]");
        }
    }
}
