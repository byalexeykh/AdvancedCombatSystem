package com.byalexeykh.advancedcombatsystem.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class SkeletonWarriorEntity extends MonsterEntity implements IMob {


    public SkeletonWarriorEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {

    }

    @Override
    protected void registerAttributes() {

    }
}
