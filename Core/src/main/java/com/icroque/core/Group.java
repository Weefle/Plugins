package com.icroque.core;

import com.icroque.core.database.Database;
import com.icroque.core.utils.PlayerData;
import com.icroque.core.utils.TextUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;

import java.util.*;

/**
 * @TODO fix bug save
 * Created by Rémi on 09/01/2016.
 */
public class Group {
    // Groups storage
    private static Group defaultGroup;
    private static Map<String, Group> groups = new HashMap<String, Group>();
    // Group variable
    private String name;
    private List<String> permissions;
    private String chatFormat = "{PLAYER}: {MESSAGE}";
    private String tabFormat = "{PLAYER}";

    public Group(String name, boolean isDefault) {
        this.name = name;
        this.permissions = new ArrayList<String>();
        if(isDefault) {
            setDefault();
        }
        groups.put(name, this);
    }

    public String getName() {
        return this.name;
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

    public void addPermission(String permission) {
        this.permissions.add(permission);
        this.recalculatePermissions();
    }

    public void removePermission(String permission) {
        this.permissions.remove(permission);
        this.recalculatePermissions();
    }

    public void setDefault() {
        for(PlayerData data : PlayerData.all()) {
            if(data.getGroup().isDefault()) {
                data.setGroup(this);
            }
        }
        defaultGroup = this;
    }

    public boolean isDefault() {
        return this.name.equalsIgnoreCase(defaultGroup.getName());
    }

    public String getChatFormat() {
        return this.chatFormat;
    }

    public void setChatFormat(String chatFormat) {
        this.chatFormat = chatFormat;
    }

    public String getTabFormat() {
        return this.tabFormat;
    }

    public void setTabFormat(String tabFormat) {
        this.tabFormat = tabFormat;
        for(PlayerData data : PlayerData.all()) {
            if(data.getGroup().getName().equalsIgnoreCase(this.name)) {
                Bukkit.getPlayer(data.getPlayerName()).setPlayerListName(TextUtils.translateColor(this.getTabFormat()).replace("{PLAYER}", data.getPlayerName()));
            }
        }
    }

    public void recalculatePermissions() {
        for(PlayerData data : PlayerData.all()) {
            if(data.getGroup().getName().equalsIgnoreCase(this.name)) {
                recalculatePermissions(data);
            }
        }
    }

    public void recalculatePermissions(PlayerData data) {
        data.getAttachment().getPermissions().clear();
        for(String permission : this.permissions) {
            if(permission.startsWith("-")) {
                data.getAttachment().unsetPermission(permission.replace("-", ""));
            }
            else {
                data.getAttachment().setPermission(permission, true);
            }
        }
        Bukkit.getPlayer(data.getPlayerName()).setPlayerListName(TextUtils.translateColor(this.getTabFormat()).replace("{PLAYER}", data.getPlayerName()));
    }

    public static Group getByName(String name) {
        return groups.get(name);
    }

    public static Collection<Group> all() {
        return groups.values();
    }

    public static Group getDefault() {
        return defaultGroup;
    }

    public static void load() {
        DBCursor cursor = Database.getDatabase().getCollection("groups").find();
        if(cursor.size() == 0) {
            Group joueur = new Group("joueur", true);
            joueur.addPermission("core.msg");
            joueur.addPermission("core.spawn");
            joueur.setChatFormat("&7Joueur {PLAYER}: {MESSAGE}");
            joueur.setTabFormat("&7Joueur {PLAYER}");
            Group admin = new Group("admin", false);
            admin.addPermission("core.*");
            admin.setChatFormat("&cAdministrateur {PLAYER}: &f{MESSAGE}");
            admin.setTabFormat("&cAdmin {PLAYER}");
        }
        try {
            while(cursor.hasNext()) {
                DBObject object = cursor.next();
                Group g = new Group(object.get("name").toString(), Boolean.parseBoolean(object.get("default").toString()));
                g.getPermissions().addAll((ArrayList<String>) object.get("permissions"));
                g.setChatFormat(object.get("chatFormat").toString());
                g.setTabFormat(object.get("tabFormat").toString());
            }
        }
        finally {
            cursor.close();
        }
    }

    public static void save() {
        DBCollection groupsCollection = Database.getDatabase().getCollection("groups");

        for(Group group : Group.all()) {
            DBObject save = new BasicDBObject("name", group.getName());
            DBObject groupDocument = groupsCollection.findOne(save);

            if(groupDocument != null) {
                save.putAll(groupDocument);
            }

            save.put("permissions", group.getPermissions());
            save.put("default", group.isDefault());
            save.put("chatFormat", group.getChatFormat());
            save.put("tabFormat", group.getTabFormat());

            if(groupDocument == null) {
                groupsCollection.insert(save);
            }
            else {
                groupsCollection.update(groupDocument, save);
            }
        }
    }
}
