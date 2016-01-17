package com.icroque.core.commands;

import com.icroque.core.utils.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 10/01/2016.
 */
public class BackCommand extends Command {

    public BackCommand() {
        super("back", "core.back", true);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        PlayerData data = PlayerData.findByName(player.getName());
        if(data.lastTeleport != null) {
            player.teleport(data.lastTeleport);
        }
        else {
            player.sendMessage("§f[§cErreur§f] §cImpossible de trouver votre dernière position.");
        }
    }
}
