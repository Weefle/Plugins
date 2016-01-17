package com.icroque.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear", "core.clear", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            if(args.length > 0) {
                clearOther(sender, args[0]);
            }
            else {
                help(sender);
            }
        }
        else {
            if(args.length > 0) {
                clearOther(sender, args[0]);
            }
            else {
                Player player = (Player) sender;
                player.getInventory().clear();
                player.updateInventory();
                player.sendMessage("§f[§aInformation§f] §aVotre inventaire a bien été vidé.");
            }
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage("§f[§cErreur§f] §cUsage: /clear [player]");
    }

    private void clearOther(CommandSender sender, String target) {
        Player player = Bukkit.getPlayer(target);

        if(player != null) {
            player.getInventory().clear();
            player.updateInventory();
            sender.sendMessage("§f[§aInformation§f] §aL'inventaire de "+ target +" a bien été vidé.");
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
        }
    }
}
