package com.byalexeykh.advancedcombatsystem.networking.messages;

import net.minecraft.network.PacketBuffer;

public class MessageSwingEffects {

    public MessageSwingEffects() {}

    public static MessageSwingEffects decode(PacketBuffer buffer) {
        return new MessageSwingEffects();
    }

    public void encode(PacketBuffer buffer) {}

    @Override
    public String toString(){
        return "MessageSwingEffects";
    }
}
