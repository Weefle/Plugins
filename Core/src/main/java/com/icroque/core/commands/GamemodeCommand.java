package com.icroque.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gamemode", "core.gamemode", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("gmc")) {
            if(args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    target.setGameMode(GameMode.CREATIVE);
                    sender.sendMessage("§f[§aInformation§f] §aLe mode de jeu de "+ target.getName() +" été mis à jour.");
                }
                else {
                    sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
                }
            }
            else {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setGameMode(GameMode.CREATIVE);
                    sender.sendMessage("§f[§aInformation§f] §aVotre mode de jeu a été mis à jour.");
                }
                else {
                    sender.sendMessage("§f[§cErreur§f] §cUsage: /gmc <player>");
                }
            }
        }
        else if(label.equalsIgnoreCase("gms")) {
            if(args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    target.setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage("§f[§aInformation§f] §aLe mode de jeu de "+ target.getName() +" été mis à jour.");
                }
                else {
                    sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
                }
            }
            else {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage("§f[§aInformation§f] §aVotre mode de jeu a été mis à jour.");
                }
                else {
                    sender.sendMessage("§f[§cErreur§f] §cUsage: /gms <player>");
                }
            }
        }
        else if(label.equalsIgnoreCase("gamemode") || label.equalsIgnoreCase("gm")) {
            if(args.length > 0) {
                if(args.length > 1) {
                    GameMode gm = matchGamemode(args[0]);
                    Player target = Bukkit.getPlayer(args[1]);
                    if(gm != null) {
                        if(target != null) {
                            target.setGameMode(gm);
                            sender.sendMessage("§f[§aInformation§f] §aLe mode de jeu de "+ target.getName() +" été mis à jour.");
                        }
                        else {
                            sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
                        }
                    }
                    else {
                        helpGamemode(sender);
                    }
                }
                else if(sender instanceof Player) {
                    GameMode gm = matchGamemode(args[0]);
                    if(gm != null) {
                        Player player = (Player) sender;
                        player.setGameMode(gm);
                        sender.sendMessage("§f[§aInformation§f] §aVotre mode de jeu a été mis à jour.");
                    }
                    else {
                        helpGamemode(sender);
                    }
                }
                else {
                    help(sender);
                }
            }
            else {
                help(sender);
            }
        }
        else {
            help(sender);
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage("§f[§cErreur§f] §cUsage: /gamemode <gamemode> [player]");
    }

    private void helpGamemode(CommandSender sender) {
        sender.sendMessage("§f[§cErreur§f] §cMode de jeu inconnu. Voici les modes de jeu disponible: creat(c,1), survi(s,0), advent(a,2), spec(sp, 3).");
    }

    private GameMode matchGamemode(String g) {
        GameMode mode = null;
        if (g.equalsIgnoreCase("gmc") || g.equalsIgnoreCase("egmc") || g.contains("creat") || g.equalsIgnoreCase("1") || g.equalsIgnoreCase("c")) {
            mode = GameMode.CREATIVE;
        }
        else if (g.equalsIgnoreCase("gms") || g.equalsIgnoreCase("egms") || g.contains("survi") || g.equalsIgnoreCase("0") || g.equalsIgnoreCase("s")) {
            mode = GameMode.SURVIVAL;
        }
        else if (g.equalsIgnoreCase("gma") || g.equalsIgnoreCase("egma") || g.contains("advent") || g.equalsIgnoreCase("2") || g.equalsIgnoreCase("a")) {
            mode = GameMode.ADVENTURE;
        }
        else if (g.equalsIgnoreCase("gmsp") || g.equalsIgnoreCase("egmsp") || g.contains("spec") || g.equalsIgnoreCase("3") || g.equalsIgnoreCase("sp")) {
            mode = GameMode.SPECTATOR;
        }
        return mode;
    }
}
