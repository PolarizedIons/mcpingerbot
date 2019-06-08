package net.polarizedions.mcpinger.minecraft.datatypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Varchar {
    // https://wiki.vg/Protocol#VarInt_and_VarLong

    public static int decode(byte[] in) {
        return decode(new ByteArrayInputStream(in));
    }

    public static int decode(ByteArrayInputStream is) {
        int numRead = 0;
        int result = 0;
        int read;
        do {
            read = is.read();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public static byte[] encode(int value) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            os.write(temp);
        } while (value != 0);

        return os.toByteArray();
    }

}
