package com.icroque.core.commands;

import com.icroque.core.Group;
import com.icroque.core.utils.PlayerData;
import com.icroque.core.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @TODO group remove
 * @TODO group list
 * @TODO Refactoring
 * Created by Rémi on 09/01/2016.
 */
public class GroupCommand extends Command {

    public GroupCommand() {
        super("group", "core.group", false);
    }

    @Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("help")) {
                help(sender);
            }
            else if(args.length >= 2) {
                if(args[0].equalsIgnoreCase("create")) {
                    if(Group.getByName(args[1]) == null) {
                        new Group(args[1], false);
                        sender.sendMessage("§f[§aPermissions§f] §aVous venez de créer le groupe "+ args[1] +".");
                    }
                    else {
                        sender.sendMessage("§f[§cPermissions§f] §cCe nom de groupe est déjà pris.");
                    }
                }
                else if(args[0].equalsIgnoreCase("default")) {
                    Group group = Group.getByName(args[1]);
                    if(group != null) {
                        group.setDefault();
                        sender.sendMessage("§f[§aPermissions§f] §aLe groupe "+ group.getName() +" est maintenant par défaut.");
                    }
                    else {
                        sender.sendMessage("§f[§cPermissions§f] §cCe groupe n'existe pas.");
                    }
                }
                else if(args.length >= 3) {
                    if(args[0].equalsIgnoreCase("set")) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null) {
                            Group group = Group.getByName(args[2]);
                            if(group != null) {
                                PlayerData.findByName(target.getName()).setGroup(group);
                                sender.sendMessage("§f[§aPermissions§f] §aLe groupe du joueur "+ args[1] +" a bien été modifié.");
                                if(!target.equals(sender)) {
                                    target.sendMessage("§f[§aPermissions§f] §aVous êtes maintenant dans le groupe "+ group.getName() +".");
                                }
                            }
                            else {
                                sender.sendMessage("§f[§cPermissions§f] §cCe groupe n'existe pas.");
                            }
                        }
                        else {
                            sender.sendMessage("§f[§cPermissions§f] §cLe joueur n'est pas en ligne.");
                        }
                    }
                    else if(args[0].equalsIgnoreCase("perm") && args.length == 3) {
                        Group group = Group.getByName(args[1]);
                        if(group != null) {
                            sender.sendMessage("§f====== §ePermissions du groupe "+ group.getName() +" §f======");
                            for(String perm : group.getPermissions()) {
                                sender.sendMessage("§f > §e"+ perm);
                            }
                        }
                        else {
                            sender.sendMessage("§f[§cPermissions§f] §cCe groupe n'existe pas.");
                        }
                    }
                    else if(args[0].equalsIgnoreCase("chat")) {
                        Group group = Group.getByName(args[1]);
                        if(group != null) {
                            group.setChatFormat(TextUtils.recompile(2, args));
                            sender.sendMessage("§f[§aPermissions§f] §aLe format du chat du groupe "+ group.getName() +" a été mis à jour.");
                        }
                        else {
                            sender.sendMessage("§f[§cPermissions§f] §cCe groupe n'existe pas.");
                        }
                    }
                    else if(args[0].equalsIgnoreCase("tab")) {
                        Group group = Group.getByName(args[1]);
                        if(group != null) {
                            group.setTabFormat(TextUtils.recompile(2, args));
                            sender.sendMessage("§f[§aPermissions§f] §aLe format de la tablist du groupe "+ group.getName() +" a été mis à jour.");
                        }
                        else {
                            sender.sendMessage("§f[§cPermissions§f] §cCe groupe n'existe pas.");
                        }
                    }
                    else if(args.length >= 4) {
                        if(args[0].equalsIgnoreCase("perm")) {
                            Group group = Group.getByName(args[1]);
                            if(group != null) {
                                if(args[2].equalsIgnoreCase("add")) {
                                    group.addPermission(args[3]);
                                    sender.sendMessage("§f[§aPermissions§f] §aLa permission a bien été ajoutée.");
                                }
                                else if(args[2].equalsIgnoreCase("del") || args[2].equalsIgnoreCase("rem") || args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("delete") || args[2].equalsIgnoreCase("rm")) {
                                    group.removePermission(args[3]);
                                    sender.sendMessage("§f[§aPermissions§f] §aLa permission a bien été supprimée.");
                                }
                                else {
                                    help(sender);
                                }
                            }
                            else {
                                sender.sendMessage("§f[§cPermissions§f] §cCe groupe n'existe pas.");
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
            else {
                help(sender);
            }
        }
        else {
            help(sender);
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage("§f====== §eAide aux groupes §f======");
        sender.sendMessage("§f > /§egroup help");
        sender.sendMessage("§f > /§egroup create <group>");
        sender.sendMessage("§f > /§egroup default <group>");
        sender.sendMessage("§f > /§egroup chat <group> <format>");
        sender.sendMessage("§f > /§egroup tab <group> <format>");
        sender.sendMessage("§f > /§egroup perm <group> add <permission>");
        sender.sendMessage("§f > /§egroup perm <group> del <permission>");
        sender.sendMessage("§f > /§egroup perm <group> list");
        sender.sendMessage("§f > /§egroup set <player> <group>");
    }
}
