package com.byalexeykh.advancedcombatsystem;

import com.byalexeykh.advancedcombatsystem.config.CommonConfigObj;
import com.byalexeykh.advancedcombatsystem.config.Config;
import com.byalexeykh.advancedcombatsystem.config.itemRegistryContainer;
import com.byalexeykh.advancedcombatsystem.items.*;
import com.byalexeykh.advancedcombatsystem.networking.NetworkHandler;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageDestroyBlock;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwing;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwingEffects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.function.Predicate;

@Mod("advancedcombatsystem")
public class AdvancedCombatSystem
{
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
    public static final RegistryObject<Item> WOODEN_SWORD = ITEMS.register("wooden_sword", () -> AdvancedItems.wooden_sword);
    public static final RegistryObject<Item> GOLDEN_SWORD = ITEMS.register("golden_sword", () -> AdvancedItems.golden_sword);
    public static final RegistryObject<Item> STONE_SWORD = ITEMS.register("stone_sword", () -> AdvancedItems.stone_sword);
    public static final RegistryObject<Item> IRON_SWORD = ITEMS.register("iron_sword", () -> AdvancedItems.iron_sword);
    public static final RegistryObject<Item> DIAMOND_SWORD = ITEMS.register("diamond_sword", () -> AdvancedItems.diamond_sword);
    public static final RegistryObject<Item> WOODEN_AXE = ITEMS.register("wooden_axe", () -> AdvancedItems.wooden_axe);
    public static final RegistryObject<Item> GOLDEN_AXE = ITEMS.register("golden_axe", () -> AdvancedItems.golden_axe);
    public static final RegistryObject<Item> STONE_AXE = ITEMS.register("stone_axe", () -> AdvancedItems.stone_axe);
    public static final RegistryObject<Item> IRON_AXE = ITEMS.register("iron_axe", () -> AdvancedItems.iron_axe);
    public static final RegistryObject<Item> DIAMOND_AXE = ITEMS.register("diamond_axe", () -> AdvancedItems.diamond_axe);
    public static final RegistryObject<Item> WOODEN_HOE = ITEMS.register("wooden_hoe", () -> AdvancedItems.wooden_hoe);
    public static final RegistryObject<Item> GOLDEN_HOE = ITEMS.register("golden_hoe", () -> AdvancedItems.golden_hoe);
    public static final RegistryObject<Item> STONE_HOE = ITEMS.register("stone_hoe", () -> AdvancedItems.stone_hoe);
    public static final RegistryObject<Item> IRON_HOE = ITEMS.register("iron_hoe", () -> AdvancedItems.iron_hoe);
    public static final RegistryObject<Item> DIAMOND_HOE = ITEMS.register("diamond_hoe", () -> AdvancedItems.diamond_hoe);
    public static final RegistryObject<Item> WOODEN_PICKAXE = ITEMS.register("wooden_pickaxe", () -> AdvancedItems.wooden_pickaxe);
    public static final RegistryObject<Item> GOLDEN_PICKAXE = ITEMS.register("golden_pickaxe", () -> AdvancedItems.golden_pickaxe);
    public static final RegistryObject<Item> STONE_PICKAXE = ITEMS.register("stone_pickaxe", () -> AdvancedItems.stone_pickaxe);
    public static final RegistryObject<Item> IRON_PICKAXE = ITEMS.register("iron_pickaxe", () -> AdvancedItems.iron_pickaxe);
    public static final RegistryObject<Item> DIAMOND_PICKAXE = ITEMS.register("diamond_pickaxe", () -> AdvancedItems.diamond_pickaxe);
    public static final RegistryObject<Item> WOODEN_SHOVEL = ITEMS.register("wooden_shovel", () -> AdvancedItems.wooden_shovel);
    public static final RegistryObject<Item> GOLDEN_SHOVEL = ITEMS.register("golden_shovel", () -> AdvancedItems.golden_shovel);
    public static final RegistryObject<Item> STONE_SHOVEL = ITEMS.register("stone_shovel", () -> AdvancedItems.stone_shovel);
    public static final RegistryObject<Item> IRON_SHOVEL = ITEMS.register("iron_shovel", () -> AdvancedItems.iron_shovel);
    public static final RegistryObject<Item> DIAMOND_SHOVEL = ITEMS.register("diamond_shovel", () -> AdvancedItems.diamond_shovel);

    //private static final DeferredRegister<EntityType<?>> MOD_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, AdvancedCombatSystem.MODID);
    //public static final RegistryObject<EntityType<SkeletonWarriorEntity>> SKELETON_WARRIOR = MOD_ENTITIES.register("skeleton_warrior", () -> AdvancedEntities.skeleton_warrior);

