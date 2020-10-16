package com.byalexeykh.advancedcombatsystem;

import com.byalexeykh.advancedcombatsystem.config.*;
import com.byalexeykh.advancedcombatsystem.items.*;
import com.byalexeykh.advancedcombatsystem.networking.NetworkHandler;
import com.byalexeykh.advancedcombatsystem.networking.messages.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Mod("advancedcombatsystem")
public class AdvancedCombatSystem
{
    //public static HashMap<String, DeferredRegister<Item>> ITEMS_DYNAMIC_REGISTER = new HashMap<String, DeferredRegister<Item>>();
    //public static List<RegistryObject<Item>> ITEMS_TO_REGISTER = new ArrayList<>();

    //private static final DeferredRegister<EntityType<?>> MOD_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, AdvancedCombatSystem.MODID);
    //public static final RegistryObject<EntityType<SkeletonWarriorEntity>> SKELETON_WARRIOR = MOD_ENTITIES.register("skeleton_warrior", () -> AdvancedEntities.skeleton_warrior);

    //private static final DeferredRegister<Item> MOD_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AdvancedCombatSystem.MODID);
    //public static final RegistryObject<Item> SKELETON_WARRIOR_SPAWNEGG = MOD_ITEMS.register("skeleton_warrior_spawnegg", () -> AdvancedItems.skeleton_warrior_spawnegg);

    public static final String MODID = "advancedcombatsystem";
    public static Logger LOGGER = LogManager.getLogger();
    public static CommonConfigObj commonCfgObj;
    public static DefaultsConfigObj[] defaultACScontainers;
    public static ItemRegisterContainer[] itemsRegisterContainers;
    public static List<ItemRegisterContainer> itemsRegisterServerContainers = new ArrayList<>();
    public static List<DefaultsConfigObj> defaultServerACScontainers = new ArrayList<>();

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String commonConfigPath = FMLPaths.CONFIGDIR.get().resolve("advancedcombatsystem/advancedcombatsystem-common.json").toString();
    private static final String defaultsConfigPath = FMLPaths.CONFIGDIR.get().resolve("advancedcombatsystem/advancedcombatsystem-defaults.json").toString();
    private static final String AttributesByIdConfigPath = FMLPaths.CONFIGDIR.get().resolve("advancedcombatsystem/advancedcombatsystem-attributesbyid.json").toString();

