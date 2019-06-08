package net.polarizedions.mcpinger.minecraft.packets;

import net.polarizedions.mcpinger.minecraft.datatypes.Varchar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class StatusPacket {

    public byte[] write() throws IOException {
        ByteArrayOutputStream packet = new ByteArrayOutputStream();
        packet.write(Varchar.encode(0)); // packet id
        byte[] packetBytes = packet.toByteArray();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(Varchar.encode(packetBytes.length));
        os.write(packetBytes);
        return os.toByteArray();
    }
}
