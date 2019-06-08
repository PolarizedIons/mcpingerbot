package net.polarizedions.mcpinger.minecraft;

import net.polarizedions.mcpinger.minecraft.packets.HandshakePacket;
import net.polarizedions.mcpinger.minecraft.packets.ResponsePacket;
import net.polarizedions.mcpinger.minecraft.packets.StatusPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Pinger {
    private String host;
    private int port;

    public Pinger(String domain) {
        String[] split = domain.split(":");
        this.host = split[0];
        this.port = split.length > 1 ? Integer.parseInt(split[1]) : 25565;
    }

    public ResponsePacket.Response ping() {
        try {
            return this.doPing();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResponsePacket.Response doPing() throws IOException, InterruptedException {
        Socket sock = new Socket(this.host, this.port);
        InputStream is = sock.getInputStream();
        OutputStream os = sock.getOutputStream();

        os.write(new HandshakePacket(this.host, this.port).write());
        os.write(new StatusPacket().write());

        // Wait for response
        int count = 0;
        while (is.available() == 0) {
            Thread.sleep(100);
            count += 1;
            if (count > 500) {
                System.out.println(count);
                return null;
            }
        }

        ByteArrayOutputStream respOs = new ByteArrayOutputStream();
        while (is.available() > 0) {
            respOs.write(is.read());
        }

        return new ResponsePacket().read(respOs.toByteArray());
    }


    public static void main(String[] args) {
        System.out.println(new Pinger("s.nork.club:25565").ping());
        System.out.println(new Pinger("s.nork.club:25566").ping());
    }
}
