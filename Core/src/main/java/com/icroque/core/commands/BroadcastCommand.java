package com.icroque.core.commands;

import com.icroque.core.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Rémi on 10/01/2016.
 */
public class BroadcastCommand extends Command {

    public BroadcastCommand() {
        super("broadcast", "core.broadcast", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            String msg = TextUtils.translateColor(TextUtils.recompile(0, args));
            Bukkit.broadcastMessage("§f[§aMessage§f] "+ msg);
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /broadcast <message>");
        }
    }
}
