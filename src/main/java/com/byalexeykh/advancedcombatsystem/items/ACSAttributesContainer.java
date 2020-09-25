package com.byalexeykh.advancedcombatsystem.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;

public class ACSAttributesContainer {
    public static float ANGLE;
    public static float RANGE;
    public static float NEEDED_BACKSWING_TICKS;
    public static float MIN_BACKSWING_TICKS;
    public static int MAX_COMBO_NUM;
    public final double SPEED_REDUCE_MODIFIER;
    public ACSAttributesContainer(float angle, float range, float neededBackswingTicks, float minBackswingTicks, int maxComboNum, double speedReduceModifier){
        this.ANGLE = angle;
        this.RANGE = range;
        this.NEEDED_BACKSWING_TICKS = neededBackswingTicks;
        this.MIN_BACKSWING_TICKS = minBackswingTicks;
        this.MAX_COMBO_NUM = maxComboNum;
        this.SPEED_REDUCE_MODIFIER = speedReduceModifier;
    }

    /**
     * returns default ACS attributes container
     * */
    public static ACSAttributesContainer getDefaultContainer(){
        return new ACSAttributesContainer(30, 6, 6, 3, 6, -0.03d);
    }

    public static boolean canDestroyBySwing(Item item, BlockState block){
        if(item instanceof AdvancedSwordItem){
            return ((AdvancedSwordItem)item).canDestroyBySwing(block);
        }
        else if(item instanceof AdvancedHoeItem){
            return ((AdvancedHoeItem)item).canDestroyBySwing(block);
        }
        else return false;
    }

    /**
     * returns ACS attributes container form item
     * */
    public static ACSAttributesContainer get(Item item){
        if(item instanceof AdvancedTiredItem){
            return ((AdvancedTiredItem)item).getACSAttributes();
        }
        else{
            return getDefaultContainer();
        }
    }
}
