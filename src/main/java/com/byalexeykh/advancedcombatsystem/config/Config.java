package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import com.google.gson.Gson;
import net.minecraft.item.ItemTier;

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

    public static void initAttributesByIdConfig(Gson gson, String path){
        try{
            FileWriter writer = new FileWriter(path);
            List<ACSAttributesContainer> ForJsons = new ArrayList<>();

            ForJsons.add(
                    new ItemRegisterContainer(
                    /*ToolType.SWORD,
                    ItemTier.WOOD,
                    1,*/
                    "example_modid",
                    "example_name",
                    new ACSAttributesContainer(
                            50,
                            7,
                            16,
                            5,
                            4,
                            -0.03d,
                            0.4f)
                    )
            );

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

    public static ItemRegisterContainer[] readAttributesByIdConfig(Gson gson, String path){
        FileReader reader;
        try{
            reader = new FileReader(path);
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("[ACS] Error while reading AttributesById config: " + e);
            return null;
        }
        return gson.fromJson(reader, ItemRegisterContainer[].class);
    }

    public static CommonConfigObj getDefaultCommonConfig(){
        return new CommonConfigObj(false, true);
    }
}
