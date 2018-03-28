package com.icroque.core.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public abstract class Command implements CommandExecutor {
    private String name;
    private String permission;
    private boolean onlyPlayer;

    public Command(String name, String permission, boolean onlyPlayer) {
        this.setName(name);
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(!(sender instanceof Player) && this.onlyPlayer) {
            sender.sendMessage("§f[§cErreur§f] §cSeul un joueur peut effectué cette commande.");
            return true;
        }
        if(sender.hasPermission(this.permission) || sender.isOp() || sender.hasPermission("core.*")) {
            command(sender, cmd, label, args);
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cVous n'avez pas les privilèges nécessaire.");
        }
        return true;
    }

    public abstract void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
