package me.kallix.chats.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Getter
public final class Configuration {

    private final Map<String, List<Color>> allColors = Maps.newHashMap();
    private String format;

    public Configuration(FileConfiguration config) {
        reloadConfig(config);
    }

    public void reloadConfig(FileConfiguration config) {

        format = config.getString("format", "{displayName}: " + ChatColor.RESET + ChatColor.WHITE + "{msg}");

        ConfigurationSection colorsSec = config.getConfigurationSection("colors");
        Map<String, List<Color>> newColors = Maps.newHashMap();

        if (colorsSec != null) {
            colorsSec.getKeys(false).forEach(key -> {
                List<Color> list = Lists.newArrayList();

                colorsSec.getStringList(key).forEach(raw -> {
                    String[] split = raw.split(":");

                    list.add(new Color(Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]),
                            Integer.parseInt(split[2])));
                });
                newColors.put(key, list);
            });
        }

        if (!newColors.isEmpty()) {
            allColors.clear();
            allColors.putAll(newColors);
        }
    }

    public List<Color> getColors(String key) {
        return allColors.get(key);
    }
}
