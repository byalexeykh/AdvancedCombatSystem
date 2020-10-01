package com.byalexeykh.advancedcombatsystem.entities;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class AdvancedEntities {
    public static final EntityType<?> skeleton_warrior = EntityType.Builder.
            create(SkeletonWarriorEntity::new, EntityClassification.MONSTER).
            size(1, 1).
            build(new ResourceLocation(AdvancedCombatSystem.MODID, "skeleton_warrior").toString());
}
