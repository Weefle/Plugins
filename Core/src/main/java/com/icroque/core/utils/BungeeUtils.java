package com.icroque.core.utils;

import com.google.gson.Gson;
import com.icroque.core.connectors.RabbitMQ;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

/**
 * Created by Rémi on 09/01/2016.
 */
public class BungeeUtils {
    private static BungeeUtils instance = null;
    @Getter
    private List<String> players;

    @SuppressWarnings("unchecked")
	public BungeeUtils() {
        instance = this;
        RabbitMQ.getInstance().subscribe((String route, String message) -> {
            if(route.equalsIgnoreCase("getplayers")) {
                players = new Gson().fromJson(message, List.class);
            }
        }, "minecraft");
    }

    public boolean isOnBungee(String playerName) {
        return players.contains(playerName);
    }

    public ServerStatus pingServer(String adress, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(adress, port), 1000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out.write(0xFE);

            StringBuilder str = new StringBuilder();
            int b;
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }
            String[] data = str.toString().split("§");
            socket.close();

            return new ServerStatus(adress, port, data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
        }
        catch (Exception e) {}
        return null;
    }

    public static BungeeUtils getInstance() {
        if(instance == null) {
            instance = new BungeeUtils();
        }
        return instance;
    }

    public static class ServerStatus {
        private String adress;
        private int port;
        private String motd;
        private int online;
        private int maxPlayer;

        public ServerStatus(String adress, int port, String motd, int online, int maxPlayer) {
            this.adress = adress;
            this.port = port;
            this.motd = motd;
            this.online = online;
            this.maxPlayer = maxPlayer;
        }

        public String getAdress() {
            return this.adress;
        }

        public int getPort() {
            return this.port;
        }

        public String getMotd() {
            return this.motd;
        }

        public int getOnlinePlayer() {
            return this.online;
        }

        public int getMaxPlayer() {
            return this.maxPlayer;
        }
    }
}
