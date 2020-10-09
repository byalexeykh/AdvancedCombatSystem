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

public class AdvancedTieredItem extends TieredItem {
    // ACS attributes
    private ACSAttributesContainer ACSattributes;

    public AdvancedTieredItem(IItemTier tier, ACSAttributesContainer attrContainer, Properties builder){
        super(tier, builder);
        this.ACSattributes = attrContainer;
    }

    public ACSAttributesContainer getACSAttributes(){
        return this.ACSattributes;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("items.description.angle", ACSattributes.ANGLE).appendText(" " + ACSattributes.ANGLE).applyTextStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("items.description.range", ACSattributes.RANGE).appendText(" " + ACSattributes.RANGE).applyTextStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("items.description.maxcombos", ACSattributes.MAX_COMBO_NUM).appendText(" " + ACSattributes.MAX_COMBO_NUM).applyTextStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("items.description.chargingaccelerator", ACSattributes.COMBO_CHARGING_SPEED_BOUNS).appendText(" " + ACSattributes.COMBO_CHARGING_SPEED_BOUNS).applyTextStyle(TextFormatting.GREEN));
    }

    @Override
    public String toString() {
        return "AdvancedTieredItem";
    }
}
