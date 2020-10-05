package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

    public static void initCommonConfig(Gson gson, String path){
        CommonConfigObj commonConfigObj = new CommonConfigObj(false);
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
        return new CommonConfigObj(false);
    }
}
