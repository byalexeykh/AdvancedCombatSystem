package com.byalexeykh.advancedcombatsystem.config;

public class CommonConfigObj {
    public boolean resetToDefault = false;

    CommonConfigObj(boolean resetAttributesToDefaultIn) {
        this.resetToDefault = resetAttributesToDefaultIn;
    }

    public boolean getResetAttributesToDefault(){
        return resetToDefault;
    }
}
