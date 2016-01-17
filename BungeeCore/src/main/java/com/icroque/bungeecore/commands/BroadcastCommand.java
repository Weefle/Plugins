package com.icroque.bungeecore.commands;

import com.icroque.bungeecore.BungeeCore;
import com.icroque.bungeecore.utils.TextUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Rémi on 16/01/2016.
 */
public class BroadcastCommand extends Command {

    public BroadcastCommand() {
        super("bbroadcrast", "bungeecore.broadcast", "bb", "bbroad");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        BungeeCore.instance.getProxy().broadcast(new TextComponent(ChatColor.translateAlternateColorCodes('&', TextUtils.recompile(0, args))));
    }
}
