package com.byalexeykh.advancedcombatsystem.networking.messages;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageDestroyBlock {
    private static BlockPos BlockToDestroy;
    private static boolean isMessageValid = false;
    private static Logger LOGGER = LogManager.getLogger();

    public MessageDestroyBlock(){ isMessageValid = false; }

    public MessageDestroyBlock(BlockPos BlockToDestroy) {
        this.BlockToDestroy = BlockToDestroy;
    }

    public static MessageDestroyBlock decode(PacketBuffer buffer) {
        MessageDestroyBlock ReturnValue = new MessageDestroyBlock();
        try {
            ReturnValue.BlockToDestroy = buffer.readBlockPos();
        } catch(IllegalArgumentException | IndexOutOfBoundsException e){
            LOGGER.error("[ACS] Exception while decoding MessageDestroyBlock: " + e);
            return ReturnValue;
        }
        isMessageValid = true;
        return ReturnValue;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBlockPos(BlockToDestroy);
    }

    // Service func's
    public boolean isMessageValid(){
        return isMessageValid;
    }

    public BlockPos getBlockPosToDestroy(){
        return this.BlockToDestroy;
    }


    @Override
    public String toString(){
        return "[ACS] MessageDestroyBlock: BlockToDestroy: " + BlockToDestroy;
    }
}
