package com.icroque.bungeecore.utils;

import com.icroque.bungeecore.BungeeCore;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by Rémi on 16/01/2016.
 */
public class RabbitMQ {
    private static RabbitMQ instance;
    private Connection connection;

    public RabbitMQ() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.99.100");
        factory.setPort(32773);
        factory.setUsername("root");
        factory.setPassword("root");

        try {
            connection = factory.newConnection();
            instance = this;
        }
        catch (Exception e) {
            BungeeCore.instance.getLogger().severe("Impossible de se connecté a RabbitMQ.");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Channel getTopic(String name) {
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(name, "fanout");
            return channel;
        }
        catch (Exception e) {
            BungeeCore.instance.getLogger().severe("Impossible de se connecté au channel "+ name +".");
        }
        return null;
    }

    public Channel createTopic(String name) {
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(name, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, name, "");
            return channel;
        }
        catch (Exception e) {
            BungeeCore.instance.getLogger().severe("Impossible de créer le channel "+ name +".");
        }
        return null;
    }

    public void publish(String topic, String route, String message) {
        Channel channel = getTopic(topic);
        if(channel != null) {
            try {
                channel.basicPublish(topic, route, null, message.getBytes());
                channel.close();
            }
            catch (Exception e) {
                BungeeCore.instance.getLogger().severe("Impossible d'envoyé un message dans le channel "+ topic +".");
            }
        }
    }

    public void subscribe(ISubscriber subscriber, String topic) {
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(topic, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, topic, "");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    subscriber.getMessage(envelope.getRoutingKey(), new String(body, "UTF-8"));
                }
            };
            channel.basicConsume(queueName, true, consumer);
        }
        catch (Exception e) {
            BungeeCore.instance.getLogger().severe("Erreur RabbitMQ: "+ e.getMessage() +".");
        }
    }

    public void closeConnection() {
        try {
            if(connection.isOpen()) {
                connection.close();
            }
        }
        catch (Exception e) {
            BungeeCore.instance.getLogger().severe("Impossible de fermé la connexion à RabbitMQ.");
        }
    }

    public static RabbitMQ getInstance() {
        return instance == null ? new RabbitMQ() : instance;
    }

    public interface ISubscriber {
        void getMessage(String route, String message);
    }
}
