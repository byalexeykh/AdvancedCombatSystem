package com.byalexeykh.advancedcombatsystem.config;

public class CommonConfigObj {
    public boolean reset_Configs_To_Default = false;
    public boolean draw_Extended_Tooltip = true;

    CommonConfigObj(boolean resetAttributesToDefault, boolean drawExtendedTooltip) {
        this.reset_Configs_To_Default = resetAttributesToDefault;
        this.draw_Extended_Tooltip = drawExtendedTooltip;
    }

    public boolean getResetConfigsToDefault(){
        return reset_Configs_To_Default;
    }

    public boolean getDrawExtendedTooltip(){ return draw_Extended_Tooltip; }
}
