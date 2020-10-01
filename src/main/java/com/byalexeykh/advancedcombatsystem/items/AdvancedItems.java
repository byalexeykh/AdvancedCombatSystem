package com.byalexeykh.advancedcombatsystem.items;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.entities.AdvancedEntities;
import com.byalexeykh.advancedcombatsystem.entities.SkeletonWarriorEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SpawnEggItem;

public class AdvancedItems {
    // SWORDS ==========================================================================================================
    public static final Item wooden_sword = new AdvancedSwordItem(
            ItemTier.WOOD,
            3,
            50,
            7,
            16,
            5,
            4,
            -0.01d,
            0.2f,
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );

    public static final Item golden_sword = new AdvancedSwordItem(
            ItemTier.GOLD,
            3,
            50,
            7,
            16,
            5,
            4,
            -0.01d,
            0.2f,
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );

    public static final Item stone_sword = new AdvancedSwordItem(
            ItemTier.STONE,
            3,
            50,
            7,
            16,
            5,
            4,
            -0.01d,
            0.2f,
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );

    public static final Item iron_sword = new AdvancedSwordItem(
            ItemTier.IRON,
            3,
            50,
            7,
            16,
            5,
            4,
            -0.01d,
            0.2f,
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );

    public static final Item diamond_sword = new AdvancedSwordItem(
            ItemTier.DIAMOND,
            3,
            50,
            7,
            16,
            5,
            4,
            -0.01d,
            0.2f,
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );

    // AXEs ============================================================================================================
    public static final Item wooden_axe = new AdvancedAxeItem(
            ItemTier.WOOD,
            6,
            40,
            6,
            30,
            10,
            2,
            -0.035d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );


    public static final Item golden_axe = new AdvancedAxeItem(
            ItemTier.GOLD,
            6,
            40,
            6,
            30,
            10,
            2,
            -0.035d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item stone_axe = new AdvancedAxeItem(
            ItemTier.STONE,
            6,
            40,
            6,
            30,
            10,
            2,
            -0.035d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item iron_axe = new AdvancedAxeItem(
            ItemTier.IRON,
            6,
            40,
            6,
            30,
            10,
            2,
            -0.035d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item diamond_axe = new AdvancedAxeItem(
            ItemTier.DIAMOND,
            6,
            40,
            6,
            30,
            10,
            2,
            -0.035d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    // HOEs ============================================================================================================
    public static final Item wooden_hoe = new AdvancedHoeItem(
            ItemTier.WOOD,
            70,
            5,
            20,
            8,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item golden_hoe = new AdvancedHoeItem(
            ItemTier.GOLD,
            70,
            5,
            20,
            8,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item stone_hoe = new AdvancedHoeItem(
            ItemTier.STONE,
            70,
            5,
            20,
            8,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item iron_hoe = new AdvancedHoeItem(
            ItemTier.IRON,
            70,
            5,
            20,
            8,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item diamond_hoe = new AdvancedHoeItem(
            ItemTier.DIAMOND,
            70,
            5,
            20,
            8,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    // PICKAXEs ========================================================================================================
    public static final Item wooden_pickaxe = new AdvancedPickaxeItem(
            ItemTier.WOOD,
            1,
            30,
            5,
            20,
            7,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item golden_pickaxe = new AdvancedPickaxeItem(
            ItemTier.GOLD,
            1,
            30,
            5,
            20,
            7,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item stone_pickaxe = new AdvancedPickaxeItem(
            ItemTier.STONE,
            1,
            30,
            5,
            20,
            7,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item iron_pickaxe = new AdvancedPickaxeItem(
            ItemTier.IRON,
            1,
            30,
            5,
            20,
            7,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item diamond_pickaxe = new AdvancedPickaxeItem(
            ItemTier.DIAMOND,
            1,
            30,
            5,
            20,
            7,
            2,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    // SHOVELs =========================================================================================================
    public static final Item wooden_shovel = new AdvancedShovelItem(
            ItemTier.WOOD,
            1.5f,
            40,
            6,
            20,
            6,
            3,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item golden_shovel = new AdvancedShovelItem(
            ItemTier.GOLD,
            1.5f,
            40,
            6,
            20,
            6,
            3,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item stone_shovel = new AdvancedShovelItem(
            ItemTier.STONE,
            1.5f,
            40,
            6,
            20,
            6,
            3,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item iron_shovel = new AdvancedShovelItem(
            ItemTier.IRON,
            1.5f,
            40,
            6,
            20,
            6,
            3,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    public static final Item diamond_shovel = new AdvancedShovelItem(
            ItemTier.DIAMOND,
            1.5f,
            40,
            6,
            20,
            6,
            3,
            -0.025d,
            0.2f,
            new Item.Properties().group(ItemGroup.TOOLS)
    );

    // SPAWN EGGS ======================================================================================================
    public static final Item skeleton_warrior_spawnegg = new SpawnEggItem(
            AdvancedEntities.skeleton_warrior,
            123,
            157,
            new Item.Properties().group(ItemGroup.MISC)
    );
}
