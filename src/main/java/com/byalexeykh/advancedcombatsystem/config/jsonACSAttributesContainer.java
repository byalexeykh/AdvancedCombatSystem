package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;

public class jsonACSAttributesContainer extends ACSAttributesContainer {
    public final ToolType type;

    public jsonACSAttributesContainer(ToolType type, ACSAttributesContainer container) {
        super(container.ANGLE, container.RANGE, container.NEEDED_BACKSWING_TICKS, container.MIN_BACKSWING_TICKS, container.MAX_COMBO_NUM, container.SPEED_REDUCTION_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        this.type = type;
    }

    public ACSAttributesContainer toACSAttributesContainer(){
        return new ACSAttributesContainer(this.ANGLE, this.RANGE, this.NEEDED_BACKSWING_TICKS, this.MIN_BACKSWING_TICKS, this.MAX_COMBO_NUM, this.SPEED_REDUCTION_MODIFIER, this.COMBO_CHARGING_SPEED_BOUNS);
    }
}
