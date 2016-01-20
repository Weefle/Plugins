package com.icroque.core.connectors;

import com.icroque.core.Core;
import com.mongodb.*;

/**
 * Created by Rémi on 02/01/2016.
 */
public class Database {
    private static MongoClient mongo;

    public static MongoClient getMongo() {
        if (mongo == null) {
            MongoCredential credential = MongoCredential.createCredential(
                    Core.instance.getConfig().getString("mongo.credential.user"),
                    Core.instance.getConfig().getString("mongo.credential.database"),
                    Core.instance.getConfig().getString("mongo.credential.password").toCharArray()
            );
            try {
                mongo = new MongoClient(new ServerAddress(
                        Core.instance.getConfig().getString("mongo.host"),
                        Core.instance.getConfig().getInt("mongo.port")
                ));
            }
            catch (Exception e) {

            }

        }
        return mongo;
    }

    public static DB getDatabase() {
        return getMongo().getDB("minecraft");
    }

    public static DBCollection getPlayerCollection() {
        return getDatabase().getCollection("players");
    }
}
