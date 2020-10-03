package com.byalexeykh.advancedcombatsystem.config;

public class jsonACSAttributesContainers{
    private jsonACSAttributesContainer[] containers;
    jsonACSAttributesContainers(jsonACSAttributesContainer... containers){
        this.containers = containers;
    }

    public jsonACSAttributesContainer[] getAttrContainers() {
        return containers;
    }
}
