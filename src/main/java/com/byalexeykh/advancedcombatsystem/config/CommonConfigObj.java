package com.byalexeykh.advancedcombatsystem.config;

public class CommonConfigObj {
    private static boolean resetAttributesToDefault = false;

    CommonConfigObj(boolean resetAttributesToDefaultIn) {
        this.resetAttributesToDefault = resetAttributesToDefaultIn;
    }

    public static boolean getResetAttributesToDefault(){
        return resetAttributesToDefault;
    }
}
