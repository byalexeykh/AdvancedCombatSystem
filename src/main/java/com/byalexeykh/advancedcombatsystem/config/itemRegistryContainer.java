package com.byalexeykh.advancedcombatsystem.config;

import net.minecraft.item.Item;

public class itemRegistryContainer {
    public String modid;
    public String name;
    public Item itemToRegister;
    public itemRegistryContainer(String modid, String name, Item itemToRegister){
        this.modid = modid;
        this.name = name;
        this.itemToRegister = itemToRegister;
    }
}
