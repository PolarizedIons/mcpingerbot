package net.polarizedions.mcpinger;

import net.polarizedions.mcpinger.config.Config;
import net.polarizedions.mcpinger.config.ConfigManager;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.SASLCapHandler;
import org.pircbotx.exception.IrcException;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class Bot {
    PircBotX ircbot;

    Bot() {
        ConfigManager.load();

        buildBot();
    }

    void buildBot() {
        Config config = ConfigManager.get();
        Configuration.Builder configurationBuilder = new Configuration.Builder()
                .setName(config.nickname)
                .setRealName(config.nickname + " - McPinger by PolarizedIons")
                .setLogin(config.nickname) // identity
                .addServer(config.ircHost, config.ircPort)
                .addAutoJoinChannels(config.ircChannels);

        // Optional stuff
        if (!config.ircUsername.isEmpty() && !config.ircPassword.isEmpty()) {
            configurationBuilder.addCapHandler(new SASLCapHandler(config.ircUsername, config.ircPassword));
            configurationBuilder.setNickservNick(config.ircUsername);
            configurationBuilder.setNickservPassword(config.ircPassword);
        }


        System.out.println(configurationBuilder);
        Configuration botConfiguration = configurationBuilder
                .setSocketFactory(SSLSocketFactory.getDefault())
                .setAutoNickChange(true)
                .addListener(new EventListener())
                .setVersion("McPinger by PolarizedIons")
                .setAutoReconnect(true)
                .setAutoReconnectAttempts(50)
                .setAutoReconnectDelay(30000)
                .buildConfiguration();

        this.ircbot = new PircBotX(botConfiguration);
    }

    void run() throws IOException, IrcException {
        ircbot.startBot();
    }

    public static void main(String[] args) throws IOException, IrcException {
        new Bot().run();
    }
}
