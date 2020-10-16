package com.byalexeykh.advancedcombatsystem.networking.messages;

import com.byalexeykh.advancedcombatsystem.config.DefaultsConfigObj;
import com.byalexeykh.advancedcombatsystem.config.ItemRegisterContainer;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageSendAttributesByIdConfig {
    private static ItemRegisterContainer itemRegisterContainer;
    private static boolean isMessageValid = false;
    private static Logger LOGGER = LogManager.getLogger();

    public MessageSendAttributesByIdConfig(){ isMessageValid = false; }

    public MessageSendAttributesByIdConfig(ItemRegisterContainer itemRegisterContainer) {
        this.itemRegisterContainer = itemRegisterContainer;
    }

    public static MessageSendAttributesByIdConfig decode(PacketBuffer buffer) {
        MessageSendAttributesByIdConfig ReturnValue = new MessageSendAttributesByIdConfig();
        try {
            ReturnValue.itemRegisterContainer = new ItemRegisterContainer(
                    buffer.readString(),
                    buffer.readString(),
                    new ACSAttributesContainer(
                            buffer.readFloat(),
                            buffer.readFloat(),
                            buffer.readFloat(),
                            buffer.readFloat(),
                            buffer.readInt(),
                            buffer.readDouble(),
                            buffer.readFloat()
                    )
            );
        } catch(IllegalArgumentException | IndexOutOfBoundsException e){
            LOGGER.error("[ACS] Exception while decoding " + MessageSendDefaultsConfig.class.getName() + ": " + e);
            return ReturnValue;
        }
        isMessageValid = true;
        return ReturnValue;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeString(itemRegisterContainer.modid);
        buffer.writeString(itemRegisterContainer.name);
        buffer.writeFloat(itemRegisterContainer.ANGLE);
        buffer.writeFloat(itemRegisterContainer.RANGE);
        buffer.writeFloat(itemRegisterContainer.NEEDED_BACKSWING_TICKS);
        buffer.writeFloat(itemRegisterContainer.MIN_BACKSWING_TICKS);
        buffer.writeInt(itemRegisterContainer.MAX_COMBO_NUM);
        buffer.writeDouble(itemRegisterContainer.SPEED_REDUCTION_MODIFIER);
        buffer.writeFloat(itemRegisterContainer.COMBO_CHARGING_SPEED_BOUNS);
    }

    // Service func's
    public boolean isMessageValid(){
        return isMessageValid;
    }

    public ItemRegisterContainer getItemRegisterContainer(){
        return this.itemRegisterContainer;
    }


    @Override
    public String toString(){
        return "[ACS] " + MessageSendDefaultsConfig.class.getName() + ": DefaultsConfig: " + itemRegisterContainer.toString();
    }
}
