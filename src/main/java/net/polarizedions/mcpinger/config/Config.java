package net.polarizedions.mcpinger.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    public String nickname = "mcpinger";
    public String ircHost = "irc.freenode.net";
    public int ircPort = 6697;
    public List<String> ircChannels = new ArrayList<>();
    public String ircUsername = "";
    public String ircPassword = "";
    public Map<String, String> pingHosts = new HashMap<>();
}
