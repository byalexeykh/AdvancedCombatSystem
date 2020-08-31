package com.byalexeykh.advancedcombatsystem;

import com.byalexeykh.advancedcombatsystem.networking.NetworkHandler;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.AttackIndicatorStatus;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("advancedcombatsystem")
public class ACSInputHandler {

    private static Logger LOGGER = LogManager.getLogger();
    private static boolean isMouseLeftKeyPressed = true;
    private static boolean MouseLeftKeyLastValue = true;
    private static boolean isComboAvailable = false, isComboRuined = false, isComboInProgress = false;
    public static boolean isAccumulatingPower = false;
    private static float ticksLMBPressed = 0;
    private static float ticksCanComboInit = 10, ticksCanComboCurrent = 0, ticksCanCombo = ticksCanComboInit;
    private static float comboComplicationDelta = 0.2f, comboTimerInit = 100, comboTimerCurrent = 0;
    private static float dashTimerInit = 200, dashTimerCurrent = 0;
    private static byte comboNum = 0;
    private static Minecraft mc;

    public ACSInputHandler(){
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ACSGuiHandler());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::OnCommonSetup);
    }

    @SubscribeEvent
    public void OnCommonSetup(FMLCommonSetupEvent event){
        mc = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void onBlockHarvesting(InputEvent.ClickInputEvent event){
        if(isAccumulatingPower || Minecraft.getInstance().player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem)
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.phase == TickEvent.Phase.START && event.player.world.isRemote) {
            if (isAccumulatingPower) {
                event.player.isSwingInProgress = false;
            }
            mc.gameSettings.attackIndicator = AttackIndicatorStatus.OFF;
        }else{

            // while LMB down ==========================================================================================
            if(mc.gameSettings.keyBindAttack.isKeyDown()){
                isMouseLeftKeyPressed = true;
                if(ticksLMBPressed < AdvancedCombatSystem.MaxBackswingTicks) {
                    RayTraceResult TraceResult = mc.objectMouseOver;
                    if(TraceResult.getType() == RayTraceResult.Type.ENTITY || TraceResult.getType() == RayTraceResult.Type.MISS || mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem){
                        isAccumulatingPower = true;
                    }
                    ticksLMBPressed++;
                }

                if(ticksLMBPressed == AdvancedCombatSystem.MaxBackswingTicks){
                    if(ticksCanComboCurrent < ticksCanCombo){
                        isComboAvailable = true;
                        ticksCanComboCurrent++;
                        ACSGuiHandler.drawComboIndicator = true;
                    }
                    else{
                        isComboAvailable = false;
                        ACSGuiHandler.drawComboIndicator = false;
                    }
                }
            }
            else{
                isMouseLeftKeyPressed = false;
            }
            //==========================================================================================================

            // on LMB up ===============================================================================================
            if (!isMouseLeftKeyPressed && MouseLeftKeyLastValue) {
                LOGGER.warn("=============== LMB UP ===============");
                isAccumulatingPower = false;
                ACSGuiHandler.drawComboIndicator = false;
                MessageSwing msg;

                if(Minecraft.getInstance().player != null){
                    try{
                        msg = new MessageSwing(mc.player.getHeldItem(Hand.MAIN_HAND), ticksLMBPressed);
                    }catch (Exception e){
                        LOGGER.error("Exception while constructing MessageSwing: " + e);
                        MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                        return;
                    }
                    MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                }
                else return;

                try{
                    NetworkHandler.INSTANCE.sendToServer(msg);
                }catch (Exception e){
                    LOGGER.error("Exception while sending MessageSwing to server: " + e);
                    MouseLeftKeyLastValue = isMouseLeftKeyPressed;
                    return;
                }
                Minecraft.getInstance().player.swingArm(Hand.MAIN_HAND);

                // Combo processing ====================================================================================
                if(isComboAvailable){
                    ticksCanComboCurrent = 0;
                    ticksLMBPressed = 0;
                    comboTimerCurrent = 0;
                    isComboRuined = false;
                    isComboAvailable = false;
                    comboNum++;
                    isComboInProgress = true;
                    ticksCanCombo = ticksCanComboInit / (comboComplicationDelta * comboNum);
                    LOGGER.warn("Combo passed! new combo window: " + ticksCanCombo);
                    ACSGuiHandler.drawComboPassedIndicator = true;
                }
                else{
                    ACSGuiHandler.drawComboPassedIndicator = false;
                    ACSGuiHandler.drawComboRuined = true;
                    isComboRuined = true;
                    isComboInProgress = false;
                    ticksCanCombo = ticksCanComboInit;
                    comboNum = 0;
                    LOGGER.warn("Combo failed! new combo window: " + ticksCanCombo);
                }
                // =====================================================================================================
            }
            //==========================================================================================================

            // Combo timers
            if(isComboRuined){
                if(ticksLMBPressed > 0) { ticksLMBPressed -= 2; }
                if(ticksCanComboCurrent > 0) { ticksCanComboCurrent--; }
                if(ticksCanComboCurrent <= 0 && ticksLMBPressed <= 0){
                    isComboRuined = false;
                    ticksLMBPressed = 0;
                    ticksCanComboCurrent = 0;
                }
            }
            if(isComboInProgress){
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
            if(dashTimerCurrent <= 0){
                // Dash to left
                if(mc.gameSettings.keyBindLeft.isKeyDown() && mc.gameSettings.keyBindSprint.isKeyDown()){
                    LOGGER.warn("Dash to left");
                    dashTimerCurrent = dashTimerInit;
                    Dash((byte)1, 0.5f);
                    return;
                }
                // Dash to right
                if(mc.gameSettings.keyBindRight.isKeyDown() && mc.gameSettings.keyBindSprint.isKeyDown()){
                    LOGGER.warn("Dash to right");
                    dashTimerCurrent = dashTimerInit;
                    Dash((byte)0, 0.5f);
                    return;
                }
            }else{
                dashTimerCurrent--;
            }
            // =========================================================================================================
        }
    }

    public static float getTicksLMBPressed(){
        return ticksLMBPressed;
    }

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
