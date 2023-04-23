package me.kallix.chats.listeners;

import lombok.RequiredArgsConstructor;
import me.kallix.chats.data.Colors;
import me.kallix.chats.data.PlayerDataCache;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public final class AsyncChatListener implements Listener {

    private final PlayerDataCache dataCache;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        event.setFormat(dataCache.getColor(event.getPlayer())
                .orElse(Colors.GRAY)
                .process(event.getPlayer().getDisplayName()) + ": " +
                ChatColor.RESET + ChatColor.WHITE + event.getMessage());
    }
}
