package com.byalexeykh.advancedcombatsystem.networking.messages;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageSwing {

    private static float ticksLMBIsPressed;
    private static boolean isMessageValid = false;
    private static int EntityToHitID;
    private static Logger LOGGER = LogManager.getLogger();

    public MessageSwing(){ isMessageValid = false; }

    public MessageSwing(ItemStack itemToHitWith, float ticksLMBIsPressed, int EntityToHitID) {
        this.ticksLMBIsPressed = ticksLMBIsPressed;
        this.EntityToHitID = EntityToHitID;
    }

    public static MessageSwing decode(PacketBuffer buffer) {
        MessageSwing ReturnValue = new MessageSwing();
        try {
            ReturnValue.ticksLMBIsPressed = buffer.readFloat();
            ReturnValue.EntityToHitID = buffer.readInt();
        } catch(IllegalArgumentException | IndexOutOfBoundsException e){
            LOGGER.error("[ACS] Exception while decoding MessageSwing: " + e);
            return ReturnValue;
        }
        isMessageValid = true;
        return ReturnValue;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeFloat(ticksLMBIsPressed);
        buffer.writeInt(EntityToHitID);
    }

    // Service func's
    public boolean isMessageValid(){
        return isMessageValid;
    }

    public float getTicksLMBIsPressed(){
        return this.ticksLMBIsPressed;
    }

    public int getEntityToHitID() {return this.EntityToHitID; }


    @Override
    public String toString(){
        return "[ACS] MessageSwing: ticksLMBpressed: " + ticksLMBIsPressed + " | EntityToHitID: " + EntityToHitID;
    }
}
