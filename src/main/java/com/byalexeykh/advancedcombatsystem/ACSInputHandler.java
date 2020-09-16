package com.byalexeykh.advancedcombatsystem;

import com.byalexeykh.advancedcombatsystem.networking.NetworkHandler;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

@Mod("advancedcombatsystem")
public class ACSInputHandler {

    private static Logger LOGGER = LogManager.getLogger();
    private static boolean isMouseLeftKeyPressed = false, isMouseLeftKeyUp = false;
    private static boolean MouseLeftKeyLastValue = false;
    private static boolean isComboAvailable = false, isComboRuined = false, isComboInProgress = false;
    private static boolean isAimingAtBlock = false, isHoldingSword = false;
    public static boolean isAccumulatingPower = false;
    public static boolean isBattleMode = false;
    public static float neededBackswingTicks = 0, minBackswingTicks = 0;
    private static float ticksLMBPressed = 0;
    private static float ticksCanComboInit = 10, ticksCanComboCurrent = 0, ticksCanCombo = ticksCanComboInit;
    private static float comboComplicationDelta = 0.2f, comboTimerInit = 100, comboTimerCurrent = 0;
    private static float dashTimerInit = 200, dashTimerCurrent = 0;
    private static byte comboNum = 0, combosAvailable = 4;
    private static Minecraft mc;

