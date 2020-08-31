package com.byalexeykh.advancedcombatsystem.networking.messages;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class MessageSwing {

    private static ItemStack itemToHitWith;
    private static float ticksLMBIsPressed;
    private static boolean isMessageValid = false;

    public MessageSwing(){ isMessageValid = false; }

    public MessageSwing(ItemStack itemToHitWith, float ticksLMBIsPressed) {
        this.itemToHitWith = itemToHitWith;
        this.ticksLMBIsPressed = ticksLMBIsPressed;
    }

    public static MessageSwing decode(PacketBuffer buffer) {
        MessageSwing ReturnValue = new MessageSwing();
        try {
            ReturnValue.itemToHitWith = buffer.readItemStack();
            ReturnValue.ticksLMBIsPressed = buffer.readFloat();
        } catch(IllegalArgumentException | IndexOutOfBoundsException e){
            System.out.println("[ACS] Exception while decoding MessageSwing: " + e);
            return ReturnValue;
        }
        isMessageValid = true;
        return ReturnValue;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeItemStack(itemToHitWith);
        buffer.writeFloat(ticksLMBIsPressed);
    }

    // Service func's
    public boolean isMessageValid(){
        return isMessageValid;
    }

    public float getTicksLMBIsPressed(){
        return this.ticksLMBIsPressed;
    }

    public ItemStack getItemToHitWith() { return this.itemToHitWith; }


    @Override
    public String toString(){
        return "[ACS] MessageSwing: ticksLMBpressed: " + ticksLMBIsPressed + " | Item: " + itemToHitWith;
    }
}
