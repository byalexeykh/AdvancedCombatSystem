package com.byalexeykh.advancedcombatsystem;

import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import com.byalexeykh.advancedcombatsystem.items.AdvancedSwordItem;
import com.byalexeykh.advancedcombatsystem.items.AdvancedTiredItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "advancedcombatsystem", value = Dist.CLIENT)
public class ACSInputHandler {

    private static Logger LOGGER = LogManager.getLogger();
    private static boolean isMouseLeftKeyPressed = false, isMouseLeftKeyUp = false;
    private static boolean MouseLeftKeyLastValue = false;
    private static boolean isComboAvailable = false, isComboRuined = false, isComboInProgress = false;
    private static boolean isAimingAtBlock = false, isHoldingSword = false;
    public static boolean isAccumulatingPower = false;
    public static boolean isBattleMode = false;
    private static float neededBackswingTicks = 0, minBackswingTicks = 0;
    private static float ticksLMBPressed = 0;
    private static float ticksCanComboInit = 30, ticksCanComboCurrent = 0, ticksCanCombo = ticksCanComboInit;
    private static float comboComplicationDelta = 0.2f, comboTimerInit = 5, comboTimerCurrent = 0;
    private static float dashTimerInit = 70, dashTimerCurrent = 0;
    private static int comboNum = 0, combosAvailable = 4;
    private static Minecraft mc;
    private static UUID REDUCE_SPEED_UUID = UUID.randomUUID();

    public ACSInputHandler(){
        MinecraftForge.EVENT_BUS.register(this);
        KeyBinds.register();
        mc = Minecraft.getInstance();
    }

