package com.byalexeykh.advancedcombatsystem.items;

import net.minecraft.item.Item;

public class ACSAttributesContainer {
    public static float ANGLE;
    public static float RANGE;
    public static float NEEDED_BACKSWING_TICKS;
    public static float MIN_BACKSWING_TICKS;
    public static int MAX_COMBO_NUM;
    public ACSAttributesContainer(float angle, float range, float neededBackswingTicks, float minBackswingTicks, int maxComboNum){
        this.ANGLE = angle;
        this.RANGE = range;
        this.NEEDED_BACKSWING_TICKS = neededBackswingTicks;
        this.MIN_BACKSWING_TICKS = minBackswingTicks;
        this.MAX_COMBO_NUM = maxComboNum;
    }

    /**
     * returns default ACS attributes container
     * */
    public static ACSAttributesContainer getDefaultContainer(){
        return new ACSAttributesContainer(30, 6, 6, 3, 6);
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
