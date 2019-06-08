package net.polarizedions.mcpinger.minecraft.datatypes;

import java.nio.ByteBuffer;

public class UShort {
    public static short decode(byte[] in) {
        ByteBuffer bb = ByteBuffer.wrap(in);
        return bb.getShort();
    }

    public static byte[] encode(short num) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.putShort(num);
        return bb.array();
    }
}