    // INPUT EVENTS ====================================================================================================
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onToggleBattleModeClick(InputEvent.KeyInputEvent event){
        if(KeyBinds.BattleModeToggle.isPressed()){
            isBattleMode = isBattleMode == true ? false : true;
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPlayerScroll(InputEvent.MouseScrollEvent event){
        isAccumulatingPower = false;
        isComboRuined = true;
        comboTimerCurrent = 0;
        ticksLMBPressed = 0;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onLeftClick(InputEvent.ClickInputEvent event){
        RayTraceResult.Type traceType = mc.objectMouseOver.getType();
        if(isAccumulatingPower || isHoldingSword || traceType == RayTraceResult.Type.ENTITY || traceType == RayTraceResult.Type.MISS || isBattleMode){
            if(event.isAttack()){
                event.setCanceled(true);
            }
        }
        Item currentItem = mc.player.getHeldItemMainhand().getItem();
        neededBackswingTicks = ACSAttributesContainer.get(currentItem).NEEDED_BACKSWING_TICKS;
        minBackswingTicks = ACSAttributesContainer.get(currentItem).MIN_BACKSWING_TICKS;
        combosAvailable = ACSAttributesContainer.get(currentItem).MAX_COMBO_NUM;
    }

    // Main mechanics ==================================================================================================
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(mc.player != null) {
            if (event.phase == TickEvent.Phase.START) {
                isHoldingSword = mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AdvancedSwordItem;
                mc.gameSettings.attackIndicator = AttackIndicatorStatus.OFF;
                if (isAccumulatingPower) {
                    event.player.isSwingInProgress = false;
                }
                if (mc.objectMouseOver != null){
                    isAimingAtBlock = mc.objectMouseOver.getType() == RayTraceResult.Type.BLOCK;
                }
            } else if (event.phase == TickEvent.Phase.END && event.player.world.isRemote) {
                // while LMB down ======================================================================================
                AttributeModifier REDUCE_SPEED = new AttributeModifier(REDUCE_SPEED_UUID, "ASCReduceSpeed", ACSAttributesContainer.get(mc.player.getHeldItemMainhand().getItem()).SPEED_REDUCE_MODIFIER, AttributeModifier.Operation.ADDITION);
                if (mc.gameSettings.keyBindAttack.isKeyDown() && (!isAimingAtBlock || isHoldingSword || isBattleMode)) {
                    isMouseLeftKeyPressed = true;
                    isMouseLeftKeyUp = false;

                    if (ticksLMBPressed < neededBackswingTicks) {
                        isAccumulatingPower = true;
                        ticksLMBPressed = ticksLMBPressed + 1 + ACSAttributesContainer.get(mc.player.getHeldItemMainhand().getItem()).COMBO_CHARGING_ACCELERATOR * comboNum;
                    }

                    if (ticksLMBPressed >= neededBackswingTicks) {
                        if (ticksCanComboCurrent < ticksCanCombo) {
                            isComboAvailable = true;
                            ticksCanComboCurrent++;
                            ACSGuiHandler.drawComboIndicator = true;
                        } else {
                            isComboAvailable = false;
                            ACSGuiHandler.drawComboIndicator = false;
                            mc.player.setSprinting(false);
                            if (!mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(REDUCE_SPEED) && mc.player.getHeldItemMainhand().getItem() instanceof AdvancedTiredItem) {
                                mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(REDUCE_SPEED);
                            }
                        }
                    }
                } else {
                    isAccumulatingPower = false;
                    isMouseLeftKeyPressed = false;
                }

                // on LMB up ===========================================================================================
                if (!isMouseLeftKeyPressed && MouseLeftKeyLastValue) {
                    if (mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(REDUCE_SPEED)) {
                        mc.player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(REDUCE_SPEED);
                    }

                    if (ticksLMBPressed >= minBackswingTicks) {
                        // Resetting values if power was accumulated but LMB was up when aiming at block
                        boolean isBlocking = mc.player.isActiveItemStackBlocking();
                        //LOGGER.warn("isBlocking = " + isBlocking);
                        if ((isAimingAtBlock && !isHoldingSword) && !isBattleMode || isBlocking) {
                            ticksLMBPressed = 0; // TODO make resetVariables() func
                            ticksCanCombo = ticksCanComboInit;
                            comboTimerCurrent = 0;
                            comboNum = 0;
                            isComboRuined = true;
                            isComboAvailable = false;
                            isComboInProgress = false;
                            isAccumulatingPower = false;
                            ACSGuiHandler.drawComboIndicator = false;
                        } else {
                            isAccumulatingPower = false;
                            ACSGuiHandler.drawComboIndicator = false;
                            AdvancedCombatSystem.swing(mc.player, ticksLMBPressed);
                            mc.player.swingArm(Hand.MAIN_HAND);
                            MouseLeftKeyLastValue = isMouseLeftKeyPressed;

                            // Combo processing ============================================================================
                            if (isComboAvailable) {
                                ticksCanComboCurrent = 0;
                                ticksLMBPressed = 0;
                                comboNum++;
                                if (comboNum > combosAvailable) {
                                    comboTimerCurrent = 0;
                                    comboNum = 0;
                                    ticksLMBPressed = neededBackswingTicks - 1;
                                    isComboRuined = true;
                                    isComboAvailable = false;
                                    isComboInProgress = false;
                                    ticksCanCombo = ticksCanComboInit;
                                    //LOGGER.warn("Maximum combo reached!");
                                } else {
                                    comboTimerCurrent = 0;
                                    isComboRuined = false;
                                    isComboAvailable = false;
                                    isComboInProgress = true;
                                    ticksCanCombo = ticksCanComboInit * (float) (Math.pow(comboComplicationDelta, comboNum));
                                    //LOGGER.warn("Combo passed! new combo window: " + ticksCanCombo);
                                    ACSGuiHandler.drawComboPassedIndicator = true;
                                }
                            } else {
                                ACSGuiHandler.drawComboPassedIndicator = false;
                                isComboRuined = true;
                                isComboInProgress = false;
                                ticksCanCombo = ticksCanComboInit;
                                comboNum = 0;
                                //LOGGER.warn("Combo failed! new combo window: " + ticksCanCombo);
                            }
                        }
                        MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                    } else {
                        if (!isMouseLeftKeyUp) {
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
                if (isComboRuined) {
                    if (ticksLMBPressed > 0) {
                        ticksLMBPressed -= 2;
                    }
                    if (ticksCanComboCurrent > 0) {
                        ticksCanComboCurrent--;
                    }
                    if (ticksCanComboCurrent <= 0 && ticksLMBPressed <= 0) {
                        isComboRuined = false;
                        ticksLMBPressed = 0;
                        ticksCanComboCurrent = 0;
                    }
                }
                if (isComboInProgress && !isAccumulatingPower) {
                    comboTimerCurrent++;
                }
                if (comboTimerCurrent >= comboTimerInit) {
                    ticksCanCombo = ticksCanComboInit;
                    comboTimerCurrent = 0;
                    comboNum = 0;
                    isComboAvailable = false;
                    isComboRuined = true;
                    isComboInProgress = false;
                    //LOGGER.warn("Combo time out!");
                }

                MouseLeftKeyLastValue = isMouseLeftKeyPressed;

                // Dash handling ===========================================================================================
                if (dashTimerCurrent >= dashTimerInit) {
                    boolean isOnGround;
                    try {
                        isOnGround = mc.player.onGround && !mc.player.isOnLadder() && !mc.player.isInWater();
                    } catch (Exception e) {
                        LOGGER.error("Error at dash handling: " + e);
                        isOnGround = true;
                    }
                    // Dash to left
                    if (mc.gameSettings.keyBindLeft.isKeyDown() && mc.gameSettings.keyBindSprint.isKeyDown() && isOnGround) {
                        //LOGGER.warn("Dash to left");
                        dashTimerCurrent = 0;
                        Dash((byte) 1, 0.6f);
                        return;
                    }
                    // Dash to right
                    if (mc.gameSettings.keyBindRight.isKeyDown() && mc.gameSettings.keyBindSprint.isKeyDown() && isOnGround) {
                        //LOGGER.warn("Dash to right");
                        dashTimerCurrent = 0;
                        Dash((byte) 0, 0.6f);
                        return;
                    }
                } else {
                    dashTimerCurrent++;
                }
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

        sideAzimuth = side == 0 ? crosshairAzimuth + 90 : crosshairAzimuth - 90;

        dashMotion = new Vec3d(
                dashStrength * -Math.cos(Math.toRadians(0)) * Math.sin(Math.toRadians(sideAzimuth)),
                0,
                dashStrength * Math.cos(Math.toRadians(0)) *  Math.cos(Math.toRadians(sideAzimuth))
        );
        //LOGGER.warn("processing player dash on client...");
        mc.player.addVelocity(dashMotion.x, dashMotion.y, dashMotion.z);
    }
}
