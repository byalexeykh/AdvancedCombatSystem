package com.byalexeykh.advancedcombatsystem.items;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.config.itemRegistryContainer;
import com.byalexeykh.advancedcombatsystem.config.jsonACSAttributesContainer;
import com.google.gson.Gson;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AdvancedItems {

    private static List<itemRegistryContainer> itemsToRegister;
    private static jsonACSAttributesContainer[] AttrContainers;

    public static void setItemsToRegister(jsonACSAttributesContainer... items){
        AttrContainers = items;
    }

    public static List<itemRegistryContainer> getItemsToRegister() {
        return itemsToRegister;
    }

    public void instantiateItems(){
        for(jsonACSAttributesContainer container : AttrContainers){
            switch (container.getType()){
                case AXE:
                    itemsToRegister.add(new itemRegistryContainer(container.getModid(), container.getName(), new AdvancedAxeItem(container.getTier(), 6, container, new Item.Properties().group(ItemGroup.TOOLS))));
                    break;
                case HOE:
                    itemsToRegister.add(new itemRegistryContainer(container.getModid(), container.getName(), new AdvancedHoeItem(container.getTier(), container, new Item.Properties().group(ItemGroup.TOOLS))));
                    break;
                case SWORD:
                    itemsToRegister.add(new itemRegistryContainer(container.getModid(), container.getName(), new AdvancedSwordItem(container.getTier(), 3, container, new Item.Properties().group(ItemGroup.COMBAT))));
                    break;
                case SHOVEL:
                    itemsToRegister.add(new itemRegistryContainer(container.getModid(), container.getName(), new AdvancedShovelItem(container.getTier(), 1.5f, container, new Item.Properties().group(ItemGroup.TOOLS))));
                    break;
                case PICKAXE:
                    itemsToRegister.add(new itemRegistryContainer(container.getModid(), container.getName(), new AdvancedSwordItem(container.getTier(), 1, container, new Item.Properties().group(ItemGroup.TOOLS))));
                    break;
                default:
                    AdvancedCombatSystem.LOGGER.error("[ACS] Invalid tool type " + container.getType() +", can't register item");
                    break;
            }
        }
    }

    public static void writeDefaultToJson(Gson gson, String path){
        try{
            FileWriter writer = new FileWriter(path);
            List<jsonACSAttributesContainer> ForJsons = new ArrayList<>();
                jsonACSAttributesContainer toAdd;
                    ForJsons.add(new jsonACSAttributesContainer("minecraft", "wooden_sword", ItemTier.WOOD, ToolType.SWORD, 50, 7, 16, 5, 4, -0.01d, 0.2f));
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
            gson.toJson(ForJsons.toArray(), writer);
            writer.flush();
            writer.close();
        }catch (Exception e){
            AdvancedCombatSystem.LOGGER.error("Error while writing to json: " + e);
        }
    }
    // SWORDS ==========================================================================================================
    public static final Item wooden_sword = new AdvancedSwordItem(ItemTier.WOOD, 3, 50, 7, 16, 5, 4, -0.01d, 0.2f, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));
    public static final Item golden_sword = new AdvancedSwordItem(ItemTier.GOLD, 3, 50, 7, 16, 5, 4, -0.01d, 0.2f, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));
    public static final Item stone_sword = new AdvancedSwordItem(ItemTier.STONE, 3, 50, 7, 16, 5, 4, -0.01d, 0.2f, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));
    public static final Item iron_sword = new AdvancedSwordItem(ItemTier.IRON, 3, 50, 7, 16, 5, 4, -0.01d, 0.2f, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));
    public static final Item diamond_sword = new AdvancedSwordItem(ItemTier.DIAMOND, 3, 50, 7, 16, 5, 4, -0.01d, 0.2f, new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));

    // AXEs ============================================================================================================
    public static final Item wooden_axe = new AdvancedAxeItem(ItemTier.WOOD, 6, 40, 6, 30, 10, 2, -0.035d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item golden_axe = new AdvancedAxeItem(ItemTier.GOLD, 6, 40, 6, 30, 10, 2, -0.035d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item stone_axe = new AdvancedAxeItem(ItemTier.STONE, 6, 40, 6, 30, 10, 2, -0.035d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item iron_axe = new AdvancedAxeItem(ItemTier.IRON, 6, 40, 6, 30, 10, 2, -0.035d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item diamond_axe = new AdvancedAxeItem(ItemTier.DIAMOND, 6, 40, 6, 30, 10, 2, -0.035d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));

    // HOEs ============================================================================================================
    public static final Item wooden_hoe = new AdvancedHoeItem(ItemTier.WOOD, 70, 5, 20, 8, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item golden_hoe = new AdvancedHoeItem(ItemTier.GOLD, 70, 5, 20, 8, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item stone_hoe = new AdvancedHoeItem(ItemTier.STONE, 70, 5, 20, 8, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item iron_hoe = new AdvancedHoeItem(ItemTier.IRON, 70, 5, 20, 8, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item diamond_hoe = new AdvancedHoeItem(ItemTier.DIAMOND, 70, 5, 20, 8, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));

    // PICKAXEs ========================================================================================================
    public static final Item wooden_pickaxe = new AdvancedPickaxeItem(ItemTier.WOOD, 1, 30, 5, 20, 7, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item golden_pickaxe = new AdvancedPickaxeItem(ItemTier.GOLD, 1, 30, 5, 20, 7, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item stone_pickaxe = new AdvancedPickaxeItem(ItemTier.STONE, 1, 30, 5, 20, 7, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item iron_pickaxe = new AdvancedPickaxeItem(ItemTier.IRON, 1, 30, 5, 20, 7, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item diamond_pickaxe = new AdvancedPickaxeItem(ItemTier.DIAMOND, 1, 30, 5, 20, 7, 2, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));

    // SHOVELs =========================================================================================================
    public static final Item wooden_shovel = new AdvancedShovelItem(ItemTier.WOOD, 1.5f, 40, 6, 20, 6, 3, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item golden_shovel = new AdvancedShovelItem(ItemTier.GOLD, 1.5f, 40, 6, 20, 6, 3, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item stone_shovel = new AdvancedShovelItem(ItemTier.STONE, 1.5f, 40, 6, 20, 6, 3, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item iron_shovel = new AdvancedShovelItem(ItemTier.IRON, 1.5f, 40, 6, 20, 6, 3, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
    public static final Item diamond_shovel = new AdvancedShovelItem(ItemTier.DIAMOND, 1.5f, 40, 6, 20, 6, 3, -0.025d, 0.2f, new Item.Properties().group(ItemGroup.TOOLS));
}
