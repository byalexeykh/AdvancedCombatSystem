package com.byalexeykh.advancedcombatsystem.networking;

import com.byalexeykh.advancedcombatsystem.AdvancedCombatSystem;
import com.byalexeykh.advancedcombatsystem.networking.messages.MessageSwing;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.function.Supplier;

public class ServerMessagesHandler {

    private static Logger LOGGER = LogManager.getLogger();

    public static void onMessageSwingReceived(MessageSwing msg, Supplier<NetworkEvent.Context> ctxSupplier){
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        World world = ctx.getSender().getEntityWorld();
        ctx.setPacketHandled(true);

        if(sideReceived != LogicalSide.SERVER){
            LOGGER.error("MessageSendDamageTo received on wrong side!: " + sideReceived);
            return;
        }
        if(!msg.isMessageValid()){
            LOGGER.error("Invalid message! " + msg.toString());
            return;
        }

        final ServerPlayerEntity player = ctx.getSender();
        if(player == null){
            LOGGER.error("ServerMessagesHandler | onSendDamageToReceived | Player was null when message was received!");
        }

        ctx.enqueueWork(() ->{
            AdvancedCombatSystem.swing(world, player, msg.getItemToHitWith(), msg.getTicksLMBIsPressed());
        });
    }
}
