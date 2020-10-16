package com.byalexeykh.advancedcombatsystem.networking.messages;

import com.byalexeykh.advancedcombatsystem.ToolType;
import com.byalexeykh.advancedcombatsystem.config.DefaultsConfigObj;
import com.byalexeykh.advancedcombatsystem.items.ACSAttributesContainer;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageSendDefaultsConfig {
    private static DefaultsConfigObj DefaultsConfig;
    private static boolean isMessageValid = false;
    private static Logger LOGGER = LogManager.getLogger();

    public MessageSendDefaultsConfig(){ isMessageValid = false; }

    public MessageSendDefaultsConfig(DefaultsConfigObj DefaultsConfig) {
        this.DefaultsConfig = DefaultsConfig;
    }

    public static MessageSendDefaultsConfig decode(PacketBuffer buffer) {
        MessageSendDefaultsConfig ReturnValue = new MessageSendDefaultsConfig();
        try {
            ReturnValue.DefaultsConfig = new DefaultsConfigObj(
                    buffer.readEnumValue(ToolType.class),
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
        buffer.writeEnumValue(DefaultsConfig.Type);
        buffer.writeFloat(DefaultsConfig.Angle);
        buffer.writeFloat(DefaultsConfig.Range);
        buffer.writeFloat(0);
        buffer.writeFloat(DefaultsConfig.Min_backswing_ticks_in_percents);
        buffer.writeInt(DefaultsConfig.Max_combo_num);
        buffer.writeDouble(DefaultsConfig.Speed_reduction_modifier);
        buffer.writeFloat(DefaultsConfig.Combo_charge_speed_bonus);
    }

    // Service func's
    public boolean isMessageValid(){
        return isMessageValid;
    }

    public DefaultsConfigObj getDefaultsConfig(){
        return this.DefaultsConfig;
    }


    @Override
    public String toString(){
        return "[ACS] " + MessageSendDefaultsConfig.class.getName() + ": DefaultsConfig: " + DefaultsConfig.toString();
    }
}
