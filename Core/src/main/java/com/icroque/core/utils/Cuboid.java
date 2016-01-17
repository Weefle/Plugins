package com.icroque.core.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * Created by Rémi on 17/01/2016.
 */
public class Cuboid {
    @Getter
    private Location loc1;
    @Getter
    private Location loc2;
    @Getter
    private int maxX;
    @Getter
    private int maxY;
    @Getter
    private int maxZ;
    @Getter
    private int minX;
    @Getter
    private int minY;
    @Getter
    private int minZ;

    public Cuboid(Location loc1, Location loc2) {
        this.loc1 = loc1;
        this.loc2 = loc2;

        /* Location X */
        if(loc1.getBlockX() > loc2.getBlockX()) {
            maxX = loc1.getBlockX();
            minX = loc2.getBlockX();
        }
        else {
            maxX = loc2.getBlockX();
            minX = loc1.getBlockX();
        }
        /* Location Y */
        if(loc1.getBlockY() > loc2.getBlockY()) {
            maxY = loc1.getBlockY();
            minY = loc2.getBlockY();
        }
        else {
            maxY = loc2.getBlockY();
            minY = loc1.getBlockY();
        }
        /* Location Z */
        if(loc1.getBlockZ() > loc2.getBlockZ()) {
            maxZ = loc1.getBlockZ();
            minZ = loc2.getBlockZ();
        }
        else {
            maxZ = loc2.getBlockZ();
            minZ = loc1.getBlockZ();
        }
    }

    public boolean isUnder(Location location) {
        Block block = location.getBlock();
        if((block.getX() >= minX) && (block.getX() <= maxX) && (block.getY() >= minY) && (block.getY() <= maxY) && (block.getZ() >= minZ) && (block.getZ() <= maxZ)) {
            return true;
        }
        return false;
    }
}
