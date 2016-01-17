package com.icroque.core.utils;

import com.icroque.core.Core;
import com.icroque.core.Group;
import com.icroque.core.database.Database;
import com.icroque.core.database.IDocument;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rémi on 09/01/2016.
 */
public class PlayerData implements IDocument {
    private static Map<String, PlayerData> playerData = new HashMap<String, PlayerData>();
    // Core
    private String player;
    private Group group = Group.getDefault();
    private PermissionAttachment attachment;
    // Others
    private long mute = 0;
    private long ban = 0;
    public String replyTo = null;
    public Scoreboard scoreboard = null;
    public Location lastTeleport = null;

    public PlayerData(Player player) {
        this.player = player.getName();
        this.attachment = player.addAttachment(Core.instance);

        playerData.put(this.player, this);
        load();
    }

    public void load() {
        DBObject playerDocument = Database.getPlayerCollection().findOne(new BasicDBObject("player", this.player));
        if(playerDocument == null) {
            save();
            return;
        }
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
        DBObject save = new BasicDBObject("player", this.player);
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
        if(playerData.containsKey(this.player)) {
            playerData.remove(this.player);
        }
    }

    public PermissionAttachment getAttachment() {
        return this.attachment;
    }

    public String getPlayerName() {
        return this.player;
    }

    public boolean isMuted() {
        return (this.mute > System.currentTimeMillis() || this.mute == -1);
    }

    public boolean isBanned() {
        return (this.ban > System.currentTimeMillis() || this.ban == -1);
    }

    public long getBanTime() {
        return this.ban;
    }

    public void setMute(long time) {
        this.mute = time;
    }

    public long getMuteTime() {
        return this.mute;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
        group.recalculatePermissions(this);
    }

    public static PlayerData findByName(String name) {
        return playerData.get(name);
    }

    public static Collection<PlayerData> all() {
        return playerData.values();
    }
}
