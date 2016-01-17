package com.icroque.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly", "core.fly", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                target.setAllowFlight(!target.getAllowFlight());
                target.setFlying(target.getAllowFlight());
                sender.sendMessage("§f[§aInformation§f] §aLe fly est maintenant "+ (target.getAllowFlight() ? "activé" : "désactivé") +" pour le joueur "+ target.getName() +".");
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.setAllowFlight(!player.getAllowFlight());
                player.setFlying(player.getAllowFlight());
                sender.sendMessage("§f[§aInformation§f] §aLe fly est maintenant "+ (player.getAllowFlight() ? "activé" : "désactivé") +".");
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cUsage: /fly <player>");
            }
        }
    }
}
