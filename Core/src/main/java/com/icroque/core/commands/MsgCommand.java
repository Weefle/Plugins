package com.icroque.core.commands;

import com.icroque.core.utils.PlayerData;
import com.icroque.core.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class MsgCommand extends Command {

    public MsgCommand() {
        super("msg", "core.msg", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                String msg = TextUtils.recompile(1, args);
                target.sendMessage("§f["+ (sender.isOp() ? "§4" : "§e") + sender.getName() +" §f -> "+ (target.isOp() ? "§4" : "§e") +"Moi§f] §6"+ msg);
                sender.sendMessage("§f["+ (target.isOp() ? "§4" : "§e") + target.getName() +" §f <- "+ (sender.isOp() ? "§4" : "§e") +"Moi§f] §6"+ msg);
                if(sender instanceof Player) {
                    PlayerData.findByName(target.getName()).setReplyTo(sender.getName());
                    PlayerData.findByName(sender.getName()).setReplyTo(target.getName());
                }
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /msg <to> <message>");
        }
    }
}
