package com.icroque.bungeecore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icroque.bungeecore.commands.BroadcastCommand;
import com.icroque.bungeecore.listeners.ServerSwitchListener;
import com.icroque.bungeecore.utils.RabbitMQ;
import com.rabbitmq.client.*;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.io.ObjectOutput;
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

        RabbitMQ.getInstance().createTopic("minecraft");
        getProxy().getScheduler().schedule(this, () -> {
            List<String> players = new ArrayList<String>();
            getProxy().getPlayers().forEach((ProxiedPlayer p) -> {
                players.add(p.getName());
            });
            RabbitMQ.getInstance().publish("minecraft", "getplayers", new Gson().toJson(players));
        }, 1, 1000, TimeUnit.MILLISECONDS);
        //setupMQ();
    }

    @Override
    public void onDisable() {
        RabbitMQ.getInstance().closeConnection();
    }

    public void setupMQ() {
        getProxy().getScheduler().runAsync(this, () -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("192.168.99.100");
                factory.setPort(32773);
                factory.setUsername("root");
                factory.setPassword("root");

                Connection connection = factory.newConnection();
                System.out.println(connection.isOpen());
                Channel channel = connection.createChannel();
                channel.exchangeDeclare("minecraft", "fanout");
                String queueName = channel.queueDeclare().getQueue();
                channel.queueBind(queueName, "minecraft", "");
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, "UTF-8");
                        BungeeCore.instance.getLogger().info(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
                    }
                };
                channel.basicConsume(queueName, true, consumer);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
