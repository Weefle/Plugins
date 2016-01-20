package com.icroque.core.utils;

import com.icroque.core.Core;
import com.icroque.core.Group;
import com.icroque.core.connectors.Database;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rémi on 09/01/2016.
 */
public class PlayerData {
    private static Map<String, PlayerData> playerData = new HashMap<String, PlayerData>();
    @Getter
    private String playerName;
    @Getter
    private Group group;
    @Getter
    private PermissionAttachment attachment;
    @Getter @Setter
    private long mute = 0;
    @Getter @Setter
    private long ban = 0;
    @Getter @Setter
    private String replyTo = null;
    @Getter @Setter
    private Scoreboard scoreboard = null;
    @Getter @Setter
    private Location lastTeleport = null;

    public PlayerData(Player player) {
        this.playerName = player.getName();
        this.attachment = player.addAttachment(Core.instance);
        this.group = Group.getDefault();

        playerData.put(this.playerName, this);
        load();
    }

    public void load() {
        DBObject playerDocument = Database.getPlayerCollection().findOne(new BasicDBObject("player", this.playerName));
        if(playerDocument == null) {
            save();
            return;
        }
        // @TODO REFACTORING
        if(playerDocument.get("group") == null || playerDocument.get("group").toString().equalsIgnoreCase("null") || Group.getByName(playerDocument.get("group").toString()) == null) {
            group = Group.getDefault();
        }
        else {
            group = Group.getByName(playerDocument.get("group").toString());
        }
        mute = Long.parseLong(playerDocument.get("mute").toString());
        ban = Long.parseLong(playerDocument.get("ban").toString());
        group.recalculatePermissions(this);
    }

    public void save() {
        DBObject save = new BasicDBObject("player", this.playerName);
        DBObject playerDocument = Database.getPlayerCollection().findOne(save);

        if(playerDocument != null) {
            save.putAll(playerDocument);
        }
        save.put("group", (this.group.isDefault() ? null : this.group.getName()));
        save.put("mute", String.valueOf(this.mute));
        save.put("ban", String.valueOf(this.ban));
        if(playerDocument == null) {
            Database.getPlayerCollection().save(save);
        }
        else {
            Database.getPlayerCollection().update(playerDocument, save);
        }
    }

    public void unload() {
        if(playerData.containsKey(this.playerName)) {
            playerData.remove(this.playerName);
        }
    }

    public boolean isMuted() {
        return (this.mute > System.currentTimeMillis() || this.mute == -1);
    }

    public boolean isBanned() {
        return (this.ban > System.currentTimeMillis() || this.ban == -1);
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
        group.recalculatePermissions(this);
    }

    /* Return user data */
    public static PlayerData findByName(String name) {
        return playerData.get(name);
    }

    /* Return all users data*/
    public static Collection<PlayerData> all() {
        return playerData.values();
    }
}
