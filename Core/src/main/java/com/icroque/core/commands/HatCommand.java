package com.icroque.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class HatCommand  extends Command {

    public HatCommand() {
        super("hat", "core.hat", true);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.getInventory().setHelmet(player.getItemInHand());
        player.sendMessage("§eJoli chapeau !");
    }
}
