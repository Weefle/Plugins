package com.icroque.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class WeatherCommand extends Command {

    public WeatherCommand() {
        super("weather", "core.weather", true);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if(label.equalsIgnoreCase("sun")) {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
            player.sendMessage("§f[§aInformation§f] §aLe temps a été mise à jour.");
        }
        else if(label.equalsIgnoreCase("storm") || label.equalsIgnoreCase("rain")) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(true);
            player.sendMessage("§f[§aInformation§f] §aLe temps a été mise à jour.");
        }
        else if(label.equalsIgnoreCase("weather") || label.equalsIgnoreCase("wea")) {
            if(args.length > 0) {
                if(args[0].equalsIgnoreCase("sun") || args[0].equalsIgnoreCase("clear")) {
                    player.getWorld().setStorm(false);
                    player.getWorld().setThundering(false);
                    player.sendMessage("§f[§aInformation§f] §aLe temps a été mise à jour.");
                }
                else if(args[0].equalsIgnoreCase("storm") || args[0].equalsIgnoreCase("rain")) {
                    player.getWorld().setStorm(true);
                    player.getWorld().setThundering(true);
                    player.sendMessage("§f[§aInformation§f] §aLe temps a été mise à jour.");
                }
                else {
                    help(sender);
                }
            }
            else {
                help(sender);
            }
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage("§f[§cErreur§f] §cUsage: /weather <storm:rain:sun:clear>");
    }
}
