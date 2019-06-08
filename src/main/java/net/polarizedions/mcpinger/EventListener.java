package net.polarizedions.mcpinger;

import net.polarizedions.mcpinger.config.Config;
import net.polarizedions.mcpinger.config.ConfigManager;
import net.polarizedions.mcpinger.minecraft.Pinger;
import net.polarizedions.mcpinger.minecraft.packets.ResponsePacket;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import java.util.HashMap;
import java.util.Map;

public class EventListener  extends ListenerAdapter {
    private static String PREFIX = ".";
    private static Map<String, Pinger> pingers = new HashMap<>();
    static {
        Config config = ConfigManager.get();
        for (Map.Entry<String, String> entry : config.pingHosts.entrySet()) {
            pingers.put(entry.getKey(), new Pinger(entry.getValue()));
        }
    }

    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        System.out.println(event);
        String msg = event.getMessage();
        if (! msg.startsWith(PREFIX)) {
            return;
        }

        String command = msg.substring(PREFIX.length()).split(" ")[0].toLowerCase();
        switch (command) {
            case "on":
                commandOn(event);
                break;
        }

        for (String key : pingers.keySet()) {
            if (command.equalsIgnoreCase(key)) {
                singleServer(event, key, pingers.get(key));
            }
        }
    }

    private void singleServer(GenericMessageEvent event, String name, Pinger pinger) {
        StringBuilder sb = new StringBuilder(name).append(": ");
        ResponsePacket.Response response = pinger.ping();

        sb
                .append("[VERSION: ")
                .append(response.version)
                .append("] [PLAYERS: ")
                .append(response.onlinePlayers)
                .append("/")
                .append(response.maxPlayers)
                .append("]: ")
                .append(String.join(", ", response.players));

        event.respondWith(sb.toString());
    }

    private void commandOn(GenericMessageEvent event) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Pinger> entry : pingers.entrySet()) {
            ResponsePacket.Response response = entry.getValue().ping();
            sb.append(entry.getKey());
            if (response == null) {
                sb.append(" [OFFLINE] ");
            }
            else {
                sb.append(" [").append(response.onlinePlayers).append("/").append(response.maxPlayers).append("] ");
            }
        }

        event.respondWith(sb.toString());
    }
}
