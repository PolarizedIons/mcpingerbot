package net.polarizedions.mcpinger.minecraft.datatypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class StringType {
    public static String decode(byte[] in) {
        return decode(new ByteArrayInputStream(in));
    }

    public static String decode (ByteArrayInputStream is) {
        int length = Varchar.decode(is); // string length
        int read = is.read();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.appendCodePoint(read);
            read = is.read();
        }

        return sb.toString();
    }

    public static byte[] encode(String in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(Varchar.encode(in.length()));
        os.write(in.getBytes());
        return os.toByteArray();
    }

}
