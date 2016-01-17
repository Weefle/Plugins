package com.icroque.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class TeleportCommand extends Command {

    public TeleportCommand() {
        super("teleport", "core.teleport", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            if(args.length > 1) {
                Player player = Bukkit.getPlayer(args[0]);
                Player target = Bukkit.getPlayer(args[1]);
                if(player != null) {
                    if(target != null) {
                        player.teleport(target);
                        if(!player.getName().equalsIgnoreCase(sender.getName())) {
                            sender.sendMessage("§f[§aInformation§f] §aLe joueur "+ player.getName() +" a été au joueur "+ target.getName() +".");
                        }
                    }
                    else {
                        sender.sendMessage("§f[§cErreur§f] §cLe joueur "+ args[1].toLowerCase() +" n'est pas connecté.");
                    }
                }
                else {
                    sender.sendMessage("§f[§cErreur§f] §cLe joueur "+ args[0].toLowerCase() +" n'est pas connecté.");
                }
            }
            else if(sender instanceof Player) {
                Player player = (Player) sender;
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    player.teleport(target);
                }
                else {
                    sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
                }
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cUsage: /tp <player> <target>");
            }
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /tp <target> OU /tp <player> <target>");
        }
    }
}
