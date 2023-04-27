package me.kallix.chats.data;

import com.google.common.collect.Maps;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public final class ColorHandler {

    public static final String UNDEFINED = "{}";

    private final Map<String, String> colorKeys = Maps.newHashMap();
    private final Configuration config;

    public ColorHandler(Configuration config) {
        this.config = config;
        reload();
    }

    public Set<String> getKeys() {
        return colorKeys.keySet();
    }

    public Collection<String> getColorfulKeys() {
        return colorKeys.values();
    }

    public boolean hasKey(String colorKey) {
        return colorKeys.containsKey(colorKey);
    }

    public String getColorfulKey(String colorKey) {
        if (colorKey.equals(UNDEFINED)) {
            return "None";
        } else {
            return colorKeys.get(colorKey);
        }
    }

    public String process(String key, String displayName) {
        if (key.equals(UNDEFINED) || config.getColors(key) == null) {
            return displayName;
        } else {
            return process(new StringBuilder(), key, displayName).toString();
        }
    }

    private StringBuilder process(StringBuilder builder, String key, String target) {

        List<Color> colors = config.getColors(key);
        boolean back = false;
        int i = -1;

        for (char c : target.toCharArray()) {
            if (i + 1 == colors.size()) {
                back = true;
            }
            i = back ? i - 1 : i + 1;
            if (i == -1) {
                back = false;
                i = 0;
            }
            builder.append(ChatColor.of(colors.get(i)))
                    .append(c);
        }
        return builder;
    }

    public void reload() {
        colorKeys.clear();
        config.getAllColors().forEach((key, colorList) ->
                colorKeys.put(key, process(new StringBuilder(), key, key).toString()));
    }
}
