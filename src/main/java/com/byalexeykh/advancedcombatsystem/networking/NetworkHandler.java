package com.byalexeykh.advancedcombatsystem.networking;

import com.byalexeykh.advancedcombatsystem.networking.messages.MessageDestroyBlock;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSendDefaultsConfig;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwing;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwingEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = "advancedcombatsystem")
public class NetworkHandler {

    public NetworkHandler(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static Logger LOGGER = LogManager.getLogger();

    public static SimpleChannel INSTANCE;
    private static final String PROTOCOL_VERSION = "1";

    // Message ID's
    private static final byte SWING_ID = 35;
    private static final byte DESTROY_BLOCK_ID = 36;
    private static final byte PLAY_SWING_EFFECTS_ID = 37;
    private static final byte SEND_CONFIG_TO_CLIENT_ID = 38;

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        LOGGER.debug("[ACS] Registering messages...");
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

        // DestroyBlock message register
        INSTANCE.registerMessage(DESTROY_BLOCK_ID,
                MessageDestroyBlock.class,
                MessageDestroyBlock::encode,
                MessageDestroyBlock::decode,
                ServerMessagesHandler::OnMessageDestroyBlockReceived
        );

        // PlaySwingEffects message register
        INSTANCE.registerMessage(PLAY_SWING_EFFECTS_ID,
                MessageSwingEffects.class,
                MessageSwingEffects::encode,
                MessageSwingEffects::decode,
                ServerMessagesHandler::OnMessageSwingEffectsReceived
        );

        // Send configs to server message register
        INSTANCE.registerMessage(SEND_CONFIG_TO_CLIENT_ID,
                MessageSendDefaultsConfig.class,
                MessageSendDefaultsConfig::encode,
                MessageSendDefaultsConfig::decode,
                ClientMessagesHandler::OnMessageSendDefaultsConfigReceived
        );
    }
}
