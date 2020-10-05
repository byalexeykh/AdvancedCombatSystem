package com.byalexeykh.advancedcombatsystem.items;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.config.jsonACSAttributesContainer;
import com.google.gson.Gson;
import net.minecraft.item.*;

import javax.smartcardio.ATR;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AdvancedItems {

    private static List<Item> itemsToRegister = new ArrayList<>();

    public static Item instantiateItem(jsonACSAttributesContainer AttrContainer){
        Item itemToRegister;
        switch (AttrContainer.type) {
            case AXE:
                itemToRegister =  new AdvancedAxeItem(AttrContainer.tier, 6, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS))/*.setRegistryName(AttrContainer.modid, AttrContainer.name)*/;
                break;
                case HOE:
                    itemToRegister = new AdvancedHoeItem(AttrContainer.tier, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS))/*.setRegistryName(AttrContainer.modid, AttrContainer.name)*/;
                    break;
                case SWORD:
                    itemToRegister = new AdvancedSwordItem(AttrContainer.tier, 3, AttrContainer, new Item.Properties().group(ItemGroup.COMBAT))/*.setRegistryName(AttrContainer.modid, AttrContainer.name)*/;
                    break;
                case SHOVEL:
                    itemToRegister = new AdvancedShovelItem(AttrContainer.tier, 1.5f, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS))/*.setRegistryName(AttrContainer.modid, AttrContainer.name)*/;
                    break;
                case PICKAXE:
                    itemToRegister = new AdvancedSwordItem(AttrContainer.tier, 1, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS))/*.setRegistryName(AttrContainer.modid, AttrContainer.name)*/;
                    break;
                default:
                    itemToRegister = null;
                    AdvancedCombatSystem.LOGGER.error("[ACS] Invalid tool type " + AttrContainer.tier + ", can't register item");
                    break;
            }
            AdvancedCombatSystem.LOGGER.warn("[ACS] Initialized item from config: " + itemToRegister);
        return itemToRegister;
    }

    public static void writeDefaultToJson(Gson gson, String path){
        try{
            FileWriter writer = new FileWriter(path);
            List<ACSAttributesContainer> ForJsons = new ArrayList<>();
            ACSAttributesContainer toAdd;
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "wooden_sword", ItemTier.WOOD, ToolType.SWORD, 50, 7, 16, 5, 4, -0.03d, 0.4f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "golden_sword", ItemTier.GOLD, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "stone_sword", ItemTier.STONE, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "iron_sword", ItemTier.IRON, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "diamond_sword", ItemTier.DIAMOND, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));

                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "wooden_axe", ItemTier.WOOD, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "golden_axe", ItemTier.GOLD, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "stone_axe", ItemTier.STONE, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "iron_axe", ItemTier.IRON, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "diamond_axe", ItemTier.DIAMOND, ToolType.AXE, 40, 6, 30, 10, 2, -0.035d, 0.2f));

                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "wooden_hoe", ItemTier.WOOD, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "golden_hoe", ItemTier.GOLD, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "stone_hoe", ItemTier.STONE, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "iron_hoe", ItemTier.IRON, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "diamond_hoe", ItemTier.DIAMOND, ToolType.HOE, 70, 5, 20, 8, 2, -0.025d, 0.2f));

                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "wooden_pickaxe", ItemTier.WOOD, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "golden_pickaxe", ItemTier.GOLD, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "stone_pickaxe", ItemTier.STONE, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "iron_pickaxe", ItemTier.IRON, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "diamond_pickaxe", ItemTier.DIAMOND, ToolType.PICKAXE,30, 5, 20, 7, 2, -0.025d, 0.2f));

                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "wooden_shovel", ItemTier.WOOD, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "golden_shovel", ItemTier.GOLD, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "stone_shovel", ItemTier.STONE, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "iron_shovel", ItemTier.IRON, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "diamond_shovel", ItemTier.DIAMOND, ToolType.SHOVEL, 40, 6, 20, 6, 3, -0.025d, 0.2f));
            gson.toJson(ForJsons, writer);
            writer.flush();
            writer.close();
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("Error while writing to json: " + e);
        }
    }
}
