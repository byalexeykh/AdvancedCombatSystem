package com.byalexeykh.advancedcombatsystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "advancedcombatsystem")
public class ACSGuiHandler extends AbstractGui {
    public ACSGuiHandler(){}

    private final ResourceLocation bar = new ResourceLocation(AdvancedCombatSystem.MODID, "textures/gui/power_indicator.png");
    private final Minecraft mc = Minecraft.getInstance();
    private final int bar_width = 18, bar_height = 7;
    private float timersDefaultValue = 35, drawComboPassedIndicatorTimer = 0, drawComboRuinedTimer = 0;
    public static boolean drawComboIndicator = false, drawComboPassedIndicator = false, drawComboRuined = false;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event){
        //Some blit param namings
        //blit(int x, int y, int textureX, int textureY, int width, int height);
        //blit(int x, int y, TextureAtlasSprite icon, int width, int height);
        //blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
        //blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);
        //blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);
        //innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            int x = 0, y = 0, screenHeight, screenWidth;
            mc.getTextureManager().bindTexture(bar);

            // Calculating center of a screen ==========================================================================
            screenHeight = mc.getMainWindow().getHeight();
            screenWidth = mc.getMainWindow().getWidth();
            if(mc.gameSettings.guiScale != 0){
                x = screenWidth / (2 * mc.gameSettings.guiScale) - bar_width / 2;
                y = screenHeight / (2 * mc.gameSettings.guiScale) + bar_height / 2 + 5;
            }

            // Draw backswing indicator ================================================================================
            if(mc.player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem || mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY || ACSInputHandler.isAccumulatingPower)
            {
                float progressPercent = ACSInputHandler.getTicksLMBPressed() / AdvancedCombatSystem.MaxBackswingTicks;
                int currentWidth = (int)(bar_width * progressPercent);

                blit(x, y, 0, 0, bar_width, bar_height);
                blit(x, y, 0, bar_height, currentWidth, bar_height);
            }

            // Combo indicator =========================================================================================
            if(drawComboIndicator)
            {
                blit(x - 1, y - 1, 18, 6, 37, 14);
            }

            // Combo passed indicator ==================================================================================
            if(drawComboPassedIndicator && drawComboPassedIndicatorTimer > 0)
            {
                blit(x + (bar_width / 2 + 4), y - 5, 0, 17, 10, 16);
            }
            drawComboPassedIndicatorTimer--;
            if(drawComboPassedIndicatorTimer <= 0){
                drawComboPassedIndicator = false;
                drawComboPassedIndicatorTimer = timersDefaultValue;
            }

            // Combo ruined indicator ==================================================================================
            if(drawComboRuined && drawComboRuinedTimer > 0){
                blit(x + bar_width / 2 - 4, y, 0, 82, 4, 89);
                drawComboRuinedTimer--;
            }
            if(drawComboRuinedTimer <= 0){
                drawComboRuined = false;
                drawComboRuinedTimer = timersDefaultValue;
            }
        }
    }
}