    public AdvancedCombatSystem(){
        LOGGER.debug("Advanced Combat System initialization...");
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.debug("[ACS] Reading configs");
        // READING COMMON CONFIG =======================================================================================
        if(!new File(commonConfigPath).exists()){
            Config.createFile(commonConfigPath);
            Config.initCommonConfig(gson, commonConfigPath);
            commonCfgObj = Config.getDefaultCommonConfig();
        }
        else{
            commonCfgObj = Config.readCommonConfig(gson, commonConfigPath);
            if(commonCfgObj.reset_Configs_To_Default){
                Config.initCommonConfig(gson, commonConfigPath);
            }
        }

        // READING DEFAULTS CONFIG =====================================================================================
        if(!new File(defaultsConfigPath).exists() || commonCfgObj.reset_Configs_To_Default){
            Config.createFile(defaultsConfigPath);
            Config.initDefaultsConfig(gson, defaultsConfigPath);
            Config.initCommonConfig(gson, commonConfigPath);
            commonCfgObj = Config.getDefaultCommonConfig();
        }
        defaultACScontainers = Config.readDefaultsConfig(gson, defaultsConfigPath);
        ACSAttributesContainer.setDefaults(defaultACScontainers);

        // READING AttributesById CONFIG ===============================================================================
        if((!new File(AttributesByIdConfigPath).exists() || commonCfgObj.reset_Configs_To_Default)){
            Config.createFile(AttributesByIdConfigPath);
            Config.initAttributesByIdConfig(gson, AttributesByIdConfigPath);
        }
        itemsRegisterContainers = Config.readAttributesByIdConfig(gson, AttributesByIdConfigPath);

        /*if(!itemsRegisterContainers[0].name.equals("example_name")) {
               List<String> modids = new ArrayList<>();
                for (ItemRegisterContainer container : itemsRegisterContainers) {
                    modids.add(container.modid);
                }
                Set<String> set = new HashSet<>(modids);
                modids.clear();
                modids.addAll(set);
                LOGGER.info("[ACS-override] modid's whose items will be overrided: " + modids.toString());

                for (String modid : modids) {
                    ITEMS_DYNAMIC_REGISTER.put(modid, DeferredRegister.create(ForgeRegistries.ITEMS, modid));
                }
                for (Map.Entry<String, DeferredRegister<Item>> entry : ITEMS_DYNAMIC_REGISTER.entrySet()) {
                    entry.getValue().register(FMLJavaModLoadingContext.get().getModEventBus());
                }

                for (ItemRegisterContainer container : itemsRegisterContainers) {
                    LOGGER.info("[ACS-override] Overriding item [" + container.name + "] from mod [" + container.modid + "]");
                    ITEMS_TO_REGISTER.add(ITEMS_DYNAMIC_REGISTER.get(container.modid).register(container.name, () -> AdvancedItems.instantiateItem(container)));
                }
            }else{
                LOGGER.warn("[ACS-override] Found example item, it will not be registered. See advancedcombatsystem-override config.");
            }*/

        // MOD SETUP ===================================================================================================
        if(FMLEnvironment.dist == Dist.CLIENT){
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClienSetup);
        }
        else{
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerSetup);
        }
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onPlayerConnectingToServer);
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
        ACSAttributesContainer acsAttributesContainer = ACSAttributesContainer.get(player);

        basicDamage = (float)player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();

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
        ACSAttributesContainer acsAttributesContainer = ACSAttributesContainer.get(player);
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
                                    playerPos.x + vectorsToTrace[i].x/ 1.5,
                                    playerPos.y + vectorsToTrace[i].y/ 1.5,
                                    playerPos.z + vectorsToTrace[i].z / 1.5),
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
        if(itemToHitWith.getItem() instanceof TieredItem) {
            try{
                NetworkHandler.INSTANCE.sendToServer(new MessageSwingEffects());
            }
            catch(Exception e){
                LOGGER.error("[ACS] ERROR while sending MessageSwingEffects to server" + e);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerConnectingToServer(PlayerEvent.PlayerLoggedInEvent event){
        // If connecting to dedicated server then send configs from server to client
        if(FMLEnvironment.dist == Dist.DEDICATED_SERVER){
            try{
                DefaultsConfigObj[] defaultsConfigObjs = Config.readDefaultsConfig(gson, defaultsConfigPath);
                MessageSendDefaultsConfig msgDefaultCfg;
                Supplier<ServerPlayerEntity> playerSup = () -> (ServerPlayerEntity) event.getPlayer();
                LOGGER.info("[ACS] Sending configs to " + event.getPlayer().getDisplayName().getString());
                for(DefaultsConfigObj configObj : defaultsConfigObjs){
                    msgDefaultCfg = new MessageSendDefaultsConfig(configObj);
                    NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(playerSup), msgDefaultCfg);
                }

                ItemRegisterContainer[] attrByIdConfigObjs = Config.readAttributesByIdConfig(gson, AttributesByIdConfigPath);
                MessageSendAttributesByIdConfig msgAttrByIdCfg;
                for(ItemRegisterContainer container : attrByIdConfigObjs){
                    msgAttrByIdCfg = new MessageSendAttributesByIdConfig(container);
                    NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(playerSup), msgAttrByIdCfg);
                }
            }catch (Exception e){
                LOGGER.error("[ACS] Error while sending configs to player " + event.getPlayer().getDisplayName().getString() + " " + e);
            }
        }else{
            //if connecting to single player server then use local configs
            ACSAttributes.loadAttributesByIdsFromConfig(itemsRegisterContainers);
            ACSAttributesContainer.setDefaults(defaultACScontainers);
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

