package com.byalexeykh.advancedcombatsystem;


import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import java.util.UUID;
import java.util.function.Predicate;

public class AdvancedCombatSystem
{
    public static final String MODID = "advancedcombatsystem";

    // Attribute modifiers UUID's
    public static final UUID MOVEMENT_SPEED_REDUCE_UUID = MathHelper.getRandomUUID(ThreadLocalRandom.current());

    public static final int MaxBackswingTicks = 30;


    public static float calculateDamage(float ticksSinceLMBPressed, ItemStack itemToHitWith, PlayerEntity player, Entity targetEntity){
        float BackswingProgress;
        float basicDamage;
        float currentDamage;
        AttributeMap attributeMap;
        IAttributeInstance attributeInstance;

        basicDamage = (float)player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
        float f1;
        if (targetEntity instanceof LivingEntity) {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((LivingEntity)targetEntity).getCreatureAttribute());
        } else {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
        }

        BackswingProgress = ticksSinceLMBPressed / MaxBackswingTicks;

        currentDamage = basicDamage * BackswingProgress;
        f1 = f1 * BackswingProgress; // TODO calculate damage considering enchantments on item

        return currentDamage;
    }

    public static void swing(World world, PlayerEntity player, ItemStack itemToHitWith, float ticksSinceLMBPressed){
        float angle = 50; // Yaw //TODO make this value editable, most likely add new attribute "angle" to items
        float range = 7; // TODO make this value editable, most likely add new attribute "range" to items
        int traceQuality = 9; // number of trace attempts. The higher the quality, the denser the trace
        float deltaAngle = angle / (traceQuality - 1);
        Vec3d playerPos = new Vec3d(player.getPositionVec().x, player.getPositionVec().y + player.getEyeHeight(), player.getPositionVec().z);
        Vec3d vectorsToTrace[] = new Vec3d[traceQuality];
        double CrosshairAzimuth, CrosshairZenith;
        LivingEntityFilter lef = new LivingEntityFilter();
        CrosshairZenith = player.getPitchYaw().x;
        CrosshairAzimuth = player.getPitchYaw().y;

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


        // Calculating points for RayTrace =============================================================================
        for (int i = 1; i < traceQuality; i++){
            x = range * -Math.cos(Math.toRadians(CrosshairZenith)) * Math.sin(Math.toRadians(startAzimuth - deltaAngle * i));
            y = range * -Math.sin(Math.toRadians(CrosshairZenith));
            z = range * Math.cos(Math.toRadians(CrosshairZenith)) *  Math.cos(Math.toRadians(startAzimuth - deltaAngle * i));
            vectorsToTrace[i] = new Vec3d(
                    playerPos.x + x,
                    playerPos.y + y,
                    playerPos.z + z
            );
        }


        // Checking what was hit with trace and doing stuff ============================================================
        for (int i = 0; i < traceQuality; i++){
            EntityRayTraceResult entityTrace = ProjectileHelper.rayTraceEntities(player, playerPos, vectorsToTrace[i], player.getBoundingBox().grow(range * 2, range * 2, range * 2), lef, range);
            RayTraceResult traceResult = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(1), vectorsToTrace[i], RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
            //world.createExplosion(player, vectorsToTrace[i].x, vectorsToTrace[i].y, vectorsToTrace[i].z, 1, Explosion.Mode.NONE);
            if(entityTrace != null){
                entityTrace.getEntity().attackEntityFrom(
                        DamageSource.causePlayerDamage(player),
                        calculateDamage(ticksSinceLMBPressed, itemToHitWith, player, entityTrace.getEntity())
                );
            }
            switch (traceResult.getType()){
                case BLOCK:
                    BlockRayTraceResult blockTrace = (BlockRayTraceResult)traceResult;
                    //world.setBlockState(blockTrace.getPos(), BlockState.)
                    //System.out.println("[ACS] Found block! : " + blockTrace.getType() + "at " + vectorsToTrace[i]);
                    // TODO play particles on that block
                    // TODO play sound of this block
                    // TODO play swing sound
                    break;
                case MISS:
                    //System.out.println("[ACS] Miss!");
                    // TODO play swing sound
                    break;
            }
        }
    }

}

class LivingEntityFilter implements Predicate<Entity>{
    @Override
    public boolean test(Entity entity) {
        return entity instanceof LivingEntity;
    }
}

