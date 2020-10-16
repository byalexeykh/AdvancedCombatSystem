package com.byalexeykh.advancedcombatsystem.items;

import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;

public class AdvancedItems {

    /*public static Item instantiateItem(ItemRegisterContainer AttrContainer){
        Item itemToRegister;
        switch (AttrContainer.type) {
            case AXE:
                itemToRegister =  new AdvancedAxeItem(AttrContainer.tier, AttrContainer.damage, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS));
                break;
                case HOE:
                    itemToRegister = new AdvancedHoeItem(AttrContainer.tier, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS));
                    break;
                case SWORD:
                    itemToRegister = new AdvancedSwordItem(AttrContainer.tier, AttrContainer.damage, AttrContainer, new Item.Properties().group(ItemGroup.COMBAT));
                    break;
                case SHOVEL:
                    itemToRegister = new AdvancedShovelItem(AttrContainer.tier, AttrContainer.damage, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS));
                    break;
                case PICKAXE:
                    itemToRegister = new AdvancedSwordItem(AttrContainer.tier, AttrContainer.damage, AttrContainer, new Item.Properties().group(ItemGroup.TOOLS));
                    break;
                default:
                    itemToRegister = null;
                    AdvancedCombatSystem.LOGGER.error("[ACS] Invalid tool type " + AttrContainer.type + ", can't register item");
                    break;
            }
            AdvancedCombatSystem.LOGGER.debug("[ACS] Initialized item from config: " + AttrContainer.name);
        return itemToRegister;
    }*/
    public static void registerAll(DeferredRegister<Item> DeferredRegister){
        /*DeferredRegister.register("wooden_sword", () -> wooden_sword);
        DeferredRegister.register("golden_sword", () -> golden_sword);
        DeferredRegister.register("stone_sword", () -> stone_sword);
        DeferredRegister.register("iron_sword", () -> iron_sword);
        DeferredRegister.register("diamond_sword", () -> diamond_sword);

        DeferredRegister.register("wooden_shovel", () -> wooden_shovel);
        DeferredRegister.register("golden_shovel", () -> golden_shovel);
        DeferredRegister.register("stone_shovel", () -> stone_shovel);
        DeferredRegister.register("iron_shovel", () -> iron_shovel);
        DeferredRegister.register("diamond_shovel", () -> diamond_shovel);

        DeferredRegister.register("wooden_pickaxe", () -> wooden_pickaxe);
        DeferredRegister.register("golden_pickaxe", () -> golden_pickaxe);
        DeferredRegister.register("stone_pickaxe", () -> stone_pickaxe);
        DeferredRegister.register("iron_pickaxe", () -> iron_pickaxe);
        DeferredRegister.register("diamond_pickaxe", () -> diamond_pickaxe);

        DeferredRegister.register("wooden_hoe", () -> wooden_hoe);
        DeferredRegister.register("golden_hoe", () -> golden_hoe);
        DeferredRegister.register("stone_hoe", () -> stone_hoe);
        DeferredRegister.register("iron_hoe", () -> iron_hoe);
        DeferredRegister.register("diamond_hoe", () -> diamond_hoe);

        DeferredRegister.register("wooden_axe", () -> wooden_axe);
        DeferredRegister.register("golden_axe", () -> golden_axe);
        DeferredRegister.register("stone_axe", () -> stone_axe);
        DeferredRegister.register("iron_axe", () -> iron_axe);
        DeferredRegister.register("diamond_axe", () -> diamond_axe);*/
    }

    /*// SWORDS ==========================================================================================================
    public static final Item wooden_sword = new AdvancedSwordItem(
            ItemTier.WOOD,
            3,
            ACSAttributes.getSwordsAttributes(),
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );

    public static final Item golden_sword = new AdvancedSwordItem(
            ItemTier.GOLD,
            3,
            ACSAttributes.getSwordsAttributes(),
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );
    public static final Item stone_sword = new AdvancedSwordItem(
            ItemTier.STONE,
            3,
            ACSAttributes.getSwordsAttributes(),
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );

    public static final Item iron_sword = new AdvancedSwordItem(
            ItemTier.IRON,
            3,
            ACSAttributes.getSwordsAttributes(),
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );
    public static final Item diamond_sword = new AdvancedSwordItem(
            ItemTier.DIAMOND,
            3,
            ACSAttributes.getSwordsAttributes(),
            new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)
    );
    // AXEs ============================================================================================================
    public static final Item wooden_axe = new AdvancedAxeItem(
            ItemTier.WOOD,
            6,
            ACSAttributes.getAxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item golden_axe = new AdvancedAxeItem(
            ItemTier.GOLD,
            6,
            ACSAttributes.getAxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item stone_axe = new AdvancedAxeItem(
            ItemTier.STONE,
            6,
            ACSAttributes.getAxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item iron_axe = new AdvancedAxeItem(
            ItemTier.IRON,
            6,
            ACSAttributes.getAxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item diamond_axe = new AdvancedAxeItem(
            ItemTier.DIAMOND,
            6,
            ACSAttributes.getAxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );
    // HOEs ============================================================================================================
    public static final Item wooden_hoe = new AdvancedHoeItem(
            ItemTier.WOOD,
            ACSAttributes.getHoesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item golden_hoe = new AdvancedHoeItem(
            ItemTier.GOLD,
            ACSAttributes.getHoesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item stone_hoe = new AdvancedHoeItem(
            ItemTier.STONE,
            ACSAttributes.getHoesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item iron_hoe = new AdvancedHoeItem(
            ItemTier.IRON,
            ACSAttributes.getHoesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item diamond_hoe = new AdvancedHoeItem(
            ItemTier.DIAMOND,
            ACSAttributes.getHoesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    // PICKAXEs ========================================================================================================
    public static final Item wooden_pickaxe = new AdvancedPickaxeItem(
            ItemTier.WOOD,
            1,
            ACSAttributes.getPickaxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item golden_pickaxe = new AdvancedPickaxeItem(
            ItemTier.GOLD,
            1,
            ACSAttributes.getPickaxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item stone_pickaxe = new AdvancedPickaxeItem(
            ItemTier.STONE,
            1,
            ACSAttributes.getPickaxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item iron_pickaxe = new AdvancedPickaxeItem(
            ItemTier.IRON,
            1,
            ACSAttributes.getPickaxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item diamond_pickaxe = new AdvancedPickaxeItem(
            ItemTier.DIAMOND,
            1,
            ACSAttributes.getPickaxesAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );
    // SHOVELs =========================================================================================================
    public static final Item wooden_shovel = new AdvancedShovelItem(
            ItemTier.WOOD,
            1.5f,
            ACSAttributes.getShovelsAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item golden_shovel = new AdvancedShovelItem(
            ItemTier.GOLD,
            1.5f,
            ACSAttributes.getShovelsAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item stone_shovel = new AdvancedShovelItem(
            ItemTier.STONE,
            1.5f,
            ACSAttributes.getShovelsAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item iron_shovel = new AdvancedShovelItem(
            ItemTier.IRON,
            1.5f,
            ACSAttributes.getShovelsAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );

    public static final Item diamond_shovel = new AdvancedShovelItem(
            ItemTier.DIAMOND,
            1.5f,
            ACSAttributes.getShovelsAttributes(),
            new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1)
    );*/
}
