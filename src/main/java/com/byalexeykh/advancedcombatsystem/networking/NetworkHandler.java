package com.byalexeykh.advancedcombatsystem.networking;

import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "advancedcombatsystem")
public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static final String PROTOCOL_VERSION = "1";

    // Message ID's
    private static final byte SWING_ID = 35;

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        System.out.println("[ACS] Initiating NetworkHandler");
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("advancedcombatsystem", "acschannel"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        // Registering messages

        // Swing message register
        INSTANCE.registerMessage(SWING_ID,
                MessageSwing.class,
                MessageSwing::encode,
                MessageSwing::decode,
                ServerMessagesHandler::onMessageSwingReceived
        );
    }
}
