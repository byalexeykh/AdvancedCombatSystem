package com.byalexeykh.advancedcombatsystem.items;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.config.DefaultsConfigObj;
import com.byalexeykh.advancedcombatsystem.config.ItemRegisterContainer;
import com.byalexeykh.advancedcombatsystem.config.jsonACSAttributesContainer;

import java.util.HashMap;
import java.util.Map;

public class ACSAttributes {
    private static ACSAttributesContainer axesAttributes = new ACSAttributesContainer(40, 6, 30, 10, 2, -0.035d, 0.2f);
    private static ACSAttributesContainer swordsAttributes = new ACSAttributesContainer(50, 7, 16, 5, 4, -0.01d, 0.2f);
    private static ACSAttributesContainer hoesAttributes = new ACSAttributesContainer(70, 5, 20, 8, 2, -0.025d, 0.2f);
    private static ACSAttributesContainer pickaxesAttributes = new ACSAttributesContainer(30, 5, 20, 7, 2, -0.025d, 0.2f);
    private static ACSAttributesContainer shovelsAttributes = new ACSAttributesContainer(40, 6, 20, 6, 3, -0.025d, 0.2f);
    private static ACSAttributesContainer handsAttributes = new ACSAttributesContainer(30, 6, 6, 3, 6, -0.03d, 0);
    private static HashMap<String, ACSAttributesContainer> attributesById = new HashMap<>();

    public static void loadAttributesFromConfig(jsonACSAttributesContainer[] cfgObj){
        for (jsonACSAttributesContainer obj : cfgObj){
            switch(obj.type){
                case PICKAXE:
                    pickaxesAttributes = obj.toACSAttributesContainer();
                    break;
                case SHOVEL:
                    shovelsAttributes = obj.toACSAttributesContainer();
                    break;
                case HOE:
                    hoesAttributes = obj.toACSAttributesContainer();
                    break;
                case AXE:
                    axesAttributes = obj.toACSAttributesContainer();
                    break;
                case SWORD:
                    swordsAttributes = obj.toACSAttributesContainer();
                    break;
                case HAND:
                    handsAttributes = obj.toACSAttributesContainer();
                    break;
                default:
                    AdvancedCombatSystem.LOGGER.warn("[ACS-Attributes] Unknown item type while loading attributes from config: " + obj.type + ". Config will not be applied!");
                    break;
            }
        }
    }

    public static void loadAttributesByIdsFromConfig(ItemRegisterContainer[] itemsContainers){
        attributesById.clear();
        for(ItemRegisterContainer i : itemsContainers){
            //AdvancedCombatSystem.LOGGER.info("[ACS] Adding custom attributes to item: " + i.modid + ":" + i.name + " with container: " + i.getContainer());
            attributesById.put(i.modid + ":" + i.name, i.getContainer());
        }
    }

    public static HashMap<String, ACSAttributesContainer> getAttrbiutesById() { return attributesById; }

    public static ACSAttributesContainer getAxesAttributes() {
        return axesAttributes;
    }

    public static ACSAttributesContainer getHandsAttributes() {
        return handsAttributes;
    }

    public static ACSAttributesContainer getHoesAttributes() {
        return hoesAttributes;
    }

    public static ACSAttributesContainer getPickaxesAttributes() {
        return pickaxesAttributes;
    }

    public static ACSAttributesContainer getShovelsAttributes() {
        return shovelsAttributes;
    }

    public static ACSAttributesContainer getSwordsAttributes() {
        return swordsAttributes;
    }
}