    public ACSInputHandler(){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ACSGuiHandler());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::OnCommonSetup);
    }

    @SubscribeEvent
    public void OnCommonSetup(FMLCommonSetupEvent event){
        KeyBinds.register();
        mc = Minecraft.getInstance();
    }

    // INPUT EVENTS ====================================================================================================
    @SubscribeEvent
    public void onToggleBattleModeClick(InputEvent.KeyInputEvent event){
        if(KeyBinds.BattleModeToggle.isPressed()){
            isBattleMode = isBattleMode == true ? false : true;
        }
    }

    @SubscribeEvent
    public void onLeftClick(InputEvent.ClickInputEvent event){
        RayTraceResult.Type traceType = mc.objectMouseOver.getType();
        if(isAccumulatingPower || isHoldingSword || traceType == RayTraceResult.Type.ENTITY || traceType == RayTraceResult.Type.MISS || isBattleMode){
            if(event.isAttack()){
                event.setCanceled(true);
            }
        }
        neededBackswingTicks = (float)AdvancedCombatSystem.getACSAttributesVanilla(mc.player.getHeldItem(Hand.MAIN_HAND).getItem()).get(3);
        minBackswingTicks = (float)AdvancedCombatSystem.getACSAttributesVanilla(mc.player.getHeldItem(Hand.MAIN_HAND).getItem()).get(6);
        combosAvailable = (byte)AdvancedCombatSystem.getACSAttributesVanilla(mc.player.getHeldItem(Hand.MAIN_HAND).getItem()).get(5);
    }

    @SubscribeEvent
    public void onPlayerScroll(InputEvent.MouseScrollEvent event){
        isAccumulatingPower = false;
        isComboRuined = true;
        comboTimerCurrent = 0;
        ticksLMBPressed = 0;
    }

    /*@SubscribeEvent
    public void onWorldEntering(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof PlayerEntity){ // TODO think if there are ways to do it better
            try {
                neededBackswingTicks = (float)AdvancedCombatSystem.getACSAttributesVanilla(mc.player.getHeldItem(Hand.MAIN_HAND).getItem()).get(3);
            }catch (Exception e){
                LOGGER.error("Error while reading neededBackswingTicks on world enter: " + e);
            }
        }
    }*/

    // Main mechanics ==================================================================================================
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START && event.player.world.isRemote) {
            if (isAccumulatingPower) {
                event.player.isSwingInProgress = false;
            }
            isHoldingSword = mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem;
            isAimingAtBlock = mc.objectMouseOver.getType() == RayTraceResult.Type.BLOCK;
            mc.gameSettings.attackIndicator = AttackIndicatorStatus.OFF;
        }else{
            // while LMB down ==========================================================================================
            if(mc.gameSettings.keyBindAttack.isKeyDown()){
                isMouseLeftKeyPressed = true;
                isMouseLeftKeyUp = false;

                if(ticksLMBPressed < neededBackswingTicks) {
                    if(isHoldingSword || !isAimingAtBlock || isBattleMode){
                        LOGGER.warn("accumulating power");
                        isAccumulatingPower = true;
                        ticksLMBPressed++;
                    }
                    else{
                        isAccumulatingPower = false;
                    }
                }

                if(ticksLMBPressed == neededBackswingTicks){
                    if(ticksCanComboCurrent < ticksCanCombo){
                        isComboAvailable = true;
                        ticksCanComboCurrent++;
                        ACSGuiHandler.drawComboIndicator = true;
                    }
                    else{
                        isComboAvailable = false;
                        ACSGuiHandler.drawComboIndicator = false;
                        mc.player.setSprinting(false);
                        if(!mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(AdvancedCombatSystem.DEFAULT_REDUCE_SPEED)){
                            mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(AdvancedCombatSystem.DEFAULT_REDUCE_SPEED);
                        }
                    }
                }
            }
            else{
                isMouseLeftKeyPressed = false;
            }

            // on LMB up ===============================================================================================
            if (!isMouseLeftKeyPressed && MouseLeftKeyLastValue) {
                LOGGER.warn("========== LMB UP ============");

                if(mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(AdvancedCombatSystem.DEFAULT_REDUCE_SPEED)){
                    mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(AdvancedCombatSystem.DEFAULT_REDUCE_SPEED);
                }

                if(ticksLMBPressed >= minBackswingTicks){
                    // Resetting values if power was accumulated but LMB was up when aiming at block
                    if((isAimingAtBlock && !isHoldingSword) && !isBattleMode){
                        ticksLMBPressed = 0;
                        ticksCanCombo = ticksCanComboInit;
                        comboTimerCurrent = 0;
                        comboNum = 0;
                        isComboRuined = true;
                        isComboAvailable = false;
                        isComboInProgress = false;
                        isAccumulatingPower = false;
                        ACSGuiHandler.drawComboIndicator = false;
                        LOGGER.warn("Aiming at block with accumulated power!");
                    }
                    else{
                        isAccumulatingPower = false;
                        ACSGuiHandler.drawComboIndicator = false;
                        MessageSwing msg;
                        if (Minecraft.getInstance().player != null) {
                            try {
                                msg = new MessageSwing(mc.player.getHeldItem(Hand.MAIN_HAND), ticksLMBPressed);
                            } catch (Exception e) {
                                LOGGER.error("Exception while constructing MessageSwing: " + e);
                                MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                                return;
                            }
                            MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                        } else return;

                        try {
                            NetworkHandler.INSTANCE.sendToServer(msg);
                        } catch (Exception e) {
                            LOGGER.error("Exception while sending MessageSwing to server: " + e);
                            MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                            return;
                        }
                        mc.player.swingArm(Hand.MAIN_HAND);
                        // Combo processing ====================================================================================
                        if(isComboAvailable){
                            ticksCanComboCurrent = 0;
                            ticksLMBPressed = 0;
                            comboNum++;
                            if(comboNum > combosAvailable){
                                comboTimerCurrent = 0;
                                comboNum = 0;
                                isComboRuined = true;
                                isComboAvailable = false;
                                isComboInProgress = false;
                                ticksCanCombo = ticksCanComboInit;
                                LOGGER.warn("Maximum combo reached!");
                            }else{
                                comboTimerCurrent = 0;
                                isComboRuined = false;
                                isComboAvailable = false;
                                isComboInProgress = true;
                                ticksCanCombo = ticksCanComboInit / (comboComplicationDelta * comboNum);
                                LOGGER.warn("Combo passed! new combo window: " + ticksCanCombo);
                                ACSGuiHandler.drawComboPassedIndicator = true;
                            }
                        }
                        else{
                            ACSGuiHandler.drawComboPassedIndicator = false;
                            isComboRuined = true;
                            isComboInProgress = false;
                            ticksCanCombo = ticksCanComboInit;
                            comboNum = 0;
                            LOGGER.warn("Combo failed! new combo window: " + ticksCanCombo);
                        }
                    }
                    MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                }
                else{
                    if(!isMouseLeftKeyUp){
                        isMouseLeftKeyUp = true;
                        MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                        ACSGuiHandler.drawComboPassedIndicator = false;
                        ACSGuiHandler.drawBackwingRuined = true;
                        isComboRuined = true;
                        isComboInProgress = false;
                        ticksCanCombo = ticksCanComboInit;
                        comboTimerCurrent = 0;
                        comboNum = 0;
                    }
                }
            }

            // Combo timers ============================================================================================
            if(isComboRuined){
                if(ticksLMBPressed > 0) { ticksLMBPressed -= 2; }
                if(ticksCanComboCurrent > 0) { ticksCanComboCurrent--; }
                if(ticksCanComboCurrent <= 0 && ticksLMBPressed <= 0){
                    isComboRuined = false;
                    ticksLMBPressed = 0;
                    ticksCanComboCurrent = 0;
                }
            }
            if(isComboInProgress && !isAccumulatingPower){
                comboTimerCurrent++;
            }
            if(comboTimerCurrent >= comboTimerInit){
                ticksCanCombo = ticksCanComboInit;
                comboTimerCurrent = 0;
                comboNum = 0;
                isComboAvailable = false;
                isComboRuined = true;
                isComboInProgress = false;
                LOGGER.warn("Combo time out!");
            }

            MouseLeftKeyLastValue = isMouseLeftKeyPressed;

            // Dash handling ===========================================================================================
            if(dashTimerCurrent >= dashTimerInit){
                boolean isOnGround;
                try{
                    isOnGround = mc.player.onGround && !mc.player.isOnLadder() && !mc.player.isInWater();
                }catch (Exception e){
                    LOGGER.error("Error at dash handling: " + e);
                    isOnGround = true;
                }
                // Dash to left
                if(mc.gameSettings.keyBindLeft.isKeyDown() && mc.gameSettings.keyBindSprint.isKeyDown() && isOnGround){
                    LOGGER.warn("Dash to left");
                    dashTimerCurrent = 0;
                    Dash((byte)1, 0.7f);
                    return;
                }
                // Dash to right
                if(mc.gameSettings.keyBindRight.isKeyDown() && mc.gameSettings.keyBindSprint.isKeyDown() && isOnGround){
                    LOGGER.warn("Dash to right");
                    dashTimerCurrent = 0;
                    Dash((byte)0, 0.7f);
                    return;
                }
            }else{
                dashTimerCurrent++;
            }
        }
    }


    public static float getTicksLMBPressed(){
        return ticksLMBPressed;
    }

    public static float getDashTimerCurrent(){ return dashTimerCurrent; }

    public static float getDashTimerInit(){ return dashTimerInit; }

    // side: 1 - left , 0 - right
    private static void Dash(byte side, float dashStrength){
        double sideAzimuth;
        float crosshairAzimuth = mc.player.getPitchYaw().y;
        Vec3d dashMotion;

        if(side == 0){
            sideAzimuth = crosshairAzimuth + 90;
        }else{
            sideAzimuth = crosshairAzimuth - 90;
        }

        dashMotion = new Vec3d(
                dashStrength * -Math.cos(Math.toRadians(0)) * Math.sin(Math.toRadians(sideAzimuth)),
                0,
                dashStrength * Math.cos(Math.toRadians(0)) *  Math.cos(Math.toRadians(sideAzimuth))
        );
        LOGGER.warn("processing player dash on client...");
        mc.player.addVelocity(dashMotion.x, dashMotion.y, dashMotion.z);
    }
}
