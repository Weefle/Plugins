package com.icroque.core.gui;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Created by Rémi on 02/01/2016.
 */
public class Item {
    private String name;
    private Material material;
    private int amount;
    private boolean hideEnchantement;
    private byte data;
    private List<String> lore = new ArrayList<String>();
    private Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();

    /**
     * @param material Material
     */
    public Item(Material material) {
        this.material = material;
        this.amount = 1;
        this.data = (byte) 0;
    }

    /**
     * @param material Material
     * @param amount int
     */
    public Item(Material material, int amount) {
        this.material = material;
        this.amount = amount;
        this.data = (byte) 0;
    }

    /**
     * Convert itemstack to item.
     * @param item ItemStack
     */
    public Item(ItemStack item) {
        this.material = item.getType();
        this.amount = item.getAmount();
        if(item.getDurability() != 0) {
            this.data = (byte) item.getDurability();
        }
        if(item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();
            this.lore = meta.getLore();
            this.name = meta.getDisplayName();
            this.enchantments = meta.getEnchants();
            if(meta.getItemFlags() != null) {
                if(meta.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
                    this.hideEnchantement = true;
                }
            }
        }
    }

    /**
     * @param name String
     * @return Item
     */
    public Item withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param amount int
     * @return Item
     */
    public Item withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @param hideEnchantement boolean
     * @return Item
     */
    public Item hideEnchant(boolean hideEnchantement) {
        this.hideEnchantement = hideEnchantement;
        return this;
    }

    /**
     * @param data byte
     * @return Item
     */
    public Item withData(byte data) {
        this.data = data;
        return this;
    }

    /**
     * @param lore String[]
     * @return Item
     */
    public Item withLore(String[] lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    /**
     * @param enchant Enchantment
     * @param level int
     * @return Item
     */
    public Item withEnchant(Enchantment enchant, int level) {
        this.enchantments.put(enchant, level);
        return this;
    }

    /**
     * Return the item.
     * @return ItemStack
     */
    public ItemStack get() {
        ItemStack item = new ItemStack(this.material, this.amount);
        item.setDurability((short) this.data);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(this.lore);
        if(this.name != null) {
            meta.setDisplayName(this.name);
        }
        if(this.enchantments.size() > 0)
            for(Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet())
                meta.addEnchant(enchant.getKey(), enchant.getValue(), true);
        if(this.hideEnchantement)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        return item;
    }
}
