package me.kallix.chats.hook;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public final class PlaceholdersHook {

    public String setPlaceholders(Player target, String text) {
        return PlaceholderAPI.setPlaceholders(target, text);
    }
}
