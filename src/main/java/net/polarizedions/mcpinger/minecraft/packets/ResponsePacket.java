package net.polarizedions.mcpinger.minecraft.packets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.polarizedions.mcpinger.minecraft.datatypes.StringType;
import net.polarizedions.mcpinger.minecraft.datatypes.Varchar;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class ResponsePacket {
    private static final JsonParser parser = new JsonParser();

    public Response read(byte[] bytes) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        Varchar.decode(is); // length
        int packetId = Varchar.decode(is); // packet id
        if (packetId != 0) {
            return null;
        }

        String responseJson = StringType.decode(is);
        Response resp = new Response();
        JsonObject json = parser.parse(responseJson).getAsJsonObject();
        resp.version = json.getAsJsonObject("version").get("name").getAsString();
        resp.motd = json.getAsJsonObject("description").get("text").getAsString();
        resp.onlinePlayers = json.getAsJsonObject("players").get("online").getAsInt();
        resp.maxPlayers = json.getAsJsonObject("players").get("max").getAsInt();
        resp.players = new ArrayList<>();

        if (json.getAsJsonObject("players").get("sample") != null) {
            JsonArray sample = json.getAsJsonObject("players").getAsJsonArray("sample");
            for (JsonElement entry : sample) {
                resp.players.add(entry.getAsJsonObject().get("name").getAsString());
            }
        }

        return resp;
    }

    public static class Response {
        public List<String> players;
        public int onlinePlayers;
        public int maxPlayers;
        public String motd;
        public String version;

        @Override
        public String toString() {
            return "Response{" +
                    "players=" + players +
                    ", onlinePlayers=" + onlinePlayers +
                    ", maxPlayers=" + maxPlayers +
                    ", motd='" + motd + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }
}
