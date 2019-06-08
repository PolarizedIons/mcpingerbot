package net.polarizedions.mcpinger.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILENAME = "config.json";
    private static Config config;

    public static Config get() {
        return config;
    }

    public static void load() {
        File confFile = new File(FILENAME);

        if (!confFile.exists()) {
            config = new Config();
            save();
            System.err.println("You must fill out the config!");
            System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(confFile));
            config = gson.fromJson(br, Config.class);
        }
        catch (FileNotFoundException e) {
            System.err.println("Error loading config!");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            FileWriter fw = new FileWriter(new File(FILENAME));
            fw.write(gson.toJson(config));
            fw.close();
        }
        catch (IOException e) {
            System.err.println("Error saving config!");
            e.printStackTrace();
        }
    }
}
