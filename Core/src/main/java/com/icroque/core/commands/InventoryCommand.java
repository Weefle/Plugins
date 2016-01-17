package com.icroque.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class InventoryCommand extends Command {

    public InventoryCommand() {
        super("inventory", "core.inventory", true);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                player.openInventory(target.getInventory());
            }
            else {
                player.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            player.sendMessage("§f[§cErreur§f] §cUsage: /inventory <player>");
        }
    }
}
