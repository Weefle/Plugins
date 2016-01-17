package com.icroque.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 10/01/2016.
 */
public class KillallCommand extends Command {

    public KillallCommand() {
        super("killall", "core.killall", true);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        for(Entity entity : player.getWorld().getEntities()) {
            if(!(entity instanceof Painting || entity instanceof ItemFrame || entity instanceof Player)) {
                entity.remove();
            }
        }
        player.sendMessage("§f[§aInformation§f] §aToute les entités du monde ont été tués.");
    }
}
