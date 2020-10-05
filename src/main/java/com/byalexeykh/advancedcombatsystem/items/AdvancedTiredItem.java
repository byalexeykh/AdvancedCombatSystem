package com.byalexeykh.advancedcombatsystem.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class AdvancedTiredItem extends TieredItem {
    // ACS attributes
    private final float angle;
    private final float range;
    private final float neededBackswingTicks;
    private final float minBackswingTicks;
    private final int maxComboNum;
    private final double speedReduceModifier;
    private final float comboChargingAccelerator; //TODO rename it to comboSpeedBonus
    protected static final UUID ATTACK_ANGLE_MODIFIER = UUID.randomUUID();
    @Deprecated
    public AdvancedTiredItem(IItemTier tier, float angleIn, float rangeIn, float neededBackswingTicksIn, float minBackswingTicksIn, int maxComboNumIn, double speedReduceModifierIn, float comboTicksModifierIn, Properties builder) {
        super(tier, builder);
        this.angle = angleIn;
        this.range = rangeIn;
        this.neededBackswingTicks = neededBackswingTicksIn;
        this.minBackswingTicks = minBackswingTicksIn;
        this.maxComboNum = maxComboNumIn;
        this.speedReduceModifier = speedReduceModifierIn;
        this.comboChargingAccelerator = comboTicksModifierIn;
    }

    public AdvancedTiredItem(IItemTier tier, ACSAttributesContainer attrContainer, Properties builder){
        super(tier, builder);
        this.angle = attrContainer.ANGLE;
        this.range = attrContainer.RANGE;
        this.neededBackswingTicks = attrContainer.NEEDED_BACKSWING_TICKS;
        this.minBackswingTicks = attrContainer.MIN_BACKSWING_TICKS;
        this.maxComboNum = attrContainer.MAX_COMBO_NUM;
        this.speedReduceModifier = attrContainer.SPEED_REDUCE_MODIFIER;
        this.comboChargingAccelerator = attrContainer.COMBO_CHARGING_SPEED_BOUNS;
    }

    public ACSAttributesContainer getACSAttributes(){
        return new ACSAttributesContainer(this.angle, this.range, this.neededBackswingTicks, this.minBackswingTicks, this.maxComboNum, this.speedReduceModifier, this.comboChargingAccelerator);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("items.description.angle", this.angle).appendText(" " + this.angle).applyTextStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("items.description.range", this.range).appendText(" " + this.range).applyTextStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("items.description.maxcombos", this.maxComboNum).appendText(" " + this.maxComboNum).applyTextStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("items.description.chargingaccelerator", this.comboChargingAccelerator).appendText(" " + this.comboChargingAccelerator).applyTextStyle(TextFormatting.GREEN));
    }

    @Override
    public String toString() {
        return "AdvancedTiredItem";
    }
}
