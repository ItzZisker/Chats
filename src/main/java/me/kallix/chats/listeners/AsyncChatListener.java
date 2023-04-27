package me.kallix.chats.listeners;

import lombok.RequiredArgsConstructor;
import me.kallix.chats.data.ColorHandler;
import me.kallix.chats.data.Configuration;
import me.kallix.chats.data.PlayerDataCache;
import me.kallix.chats.hook.PlaceholdersHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;

@RequiredArgsConstructor
public final class AsyncChatListener implements Listener {

    private final ColorHandler colorHandler;
    private final PlayerDataCache dataCache;
    private final Configuration configuration;
    private final PlaceholdersHook placeholdersHook;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        Optional<String> color = dataCache.getColor(player);
        String displayName = player.getDisplayName();
        String text = configuration.getFormat()
                .replace("{displayName}", color.isPresent() ? colorHandler.process(color.get(), displayName) : displayName)
                .replace("{msg}", event.getMessage());

        if (placeholdersHook != null) {
            text = ChatColor.translateAlternateColorCodes('&', placeholdersHook.setPlaceholders(player, text));
        }

        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        event.setFormat(text);
    }
}
