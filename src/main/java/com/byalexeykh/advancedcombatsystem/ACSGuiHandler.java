package com.byalexeykh.advancedcombatsystem;

import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import com.byalexeykh.advancedcombatsystem.items.AdvancedSwordItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TieredItem;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem.commonCfgObj;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "advancedcombatsystem", value = Dist.CLIENT)
public class ACSGuiHandler extends AbstractGui {

    public ACSGuiHandler(){
        MinecraftForge.EVENT_BUS.register(this);
        mc = Minecraft.getInstance();
    }

    private final ResourceLocation bar = new ResourceLocation(AdvancedCombatSystem.MODID, "textures/gui/power_indicator.png");
    private final int bar_width = 18, bar_height = 7;
    private float timersDefaultValue = 35, drawComboPassedIndicatorTimer = 0, drawComboRuinedTimer = 0;
    public static boolean drawComboIndicator = false, drawComboPassedIndicator = false, drawBackwingRuined = false;
    private static boolean isBackswingIndicatorDrawed = false;
    private Minecraft mc;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void renderOverlay(RenderGameOverlayEvent event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            int BackswingX = 0, BackswingY = 0, screenHeight, screenWidth;
            mc.getTextureManager().bindTexture(bar);

            // Calculating center of a screen ==========================================================================
            screenHeight = event.getWindow().getScaledHeight();
            screenWidth = event.getWindow().getScaledWidth();
            BackswingX = (screenWidth / 2) - (bar_width / 2);
            BackswingY = (screenHeight / 2) + 7;

            // Draw backswing indicator ================================================================================
            if(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem || mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY || ACSInputHandler.isAccumulatingPower || ACSInputHandler.isBattleMode)
            {
                float neededBackswingTicks = ACSAttributesContainer.get(mc.player).NEEDED_BACKSWING_TICKS;
                float progressPercent = ACSInputHandler.getTicksLMBPressed() / neededBackswingTicks;
                float minBackswingPercent = ACSAttributesContainer.get(mc.player).MIN_BACKSWING_TICKS / neededBackswingTicks;
                int currentWidth = (int)(bar_width * progressPercent);
                int currentMinBackswing = (int)(bar_width * minBackswingPercent);
                isBackswingIndicatorDrawed = true;

                blit(BackswingX, BackswingY, 0, 0, bar_width, bar_height);
                blit(BackswingX, BackswingY, 0, bar_height, currentWidth, bar_height);
                blit(BackswingX + currentMinBackswing, BackswingY + 2, 0, 41, 1, 4);
            }
            else{
                isBackswingIndicatorDrawed = false;
            }

            // Combo indicator =========================================================================================
            if(drawComboIndicator && isBackswingIndicatorDrawed)
            {
                blit(BackswingX - 1, BackswingY - 1, 18, 6, 37, 14);
            }

            // Combo passed indicator ==================================================================================
            if((drawComboPassedIndicator && drawComboPassedIndicatorTimer > 0) && isBackswingIndicatorDrawed)
            {
                blit(BackswingX + (bar_width / 2 + 4), BackswingY - 5, 0, 17, 10, 16);
            }
            drawComboPassedIndicatorTimer--;
            if(drawComboPassedIndicatorTimer <= 0){
                drawComboPassedIndicator = false;
                drawComboPassedIndicatorTimer = timersDefaultValue;
            }

            // Backswing ruined indicator ==============================================================================
            if(drawBackwingRuined && drawComboRuinedTimer > 0 && isBackswingIndicatorDrawed){
                blit(BackswingX + bar_width / 2 - 4, BackswingY, 0, 31, 5, 9);
                drawComboRuinedTimer--;
            }
            if(drawComboRuinedTimer <= 0){
                drawBackwingRuined = false;
                drawComboRuinedTimer = timersDefaultValue;
            }

            // Dash cooldown indicator =================================================================================
            if(ACSInputHandler.getDashTimerCurrent() < ACSInputHandler.getDashTimerInit()){
                float progressPercent = ACSInputHandler.getDashTimerCurrent() / ACSInputHandler.getDashTimerInit();
                blit(BackswingX ,BackswingY + 8, 0, 46, 17, 1);
                blit(BackswingX ,BackswingY + 8, 0, 47, (int)(17 * progressPercent), 1);
            }

            // Battle toggle indicator =================================================================================
            int battleToggleX, battleToggleY;
            battleToggleX = screenWidth / 2 - 6;
            battleToggleY = screenHeight - 50;
            if(!ACSInputHandler.isBattleMode){
                blit(battleToggleX, battleToggleY, 0, 48, 13, 13);
            }else{
                blit(battleToggleX, battleToggleY, 13, 48, 13, 13);
            }

            //Some blit param namings
            //blit(int x, int y, int textureX, int textureY, int width, int height);
            //blit(int x, int y, TextureAtlasSprite icon, int width, int height);
            //blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
            //blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);
            //blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
            //innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onItemTooltipDisplayed(ItemTooltipEvent event){
        if(event.getItemStack().getItem() instanceof TieredItem && commonCfgObj.draw_Extended_Tooltip){
            ACSAttributesContainer container = ACSAttributesContainer.get(event.getItemStack().getItem());
            /*for(ITextComponent i : event.getToolTip()){
                AdvancedCombatSystem.LOGGER.warn("[ACS] Tooltip: " + i.getString() + " index: " + event.getToolTip().indexOf(i));
            }*/
            if(event.getToolTip().size() > 3)
                event.getToolTip().remove(3);
            event.getToolTip().add(new TranslationTextComponent("items.description.angle", container.ANGLE).appendText(" " + container.ANGLE).applyTextStyle(TextFormatting.DARK_GREEN));
            event.getToolTip().add(new TranslationTextComponent("items.description.range", container.RANGE).appendText(" " + container.RANGE).applyTextStyle(TextFormatting.DARK_GREEN));
            event.getToolTip().add(new TranslationTextComponent("items.description.maxcombos", container.MAX_COMBO_NUM).appendText(" " + container.MAX_COMBO_NUM).applyTextStyle(TextFormatting.DARK_GREEN));
            event.getToolTip().add(new TranslationTextComponent("items.description.chargingaccelerator", container.COMBO_CHARGING_SPEED_BOUNS).appendText(" " + container.COMBO_CHARGING_SPEED_BOUNS).applyTextStyle(TextFormatting.DARK_GREEN));
        }
    }
}
