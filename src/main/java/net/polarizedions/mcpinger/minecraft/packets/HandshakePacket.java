package net.polarizedions.mcpinger.minecraft.packets;

import net.polarizedions.mcpinger.minecraft.datatypes.StringType;
import net.polarizedions.mcpinger.minecraft.datatypes.UShort;
import net.polarizedions.mcpinger.minecraft.datatypes.Varchar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HandshakePacket {
    private final String host;
    private final int port;

    public HandshakePacket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public byte[] write() throws IOException {
        ByteArrayOutputStream packet = new ByteArrayOutputStream();
        packet.write(Varchar.encode(0)); // packet id
        packet.write(Varchar.encode(12)); // Protocol Version
        packet.write(StringType.encode(this.host)); // host
        packet.write(UShort.encode((short)this.port)); // port
        packet.write(Varchar.encode(1)); // next state
        byte[] packetBytes = packet.toByteArray();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(Varchar.encode(packetBytes.length));
        os.write(packetBytes);
        return os.toByteArray();
    }
}
