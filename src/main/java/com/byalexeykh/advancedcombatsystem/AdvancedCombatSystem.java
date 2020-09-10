package com.byalexeykh.advancedcombatsystem;


import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class AdvancedCombatSystem
{
    public static final String MODID = "advancedcombatsystem";
    private static Logger LOGGER = LogManager.getLogger();

    // Attribute modifiers UUID's
    public static final UUID MOVEMENT_SPEED_REDUCE_UUID = MathHelper.getRandomUUID(ThreadLocalRandom.current());

    public static final int DefaultBackswingTicks = 30;


    public static float calculateDamage(float ticksSinceLMBPressed, PlayerEntity player, Entity targetEntity, boolean isJumping, boolean isSprinting){
        float BackswingProgress;
        float basicDamage;
        float currentDamage;
        float jumpModifier = 1.3f;
        float sprintModifier = 1.5f;

        basicDamage = (float)player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        float f1;
        if (targetEntity instanceof LivingEntity) {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((LivingEntity)targetEntity).getCreatureAttribute());
        } else {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
        }

        BackswingProgress = ticksSinceLMBPressed / (float) getACSAttributesVanilla(player.getHeldItem(Hand.MAIN_HAND).getItem()).get((3));

        currentDamage = basicDamage * BackswingProgress;
        f1 *= BackswingProgress; // TODO calculate damage considering enchantments on item
        LOGGER.warn("f1 value: " + f1);
        if(isJumping){
            currentDamage = /*TODO add jumpModifier to item attributes*/ currentDamage * jumpModifier;
            ((ServerWorld)targetEntity.getEntityWorld()).spawnParticle(
                    ParticleTypes.CRIT,
                    targetEntity.getPosX(),
                    targetEntity.getPosYEye(),
                    targetEntity.getPosZ(),
                    5, 0,0, 0,0.3);
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
        }
        if(isSprinting){
            currentDamage = /*TODO add sprintModifier to item attributes*/ currentDamage * sprintModifier;
            ((ServerWorld)targetEntity.getEntityWorld()).spawnParticle(
                    ParticleTypes.CRIT,
                    targetEntity.getPosX(),
                    targetEntity.getPosYEye(),
                    targetEntity.getPosZ(),
                    8, 0,0, 0,0.4);
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
        }

        LOGGER.warn("Current damage: " + currentDamage);
        return currentDamage;
    }

    public static void swing(World world, PlayerEntity player, ItemStack itemToHitWith, float ticksSinceLMBPressed){
        float angle = (float) getACSAttributesVanilla(itemToHitWith.getItem()).get(0); // Yaw //TODO add angle to item attributes
        float range = (float) getACSAttributesVanilla(itemToHitWith.getItem()).get(1); // TODO add range to item attributes
        int traceQuality = (int) getACSAttributesVanilla(itemToHitWith.getItem()).get(2); // number of trace attempts. The higher the quality, the denser the trace
        float deltaAngle = angle / (traceQuality - 1);
        boolean isJumping = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater();
        boolean isSprinting = player.isSprinting();
        double CrosshairAzimuth, CrosshairZenith;
        Vec3d playerPos = new Vec3d(player.getPosX(), player.getPosYEye(), player.getPosZ());
        Vec3d vectorsToTrace[] = new Vec3d[traceQuality];
        LivingEntityFilter lef = new LivingEntityFilter();
        CrosshairZenith = player.getPitchYaw().x;
        CrosshairAzimuth = player.getPitchYaw().y;

        LOGGER.warn("Current angle = " + angle + ", range = " + range + ", traceQuality = " + traceQuality + ", deltaAngle = " + deltaAngle);

        // Checking if the current item is a hand ======================================================================
        if(!(boolean) getACSAttributesVanilla(itemToHitWith.getItem()).get(4)) {
            LOGGER.warn("Not hand!");
            // Calculating starting point from where calculations will began ===============================================
            double startAzimuth = CrosshairAzimuth + (angle / 2);
            double x, y, z;
            x = range * -Math.cos(Math.toRadians(CrosshairZenith)) * Math.sin(Math.toRadians(startAzimuth));
            y = range * -Math.sin(Math.toRadians(CrosshairZenith));
            z = range * Math.cos(Math.toRadians(CrosshairZenith)) *  Math.cos(Math.toRadians(startAzimuth));
            vectorsToTrace[0] = new Vec3d(
                    playerPos.x + x,
                    playerPos.y + y,
                    playerPos.z + z
            );
            // Calculating points for RayTrace =========================================================================
            for (int i = 1; i < traceQuality; i++) {
                    x = range * -Math.cos(Math.toRadians(CrosshairZenith)) * Math.sin(Math.toRadians(startAzimuth - deltaAngle * i));
                    y = range * -Math.sin(Math.toRadians(CrosshairZenith));
                    z = range * Math.cos(Math.toRadians(CrosshairZenith)) * Math.cos(Math.toRadians(startAzimuth - deltaAngle * i));
                    vectorsToTrace[i] = new Vec3d(
                            playerPos.x + x,
                            playerPos.y + y,
                            playerPos.z + z
                    );
                }
            // Checking what was hit with trace and doing stuff ========================================================
            for (int i = 0; i < traceQuality; i++) {
                EntityRayTraceResult entityTrace = ProjectileHelper.rayTraceEntities(player, playerPos, vectorsToTrace[i], player.getBoundingBox().grow(range * 2, range * 2, range * 2), lef, range);
                RayTraceResult traceResult = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(1), vectorsToTrace[i], RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
                if (entityTrace != null) {
                    entityTrace.getEntity().attackEntityFrom(
                            DamageSource.causePlayerDamage(player),
                            calculateDamage(ticksSinceLMBPressed, player, entityTrace.getEntity(), isJumping, isSprinting)
                    );
                    if (isSprinting) {
                        entityTrace.getEntity().addVelocity(vectorsToTrace[i].normalize().x * 0.2, vectorsToTrace[i].normalize().y * 0.2, vectorsToTrace[i].normalize().z * 0.2);
                    }
                }
                if (traceResult.getType() == RayTraceResult.Type.BLOCK) {
                    BlockRayTraceResult blockTrace = (BlockRayTraceResult) traceResult;
                    Block hittedBlock = world.getBlockState(blockTrace.getPos()).getBlock();
                    if (itemToHitWith.getItem() instanceof SwordItem || itemToHitWith.getItem() instanceof HoeItem) {
                        if (hittedBlock instanceof LeavesBlock || hittedBlock instanceof TallGrassBlock) {
                            world.destroyBlock(blockTrace.getPos(), false);
                        }
                    }
                    // TODO play particles on that block (partially done)
                    // TODO play sound of this block (partially done)
                }
                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                player.spawnSweepParticles();
            }
        }
        else{
            LOGGER.warn("Hand!");
            Vec3d endPos = (player.getLookVec().mul(range, range, range)).add(playerPos);
            EntityRayTraceResult entityTrace = ProjectileHelper.rayTraceEntities(player, playerPos, endPos, player.getBoundingBox().grow(range * 2, range * 2, range * 2), lef, range);
            RayTraceResult traceResult = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(1), player.getLookVec(), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
            if (entityTrace != null) {
                entityTrace.getEntity().attackEntityFrom(
                        DamageSource.causePlayerDamage(player),
                        calculateDamage(ticksSinceLMBPressed, player, entityTrace.getEntity(), isJumping, isSprinting)
                );
            }
            if (traceResult.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockTrace = (BlockRayTraceResult) traceResult;
                Block hittedBlock = world.getBlockState(blockTrace.getPos()).getBlock();
                if (hittedBlock instanceof LeavesBlock || hittedBlock instanceof TallGrassBlock) {
                    world.destroyBlock(blockTrace.getPos(), false);
                }
            }
        }
    }

    /**
     * Used for getting ACS attributes from vanilla items
     * @return 0 - angle(float) | 1 - range(float) | 2 - traceQuality(int) | 3 - neededBackswingTicks(float) | 4 - isHand(boolean) | 5 - maxComboNum(int)
     */
    public static List<Object> getACSAttributesVanilla(Item itemToHitWith){
        float angle;
        float range;
        float neededBackswingTicks;
        int traceQuality;
        byte maxComboNum = 0;
        boolean isHand;
        if(itemToHitWith instanceof SwordItem) {
            angle = 50;
            range = 7;
            traceQuality = 9;
            neededBackswingTicks = 30;
            maxComboNum = 4;
            isHand = false;
            return Arrays.asList(angle, range, traceQuality, neededBackswingTicks, isHand, maxComboNum);
        }
        else if(itemToHitWith instanceof HoeItem){
            angle = 40;
            range = 6;
            traceQuality = 7;
            neededBackswingTicks = 50;
            maxComboNum = 2;
            isHand = false;
            return Arrays.asList(angle, range, traceQuality, neededBackswingTicks, isHand, maxComboNum);
        }
        else if(itemToHitWith instanceof AxeItem){
            angle = 40;
            range = 6;
            traceQuality = 7;
            neededBackswingTicks = 70;
            maxComboNum = 2;
            isHand = false;
            return Arrays.asList(angle, range, traceQuality, neededBackswingTicks, isHand, maxComboNum);
        }
        else if(itemToHitWith instanceof ShovelItem){
            angle = 40;
            range = 6;
            traceQuality = 7;
            neededBackswingTicks = 50;
            maxComboNum = 3;
            isHand = false;
            return Arrays.asList(angle, range, traceQuality, neededBackswingTicks, isHand, maxComboNum);
        }
        else if(itemToHitWith instanceof PickaxeItem){
            angle = 30;
            range = 6;
            traceQuality = 5;
            neededBackswingTicks = 60;
            maxComboNum = 2;
            isHand = false;
            return Arrays.asList(angle, range, traceQuality, neededBackswingTicks, isHand, maxComboNum);
        }
        else{
            angle = 50;
            range = 6;
            traceQuality = 9;
            neededBackswingTicks = 10;
            maxComboNum = 6;
            isHand = true;
            return Arrays.asList(angle, range, traceQuality, neededBackswingTicks, isHand, maxComboNum);
        }
    }
}

class LivingEntityFilter implements Predicate<Entity>{
    @Override
    public boolean test(Entity entity) {
        return entity instanceof LivingEntity;
    }
}

