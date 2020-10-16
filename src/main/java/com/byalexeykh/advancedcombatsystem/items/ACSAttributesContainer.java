package com.byalexeykh.advancedcombatsystem.items;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.config.DefaultsConfigObj;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;

import java.util.HashMap;

public class ACSAttributesContainer {
    public final float ANGLE;
    public final float RANGE;
    public final float NEEDED_BACKSWING_TICKS;
    public final float MIN_BACKSWING_TICKS;
    public final int MAX_COMBO_NUM;
    public final double SPEED_REDUCTION_MODIFIER;
    public final float COMBO_CHARGING_SPEED_BOUNS;
    private static HashMap<ToolType, ACSAttributesContainer> defaultContainers = new HashMap<ToolType, ACSAttributesContainer>();
    public ACSAttributesContainer(float angle, float range, float neededBackswingTicks, float minBackswingTicks, int maxComboNum, double speedReduceModifier, float comboChargingSpeedBonus){
        this.ANGLE = angle;
        this.RANGE = range;
        this.NEEDED_BACKSWING_TICKS = neededBackswingTicks;
        this.MIN_BACKSWING_TICKS = minBackswingTicks;
        this.MAX_COMBO_NUM = maxComboNum;
        this.SPEED_REDUCTION_MODIFIER = speedReduceModifier;
        this.COMBO_CHARGING_SPEED_BOUNS = comboChargingSpeedBonus;
    }

    /**
     * returns default ACS attributes container
     * */
    public static ACSAttributesContainer getDefaultContainer(){
        return new ACSAttributesContainer(30, 6, 6, 3, 6, -0.03d, 0);
    }

    public ACSAttributesContainer getContainer(){
        return this;
    }

    public static void setDefaults(DefaultsConfigObj[] defaultContainersFromConfig) {
        for(DefaultsConfigObj cfgObj : defaultContainersFromConfig){
            defaultContainers.put(cfgObj.Type, new ACSAttributesContainer(cfgObj.Angle, cfgObj.Range, 0, cfgObj.Min_backswing_ticks_in_percents, cfgObj.Max_combo_num, cfgObj.Speed_reduction_modifier, cfgObj.Combo_charge_speed_bonus));
        }
    }

    private static boolean canDestroyBySwingDefault(BlockState blockState){
        Material material = blockState.getMaterial();
        return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !blockState.isIn(BlockTags.LEAVES) && blockState.getBlock() != Blocks.COBWEB ? false : true;
    }

    public static boolean canDestroyBySwing(Item item, BlockState blockState){
        if(item instanceof AdvancedSwordItem){
            return ((AdvancedSwordItem)item).canDestroyBySwing(blockState);
        }
        else if(item instanceof AdvancedHoeItem){
            return ((AdvancedHoeItem)item).canDestroyBySwing(blockState);
        }
        else if(item instanceof SwordItem){
            return canDestroyBySwingDefault(blockState);
        }
        else if(item instanceof HoeItem){
            return canDestroyBySwingDefault(blockState);
        }
        else return false;
    }

    /**
     * returns ACS attributes container form item
     * */
    public static ACSAttributesContainer get(PlayerEntity player){
        Item item = player.getHeldItemMainhand().getItem();
        if(ACSAttributes.getAttrbiutesById().containsKey(item.getRegistryName().toString())){
            if(ACSAttributes.getAttrbiutesById().get(item.getRegistryName().toString()).NEEDED_BACKSWING_TICKS == 0){
                ACSAttributesContainer container = ACSAttributes.getAttrbiutesById().get(item.getRegistryName().toString());
                return new ACSAttributesContainer (container.ANGLE, container.RANGE, player.getCooldownPeriod(), player.getCooldownPeriod() * (container.MIN_BACKSWING_TICKS / 100), container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
            }
            else{
                return ACSAttributes.getAttrbiutesById().get(item.getRegistryName().toString());
            }
        }
        if(item instanceof AdvancedTieredItem){
            return ((AdvancedTieredItem)item).getACSAttributes();
        }
        else if(item instanceof SwordItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.SWORD);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, player.getCooldownPeriod(), player.getCooldownPeriod() * (container.MIN_BACKSWING_TICKS / 100), container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof PickaxeItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.PICKAXE);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, player.getCooldownPeriod(), player.getCooldownPeriod() * (container.MIN_BACKSWING_TICKS / 100), container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof AxeItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.AXE);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, player.getCooldownPeriod(), player.getCooldownPeriod() * (container.MIN_BACKSWING_TICKS / 100), container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof ShovelItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.SHOVEL);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, player.getCooldownPeriod(), player.getCooldownPeriod() * (container.MIN_BACKSWING_TICKS / 100), container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof HoeItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.HOE);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, player.getCooldownPeriod(), player.getCooldownPeriod() * (container.MIN_BACKSWING_TICKS / 100), container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else{
            return ACSAttributes.getHandsAttributes();
        }
    }

    /**
     * returns ACS attributes container form item ONLY FOR GUI
     * */
    public static ACSAttributesContainer get(Item item){
        if(ACSAttributes.getAttrbiutesById().containsKey(item.getRegistryName().toString())){
            return ACSAttributes.getAttrbiutesById().get(item.getRegistryName().toString());
        }
        if(item instanceof AdvancedTieredItem){
            return ((AdvancedTieredItem)item).getACSAttributes();
        }
        else if(item instanceof SwordItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.SWORD);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, 0, 0, container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof PickaxeItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.PICKAXE);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, 0, 0, container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof AxeItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.AXE);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE,0, 0, container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof ShovelItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.SHOVEL);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, 0, 0, container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else if(item instanceof HoeItem){
            ACSAttributesContainer container = defaultContainers.get(ToolType.HOE);
            return new ACSAttributesContainer (container.ANGLE, container.RANGE, 0, 0, container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        }
        else{
            return ACSAttributes.getHandsAttributes();
        }
    }

    @Override
    public String toString() {
        return "Angle = " + this.ANGLE + " range = " + this.RANGE + " needed_backswing_ticks = " + this.NEEDED_BACKSWING_TICKS + " min_backswing_ticks = " + this.MIN_BACKSWING_TICKS + " max_combo_num = " + this.MAX_COMBO_NUM + " speed_reduce_modifier = " + this.SPEED_REDUCTION_MODIFIER + " combo_charging_speed_bonus = " + this.COMBO_CHARGING_SPEED_BOUNS;
    }
}
