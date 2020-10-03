package com.byalexeykh.advancedcombatsystem.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;

import javax.annotation.Nullable;
import java.util.List;

public class AdvancedSwordItem extends AdvancedTiredItem {
    private final float attackDamage;
    // ACS attributes
    @Deprecated
    public AdvancedSwordItem(IItemTier tier, float attackDamageIn, float angleIn, float rangeIn, float neededBackswingTicksIn, float minBackswingTicksIn, int maxComboNumIn, double speedReduceModifierIn, float comboTicksModifierIn, Properties builder) {
        super(tier, angleIn, rangeIn, neededBackswingTicksIn, minBackswingTicksIn, maxComboNumIn, speedReduceModifierIn, comboTicksModifierIn, builder);
        this.attackDamage = attackDamageIn + tier.getAttackDamage();
    }

    public AdvancedSwordItem(IItemTier tier, float attackDamageIn, ACSAttributesContainer attrContainer, Properties builder){
        super(tier, attrContainer, builder);
        this.attackDamage = attackDamageIn + tier.getAttackDamage();
    }

    public boolean canDestroyBySwing(BlockState blockState){
        Material material = blockState.getMaterial();
        return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !blockState.isIn(BlockTags.LEAVES) && blockState.getBlock() != Blocks.COBWEB ? false : true;
    }

    public float getDefaultDamage(){
        return this.attackDamage;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return false;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 0;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (p_220044_0_) -> {
                p_220044_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

}

