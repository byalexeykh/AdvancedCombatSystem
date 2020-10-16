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
    public String toString() {
        return "AdvancedTieredItem";
    }
}
