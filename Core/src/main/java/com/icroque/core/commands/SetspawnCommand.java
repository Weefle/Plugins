package com.icroque.core.commands;

import com.icroque.core.Core;
import com.icroque.core.utils.ConfigUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class SetspawnCommand extends Command {

    public SetspawnCommand() {
        super("setspawn", "core.setspawn", true);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        SpawnCommand.spawn = player.getLocation();
        ConfigUtils.setLocation(Core.instance.getConfig(), "spawn", player.getLocation());
        Core.instance.saveConfig();
        player.sendMessage("§f[§aInformation§f] §aLe spawn a été mis à jour.");
    }
}
