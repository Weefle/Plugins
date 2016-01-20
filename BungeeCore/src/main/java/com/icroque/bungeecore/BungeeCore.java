package com.icroque.bungeecore;

import com.google.gson.Gson;
import com.icroque.bungeecore.commands.BroadcastCommand;
import com.icroque.bungeecore.listeners.ServerSwitchListener;
import com.icroque.bungeecore.utils.RabbitMQ;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rémi on 10/01/2016.
 */
public class BungeeCore extends Plugin {
    public static BungeeCore instance;

    @Override
    public void onEnable() {
        instance = this;

        getProxy().getPluginManager().registerCommand(this, new BroadcastCommand());
        getProxy().getPluginManager().registerListener(this, new ServerSwitchListener());

        setupMQ();
    }

    @Override
    public void onDisable() {
        RabbitMQ.getInstance().closeConnection();
    }

    public void setupMQ() {
        RabbitMQ.getInstance().createTopic("minecraft");

        getProxy().getScheduler().schedule(this, () -> {
            List<String> players = new ArrayList<String>();
            getProxy().getPlayers().forEach((ProxiedPlayer p) -> {
                players.add(p.getName());
            });
            RabbitMQ.getInstance().publish("minecraft", "getplayers", new Gson().toJson(players));
            RabbitMQ.getInstance().publish("minecraft", "onlineplayers", new Gson().toJson(getProxy().getOnlineCount()));
        }, 1, 1000, TimeUnit.MILLISECONDS);
    }
}