    //private static final DeferredRegister<Item> MOD_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AdvancedCombatSystem.MODID);
    //public static final RegistryObject<Item> SKELETON_WARRIOR_SPAWNEGG = MOD_ITEMS.register("skeleton_warrior_spawnegg", () -> AdvancedItems.skeleton_warrior_spawnegg);

    public static final String MODID = "advancedcombatsystem";
    public static Logger LOGGER = LogManager.getLogger();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AdvancedCombatSystem(){
        LOGGER.info("Advanced Combat System initialization...");
        MinecraftForge.EVENT_BUS.register(this);

        CommonConfigObj commonConfig;
        String commonConfigPath = FMLPaths.CONFIGDIR.get().resolve("advancedcombatsystem-common.json").toString();
        if(!new File(commonConfigPath).exists()){
            Config.createFile(commonConfigPath);
            Config.initCommonConfig(gson, commonConfigPath);
            commonConfig = Config.getDefaultCommonConfig();
        }
        else{
            commonConfig = Config.readCommonConfig(gson, FMLPaths.CONFIGDIR.get().resolve("advancedcombatsystem-common.json").toString());
        }

        String itemsPath = FMLPaths.CONFIGDIR.get().resolve("advancedcombatsystem-items.json").toString();
        if(!new File(itemsPath).exists()){
            Config.createFile(itemsPath);
            AdvancedItems.writeDefaultToJson(gson, itemsPath);
        }
        else{
             AdvancedItems.setItemsToRegister(Config.readItemsConfig(gson, itemsPath).getAttrContainers());
        }

        if(FMLEnvironment.dist == Dist.CLIENT){
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClienSetup);
        }
        else{
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerSetup);
        }
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void registerItem(RegistryEvent.Register<Item> event){
        for(itemRegistryContainer i : AdvancedItems.getItemsToRegister()){
            event.getRegistry(). register(i.itemToRegister); // TODO register all items with correct modid and name
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onClienSetup(FMLClientSetupEvent event){
        LOGGER.log(Level.INFO, "Client setup for Advanced Combat System...");
        new ACSInputHandler();
        new ACSGuiHandler();
        new NetworkHandler();
    }

    @SubscribeEvent
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void onServerSetup(FMLDedicatedServerSetupEvent event){
        LOGGER.log(Level.INFO, "Server setup for Advanced Combat System...");
        new NetworkHandler();
    }

    public static float calculateDamage(float ticksSinceLMBPressed, PlayerEntity player, Entity targetEntity){
        float BackswingProgress;
        float basicDamage;
        float currentDamage;
        float jumpModifier = 1.3f;
        float sprintModifier = 1.5f;
        boolean isJumping = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater();
        boolean isSprinting = player.isSprinting();
        ACSAttributesContainer acsAttributesContainer = ACSAttributesContainer.get(player.getHeldItemMainhand().getItem());

        basicDamage = (float)player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue(); //TODO take basic damage from item

        float f1;
        if (targetEntity instanceof LivingEntity) {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((LivingEntity)targetEntity).getCreatureAttribute());
        } else {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
        }

        BackswingProgress = ticksSinceLMBPressed / acsAttributesContainer.NEEDED_BACKSWING_TICKS;
        //LOGGER.warn("BackswingProgress =  " + BackswingProgress);
        //LOGGER.warn("f1 value: " + f1);
        currentDamage = basicDamage * BackswingProgress;
        f1 *= BackswingProgress; // TODO calculate damage considering enchantments on item
        currentDamage += f1;
        //LOGGER.warn("f1 end value: " + f1);
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

        //LOGGER.warn("Current damage: " + currentDamage);
        return currentDamage;
    }

    public static void swing(PlayerEntity player, float ticksSinceLMBPressed){
        ItemStack itemToHitWith = player.getHeldItemMainhand();
        World world = player.getEntityWorld();
        ACSAttributesContainer acsAttributesContainer = ACSAttributesContainer.get(itemToHitWith.getItem());
        float angle = acsAttributesContainer.ANGLE; // Yaw
        float range = acsAttributesContainer.RANGE;
        int traceQuality = (int)((angle / 10) % 2 == 0 ? (angle / 10) - 1 : (angle / 10)); // number of trace attempts. The higher the quality, the denser the trace
        float deltaAngle = angle / (traceQuality - 1);
        double CrosshairAzimuth, CrosshairZenith;
        Vec3d playerPos = new Vec3d(player.getPosX(), player.getPosYEye(), player.getPosZ());
        Vec3d vectorsToTrace[] = new Vec3d[traceQuality];
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
                x,
                y,
                z
        );
        // Calculating points for RayTrace =============================================================================
        for (int i = 1; i < traceQuality; i++) {
            x = range * -Math.cos(Math.toRadians(CrosshairZenith)) * Math.sin(Math.toRadians(startAzimuth - deltaAngle * i));
            y = range * -Math.sin(Math.toRadians(CrosshairZenith));
            z = range * Math.cos(Math.toRadians(CrosshairZenith)) * Math.cos(Math.toRadians(startAzimuth - deltaAngle * i));
            vectorsToTrace[i] = new Vec3d(
                    x,
                    y,
                    z
            );
        }

        // Checking what was hit with trace and doing stuff ============================================================
        for (int i = 0; i < traceQuality; i++) {
            EntityRayTraceResult entityTrace = ProjectileHelper.rayTraceEntities(
                    player,
                    player.getEyePosition(0.5f),
                    new Vec3d(
                            playerPos.x + vectorsToTrace[i].x,
                            playerPos.y + vectorsToTrace[i].y,
                            playerPos.z + vectorsToTrace[i].z),
                    player.getBoundingBox().grow(range, range, range),
                    lef,
                    range
            );

            BlockRayTraceResult blockTrace = world.rayTraceBlocks(
                    new RayTraceContext(
                            player.getEyePosition(0.5f),
                            new Vec3d(
                                    playerPos.x + vectorsToTrace[i].x/ 1.4,
                                    playerPos.y + vectorsToTrace[i].y/ 1.4,
                                    playerPos.z + vectorsToTrace[i].z / 1.4),
                            RayTraceContext.BlockMode.OUTLINE,
                            RayTraceContext.FluidMode.NONE,
                            player
                    )
            );

            if (blockTrace.getType() == RayTraceResult.Type.BLOCK) {
                BlockState hittedBlock = world.getBlockState(blockTrace.getPos());
                if (ACSAttributesContainer.canDestroyBySwing(itemToHitWith.getItem(), hittedBlock)) {
                    try{
                        MessageDestroyBlock messageDestroyBlock = new MessageDestroyBlock(blockTrace.getPos());
                        NetworkHandler.INSTANCE.sendToServer(messageDestroyBlock);
                    }
                    catch (Exception e){
                        LOGGER.error("[ACS] Exception while constructing and sending MessageDestroyBlock to server: " + e);
                    }
                }
            }

            if (entityTrace != null) {
                if(player.canEntityBeSeen(entityTrace.getEntity())){
                    try{
                        MessageSwing messageSwing = new MessageSwing(itemToHitWith, ticksSinceLMBPressed, entityTrace.getEntity().getEntityId());
                        NetworkHandler.INSTANCE.sendToServer(messageSwing);
                    }
                    catch(Exception e){
                        LOGGER.error("[ACS] Exception while constructing and sending MessageSwing to server: " + e);
                    }
                }
            }
        }

        // Checking if the current item is a hand, if not then swing effects are not played
        if(itemToHitWith.getItem() instanceof AdvancedTiredItem) {
            try{
                NetworkHandler.INSTANCE.sendToServer(new MessageSwingEffects());
            }
            catch(Exception e){
                LOGGER.error("[ACS] ERROR while sending MessageSwingEffects to server" + e);
            }
        }
    }

    public static double getDistanceToBlock(Entity entity, BlockPos pos) {
        double deltaX = pos.getX() - entity.getPosX();
        double deltaY = pos.getY() - entity.getPosY();
        double deltaZ = pos.getZ() - entity.getPosZ();

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    public static double getDistanceToEntity(Entity entityStart, Entity entityEnd) {
        double deltaX = entityEnd.getPosX() - entityStart.getPosX();
        double deltaY = entityEnd.getPosY() - entityStart.getPosY();
        double deltaZ = entityEnd.getPosZ() - entityStart.getPosZ();

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    /*public static LivingEntity[] getEntitesNearby(){
        // TODO detect skeletons nearby to draw bow indicator above them
    }*/
}

class LivingEntityFilter implements Predicate<Entity>{
    @Override
    public boolean test(Entity entity) {
        return entity instanceof LivingEntity;
    }
}

class KeyBinds{
    public static KeyBinding DashRight;
    public static KeyBinding DashLeft;
    public static KeyBinding BattleModeToggle;
    public static KeyBinding BattleModeAutodetect;

    public static void register(){
        BattleModeToggle = new KeyBinding("Toggle battle mode", GLFW.GLFW_KEY_B, "Advanced Combat System");
        ClientRegistry.registerKeyBinding(BattleModeToggle);

        DashRight = new KeyBinding("Dash to right", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_D, "Advanced Combat System");
        ClientRegistry.registerKeyBinding(DashRight);
        DashLeft = new KeyBinding("Dash to left", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_A, "Advanced Combat System");
        ClientRegistry.registerKeyBinding(DashLeft);
    }
}

