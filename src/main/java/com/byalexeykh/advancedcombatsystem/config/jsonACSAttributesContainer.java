package com.byalexeykh.advancedcombatsystem.config;

import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;

public class jsonACSAttributesContainer extends ACSAttributesContainer {
    public final ToolType type;
    public jsonACSAttributesContainer(ToolType type, float angle, float range, float neededBackswingTicks, float minBackswingTicks, int maxComboNum, double speedReduceModifier, float comboChargingSeedBonus) {
        super(angle, range, neededBackswingTicks, minBackswingTicks, maxComboNum, speedReduceModifier, comboChargingSeedBonus);
        this.type = type;
    }

    public jsonACSAttributesContainer(ToolType type, ACSAttributesContainer container) {
        super(container.ANGLE, container.RANGE, container.NEEDED_BACKSWING_TICKS, container.MIN_BACKSWING_TICKS, container.MAX_COMBO_NUM, container.SPEED_REDUCE_MODIFIER, container.COMBO_CHARGING_SPEED_BOUNS);
        this.type = type;
    }

    public ACSAttributesContainer toACSAttributesContainer(){
        return new ACSAttributesContainer(this.ANGLE, this.RANGE, this.NEEDED_BACKSWING_TICKS, this.MIN_BACKSWING_TICKS, this.MAX_COMBO_NUM, this.SPEED_REDUCE_MODIFIER, this.COMBO_CHARGING_SPEED_BOUNS);
    }
}
