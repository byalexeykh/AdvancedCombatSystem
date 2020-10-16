package com.byalexeykh.advancedcombatsystem.networking;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.config.DefaultsConfigObj;
import com.byalexeykh.advancedcombatsystem.config.ItemRegisterContainer;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributes;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSendAttributesByIdConfig;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSendDefaultsConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class ClientMessagesHandler {
    private static Logger LOGGER = LogManager.getLogger();

    public static void OnMessageSendDefaultsConfigReceived(MessageSendDefaultsConfig msg, Supplier<NetworkEvent.Context> ctxSupplier){
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if(sideReceived != LogicalSide.CLIENT){
            LOGGER.error("MessageSendDefaultsConfig received on wrong side!: " + sideReceived);
            return;
        }

        ctx.enqueueWork(() ->{
            AdvancedCombatSystem.defaultServerACScontainers.add(msg.getDefaultsConfig());
            if(AdvancedCombatSystem.defaultServerACScontainers.size() > ToolType.values().length){
                LOGGER.warn("[ACS] More MessageSendDefaultsConfig were sent than needed! Needed: " + ToolType.values().length + ", were sent: " + AdvancedCombatSystem.defaultServerACScontainers.size());
                LOGGER.warn("[ACS] Extra message: " + msg.toString());
            }

            if(AdvancedCombatSystem.defaultServerACScontainers.size() == ToolType.values().length){
                LOGGER.info("[ACS] Applying server config for defaultsConfig");
                DefaultsConfigObj[] toAdd = new DefaultsConfigObj[AdvancedCombatSystem.defaultServerACScontainers.size()];
                AdvancedCombatSystem.defaultServerACScontainers.toArray(toAdd);
                ACSAttributesContainer.setDefaults(toAdd);
            }
        });
    }

    public static void OnMessageSendAttributesByIdConfigReceived(MessageSendAttributesByIdConfig msg, Supplier<NetworkEvent.Context> ctxSupplier){
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if(sideReceived != LogicalSide.CLIENT){
            LOGGER.error("MessageSendDefaultsConfig received on wrong side!: " + sideReceived);
            return;
        }

        ctx.enqueueWork(() ->{
            //AdvancedCombatSystem.LOGGER.warn("[ACS-server] Container to send to client: " + msg.getItemRegisterContainer().toString());
            AdvancedCombatSystem.itemsRegisterServerContainers.add(msg.getItemRegisterContainer());

            if(AdvancedCombatSystem.itemsRegisterServerContainers.size() - 1 == AdvancedCombatSystem.itemsRegisterContainers.length){
                for(ItemRegisterContainer i : AdvancedCombatSystem.itemsRegisterServerContainers){
                    LOGGER.info("[ACS] Applying server config for item: " + i.modid + ":" + i.name + " = " + i.getContainer().toString());
                }
                ItemRegisterContainer[] toAdd = new ItemRegisterContainer[AdvancedCombatSystem.itemsRegisterServerContainers.size()];
                AdvancedCombatSystem.itemsRegisterServerContainers.toArray(toAdd);
                ACSAttributes.loadAttributesByIdsFromConfig(toAdd);
            }
        });
    }
}
