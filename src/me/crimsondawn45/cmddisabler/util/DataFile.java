package me.crimsondawn45.cmddisabler.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DataFile {

    private String name;
    private JavaPlugin plugin;
    private FileConfiguration configData = null;
    private File configFile= null;

    public DataFile(String name, JavaPlugin plugin) {
        this.name = name;
        this.plugin = plugin;
        reload();
    }
    
    public void reload() {
        if(this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), this.name + ".yml");
        }
        this.configData = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public FileConfiguration getConfig() {
        //Reload if there are changes
        if(this.configData != YamlConfiguration.loadConfiguration(this.configFile)) {
            reload();
        }
        return this.configData;
    }

    public void save(FileConfiguration config) {
        try {
            config.save(this.configFile);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "\"" + this.name +".yml\" failed to save!", e);
        }
    }

    public String getColoredString(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path).trim());
    }
}
