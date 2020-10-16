package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;

public class ItemRegisterContainer extends ACSAttributesContainer {
    public final String modid;
    public final String name;

    public ItemRegisterContainer(String modid, String name, ACSAttributesContainer container) {
        super(container.ANGLE, container.RANGE, container.NEEDED_BACKSWING_TICKS, container.MIN_BACKSWING_TICKS, container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        this.modid = modid;
        this.name = name;
    }
}
