package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;

public class jsonACSAttributesContainer extends ACSAttributesContainer {
    public final String modid;
    public final String name;
    public final ToolType type;
    public final ItemTier tier;
    public jsonACSAttributesContainer(String modid, String name, ItemTier tier, ToolType type, float angle, float range, float neededBackswingTicks, float minBackswingTicks, int maxComboNum, double speedReduceModifier, float comboChargingSeedBonus) {
        super(angle, range, neededBackswingTicks, minBackswingTicks, maxComboNum, speedReduceModifier, comboChargingSeedBonus);
        this.modid = modid;
        this.tier = tier;
        this.type = type;
        this.name = name;
    }

    public ACSAttributesContainer toACSAttributesContainer(){
        return new ACSAttributesContainer(this.ANGLE, this.RANGE, this.NEEDED_BACKSWING_TICKS, this.MIN_BACKSWING_TICKS, this.MAX_COMBO_NUM, this.SPEED_REDUCE_MODIFIER, this.COMBO_CHARGING_SPEED_BOUNS);
    }
}