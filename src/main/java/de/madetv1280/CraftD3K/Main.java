package de.madetv1280.CraftD3K;

import de.madetv1280.CraftD3K.commands.CraftCommand;
import de.madetv1280.CraftD3K.commands.CraftReloadCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static Main instance;
    private FileConfiguration messages;
    private File messagesFile;

    // Saved player uuid and time;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();


    // Plugin startup logic
    @Override
    public void onEnable() {
        instance = this;

        loadMessages(); // Load Messages from messages.yml

        saveDefaultConfig(); // Loads config.yml, or creates it if it doesn't exist.
        FileConfiguration config = getConfig();

        // Command registration
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("c").setExecutor(new CraftCommand()); // Alias for /craft

        getCommand("craftreload").setExecutor(new CraftReloadCommand()); //

        String currentVersion = getDescription().getVersion();

        /*
        Code to check for the latest version on Modrinth.
        This can be implemented in the future once the mod is approved.
        */

    }

    // Plugin shutdown logic
    @Override
    public void onDisable() {

    }


    public static Main getInstance() {
        return instance;

    }

    public HashMap<UUID, Long> getCooldowns() {
        return cooldowns;
    }

    // Reloads Configuration
    public void reloadMessages() {
        reloadConfig();

    }


    // Loads messages.yml, or creates it if it doesn't exist.
    private void loadMessages() {
        String msgFilePath = getConfig().getString("messages-file"); // Get msg file location out of config file
        if (msgFilePath == null) {
            msgFilePath = "messages.yml";  // Check if path is null, when null, set to default.
        }

        messagesFile = new File(getDataFolder(), msgFilePath);
        if (!messagesFile.exists()) {
            saveResource(msgFilePath, false); // If messages.yml doesn't exist, copy original from resources
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile); // Getting messages from file

    }

    // Method to get messages from messages.yml, including a prefix
    public String getMessage(String key) {
        String prefix = messages.getString("prefix", ""); // Load the prefix from messages.yml, default to empty string if not set
        String msg = messages.getString(key, "Message not found! Report this to an admin!"); // Load the actual message by key, default to "Message not found!" if key doesn't exist

        return (prefix + msg).replace("&", "§").trim(); // Combine prefix and message, replace "&" with "§" (Minecraft color codes)
                                                                                // and trim leading/trailing spaces
    }
}
