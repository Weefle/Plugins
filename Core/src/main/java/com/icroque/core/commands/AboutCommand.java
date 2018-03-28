package com.icroque.core.commands;

import com.icroque.core.utils.PlayerData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Rémi on 09/01/2016.
 */
public class AboutCommand extends Command {

    public AboutCommand() {
        super("about", "core.about", false);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void command(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null) {
                sender.sendMessage("§f====== §eA propos de "+ target.getName() +"§f======");
                sender.sendMessage("§f> §eMode de jeu: §f"+ target.getGameMode().name());
                sender.sendMessage("§f> §eVie: §f"+ target.getHealth() +"/"+ target.getMaxHealth());
                sender.sendMessage("§f> §eFly: §f"+ (target.getAllowFlight() ? "activé" : "désactivé"));
                sender.sendMessage("§f> §eAdresse ip: §f"+ target.getAddress().getAddress().toString());
                sender.sendMessage("§f> §eNiveau: §f"+ target.getLevel());
                sender.sendMessage("§f> §eGroupe: §f"+ PlayerData.findByName(target.getName()).getGroup().getName());
                sender.sendMessage("§f> §ePosition: §f"+ Math.floor(target.getLocation().getX()) +"/"+ Math.floor(target.getLocation().getY()) +"/"+ Math.floor(target.getLocation().getZ()));
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    TextComponent seeInv = new TextComponent();
                    seeInv.setText("§f>> §eVoir l'inventaire");
                    seeInv.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inv "+ target.getName()));
                    player.spigot().sendMessage(seeInv);
                }
            }
            else {
                sender.sendMessage("§f[§cErreur§f] §cLe joueur n'est pas connecté.");
            }
        }
        else {
            sender.sendMessage("§f[§cErreur§f] §cUsage: /about <player>");
        }
    }
}
