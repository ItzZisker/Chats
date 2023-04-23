package me.kallix.chats.data;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.kallix.chats.utils.Pair;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum Colors {

    RED_GRADIENT(Arrays.asList(
            new Color(255, 51, 51),
            new Color(255, 0, 0),
            new Color(204, 0, 0),
            new Color(153, 0, 0),
            new Color(102, 0, 0),
            new Color(80, 0, 0)
    ), ChatColor.RED),

    BLUE_GRADIENT(Arrays.asList(
            new Color(102, 178, 255),
            new Color(51, 153, 255),
            new Color(0, 128, 255),
            new Color(0, 102, 204),
            new Color(102, 102, 255),
            new Color(51, 51, 255),
            new Color(0, 0, 255),
            new Color(0, 0, 153),
            new Color(0, 0, 102)
    ), ChatColor.BLUE),

    GREEN_GRADIENT(Arrays.asList(
            new Color(0, 255, 0),
            new Color(0, 204, 0),
            new Color(0, 153, 0),
            new Color(0, 130, 0),
            new Color(0, 121, 0),
            new Color(0, 80, 0),
            new Color(0, 51, 0)
    ), ChatColor.GREEN),

    ORANGE_GRADIENT(Arrays.asList(
            new Color(255, 153, 51),
            new Color(255, 128, 0),
            new Color(204, 102, 0),
            new Color(153, 76, 0),
            new Color(102, 51, 0)
    ), ChatColor.GOLD),

    GRAY_GRADIENT(Arrays.asList(
            new Color(128, 128, 128),
            new Color(96, 96, 96),
            new Color(70, 70, 70),
            new Color(60, 60, 60),
            new Color(40, 40, 40),
            new Color(30, 30, 30)
    ), ChatColor.DARK_GRAY),

    RED(Collections.singletonList(Color.RED), ChatColor.RED),
    BLUE(Collections.singletonList(Color.BLUE), ChatColor.BLUE),
    LIME(Collections.singletonList(Color.GREEN), ChatColor.GREEN),
    GREEN(Collections.singletonList(new Color(0, 102, 0)), ChatColor.DARK_GREEN),
    AQUA(Collections.singletonList(Color.CYAN), ChatColor.AQUA),
    YELLOW(Collections.singletonList(Color.YELLOW), ChatColor.YELLOW),
    WHITE(Collections.singletonList(Color.WHITE), ChatColor.WHITE),
    GRAY(Collections.singletonList(Color.GRAY), ChatColor.DARK_GRAY),
    BLACK(Collections.singletonList(Color.BLACK), ChatColor.BLACK);

    private static final Map<String, Pair<Colors, String>> cache = Maps.newConcurrentMap();
    private final List<java.awt.Color> colors;

    @Getter
    private final ChatColor chatColor;

    public String process(String displayName) {

        Pair<Colors, String> pair = cache.get(displayName);

        if (pair != null && pair.key() == this) {
            return pair.value();
        }

        String result;

        if (name().endsWith("_GRADIENT")) {
            StringBuilder builder = new StringBuilder();

            boolean back = false;
            int i = -1;

            for (char c : displayName.toCharArray()) {
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
            result = builder.toString();
        } else {
            result = ChatColor.of(colors.get(0)) + displayName;
        }

        cache.putIfAbsent(displayName, new Pair<>(this, result));

        return result;
    }

    public static void destroy(Player player) {
        cache.remove(player.getDisplayName());
    }

    public static void destroy() {
        cache.clear();
    }
}
