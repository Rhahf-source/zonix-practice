package us.zonix.practice.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigFile {
    private File file;
    private YamlConfiguration configuration;

    public ConfigFile(JavaPlugin plugin, String name) {
        this.file = new File(plugin.getDataFolder(), name + ".yml");
        if (!this.file.getParentFile().exists()) {
            this.file.getParentFile().mkdir();
        }

        plugin.saveResource(name + ".yml", false);
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public double getDouble(String path) {
        return this.configuration.contains(path) ? this.configuration.getDouble(path) : 0.0;
    }

    public int getInt(String path) {
        return this.configuration.contains(path) ? this.configuration.getInt(path) : 0;
    }

    public boolean getBoolean(String path) {
        return this.configuration.contains(path) ? this.configuration.getBoolean(path) : false;
    }

    public String getString(String path) {
        return this.configuration.contains(path) ? ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path)) : "ERROR: STRING NOT FOUND";
    }

    public String getString(String path, String callback, boolean colorize) {
        if (this.configuration.contains(path)) {
            return colorize ? ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path)) : this.configuration.getString(path);
        } else {
            return callback;
        }
    }

    public List<String> getReversedStringList(String path) {
        List<String> list = this.getStringList(path);
        if (list == null) {
            return Arrays.asList("ERROR: STRING LIST NOT FOUND!");
        } else {
            int size = list.size();
            List<String> toReturn = new ArrayList<>();

            for (int i = size - 1; i >= 0; i--) {
                toReturn.add(list.get(i));
            }

            return toReturn;
        }
    }

    public List<String> getStringList(String path) {
        if (!this.configuration.contains(path)) {
            return Arrays.asList("ERROR: STRING LIST NOT FOUND!");
        } else {
            ArrayList<String> strings = new ArrayList<>();

            for (String string : this.configuration.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }

            return strings;
        }
    }

    public List<String> getStringListOrDefault(String path, List<String> toReturn) {
        if (!this.configuration.contains(path)) {
            return toReturn;
        } else {
            ArrayList<String> strings = new ArrayList<>();

            for (String string : this.configuration.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }

            return strings;
        }
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }
}
