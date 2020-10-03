package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import net.minecraft.item.IItemTier;

public class jsonACSAttributesContainer extends ACSAttributesContainer {
    private static String modid;
    private static String name;
    private static ToolType type;
    private static IItemTier tier;
    public jsonACSAttributesContainer(String modid, String name, IItemTier tier, ToolType type, float angle, float range, float neededBackswingTicks, float minBackswingTicks, int maxComboNum, double speedReduceModifier, float comboChargingAccelerator) {
        super(angle, range, neededBackswingTicks, minBackswingTicks, maxComboNum, speedReduceModifier, comboChargingAccelerator);
        this.modid = modid;
        this.tier = tier;
        this.type = type;
        this.name = name;
    }

    public ACSAttributesContainer toACSAttributesContainer(){
        return new ACSAttributesContainer(this.ANGLE, this.RANGE, this.NEEDED_BACKSWING_TICKS, this.MIN_BACKSWING_TICKS, this.MAX_COMBO_NUM, this.SPEED_REDUCE_MODIFIER, this.COMBO_CHARGING_ACCELERATOR);
    }

    public static String getModid() {
        return modid;
    }

    public static String getName() {
        return name;
    }

    public static ToolType getType() {
        return type;
    }

    public static IItemTier getTier() { return tier; }
}
