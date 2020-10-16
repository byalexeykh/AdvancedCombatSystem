package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;

public class DefaultsConfigObj {
    public final ToolType Type;
    public final float Angle;
    public final float Range;
    public final float Min_backswing_ticks_in_percents;
    public final int Max_combo_num;
    public final double Speed_reduction_modifier;
    public final float Combo_charge_speed_bonus;

    public DefaultsConfigObj(ToolType type, ACSAttributesContainer container){
        this.Type = type;
        this.Angle = container.ANGLE;
        this.Range = container.RANGE;
        this.Min_backswing_ticks_in_percents = container.MIN_BACKSWING_TICKS;
        this.Max_combo_num = container.MAX_COMBO_NUM;
        this.Speed_reduction_modifier = container.SPEED_REDUCTION_MODIFIER;
        this.Combo_charge_speed_bonus = container.COMBO_CHARGING_SPEED_BOUNS;
    }

    @Override
    public String toString() {
        return "DefaultsConfigObj for items of type: " + Type;
    }
}
