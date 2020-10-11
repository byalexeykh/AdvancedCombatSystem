package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static void createFile(String cfgPath){
        final File file = new File(cfgPath);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("[ACS] Error while trying to create config file: " + e);
        }
    }

    public static void initItemsConfig(Gson gson, String path){
        try{
            FileWriter writer = new FileWriter(path);
            List<ACSAttributesContainer> ForJsons = new ArrayList<>();

            ForJsons.add(new jsonACSAttributesContainer(ToolType.SWORD, 50, 7, 16, 5, 4, -0.03d, 0.4f));
            ForJsons.add(new jsonACSAttributesContainer(ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer(ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer(ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer(ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));

            /*ForJsons.add(new jsonACSAttributesContainer("wooden_sword", ItemTier.WOOD, ToolType.SWORD, 50, 7, 16, 5, 4, -0.03d, 0.4f));
            ForJsons.add(new jsonACSAttributesContainer("golden_sword", ItemTier.GOLD, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("stone_sword", ItemTier.STONE, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("iron_sword", ItemTier.IRON, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("diamond_sword", ItemTier.DIAMOND, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));

            ForJsons.add(new jsonACSAttributesContainer("wooden_axe", ItemTier.WOOD, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("golden_axe", ItemTier.GOLD, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("stone_axe", ItemTier.STONE, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("iron_axe", ItemTier.IRON, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("diamond_axe", ItemTier.DIAMOND, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));

            ForJsons.add(new jsonACSAttributesContainer("wooden_hoe", ItemTier.WOOD, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("golden_hoe", ItemTier.GOLD, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("stone_hoe", ItemTier.STONE, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("iron_hoe", ItemTier.IRON, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("diamond_hoe", ItemTier.DIAMOND, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));

            ForJsons.add(new jsonACSAttributesContainer("wooden_pickaxe", ItemTier.WOOD, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("golden_pickaxe", ItemTier.GOLD, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("stone_pickaxe", ItemTier.STONE, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("iron_pickaxe", ItemTier.IRON, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("diamond_pickaxe", ItemTier.DIAMOND, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));

            ForJsons.add(new jsonACSAttributesContainer("wooden_shovel", ItemTier.WOOD, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("golden_shovel", ItemTier.GOLD, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("stone_shovel", ItemTier.STONE, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("iron_shovel", ItemTier.IRON, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
            ForJsons.add(new jsonACSAttributesContainer("diamond_shovel", ItemTier.DIAMOND, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));*/

            gson.toJson(ForJsons, writer);
            writer.flush();
            writer.close();
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("Error while writing to json: " + e);
        }
    }

    public static void initDefaultsConfig(Gson gson, String path){
        DefaultsConfigObj defaultPickaxe = new DefaultsConfigObj(ToolType.PICKAXE, new ACSAttributesContainer(30, 5, 20, 25, 2, -0.025d, 0.2f));
        DefaultsConfigObj defaultShovel = new DefaultsConfigObj(ToolType.SHOVEL, new ACSAttributesContainer(40, 6, 20, 45, 3, -0.025d, 0.2f));
        DefaultsConfigObj defaultSword = new DefaultsConfigObj(ToolType.SWORD, new ACSAttributesContainer(50, 7, 16, 30, 4, -0.01d, 0.2f));
        DefaultsConfigObj defaultAxe = new DefaultsConfigObj(ToolType.AXE, new ACSAttributesContainer(40, 6, 30, 30, 2, -0.035d, 0.2f));
        DefaultsConfigObj defaultHoe = new DefaultsConfigObj(ToolType.HOE, new ACSAttributesContainer(70, 5, 20, 30, 2, -0.025d, 0.2f));
        DefaultsConfigObj defaultHand = new DefaultsConfigObj(ToolType.HAND, ACSAttributesContainer.getDefaultContainer());
        DefaultsConfigObj[] objs = {defaultPickaxe, defaultAxe, defaultShovel, defaultSword, defaultHoe, defaultHand};
        FileWriter writer;
        try{
            writer = new FileWriter(path);
            gson.toJson(objs, writer);
            writer.flush();
            writer.close();
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("[ACS] error while common config initialization: " + e);
            return;
        }
    }

    public static void initCommonConfig(Gson gson, String path){
        CommonConfigObj commonConfigObj = getDefaultCommonConfig();
        FileWriter writer;
        try{
            writer = new FileWriter(path);
            gson.toJson(commonConfigObj, writer);
            writer.flush();
            writer.close();
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("[ACS] error while common config initialization: " + e);
            return;
        }
    }

    public static DefaultsConfigObj[] readDefaultsConfig(Gson gson, String path){
        FileReader reader;
        try{
            reader = new FileReader(path);
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("[ACS] Error while reading common config: " + e);
            return null;
        }
        return gson.fromJson(reader, DefaultsConfigObj[].class);
    }

    public static CommonConfigObj readCommonConfig(Gson gson, String path){
        FileReader reader;
        try{
            reader = new FileReader(path);
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("[ACS] Error while reading common config: " + e);
            return null;
        }
        return gson.fromJson(reader, CommonConfigObj.class);
    }

    public static jsonACSAttributesContainer[] readItemsConfig(Gson gson, String path){
        FileReader reader;
        try{
            reader = new FileReader(path);
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("[ACS] Error while reading items config: " + e);
            return null;
        }
        return gson.fromJson(reader, jsonACSAttributesContainer[].class);
    }

    public static CommonConfigObj getDefaultCommonConfig(){
        return new CommonConfigObj(false, true);
    }
}
