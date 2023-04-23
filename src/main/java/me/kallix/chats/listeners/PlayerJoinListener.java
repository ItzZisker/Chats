package me.kallix.chats.listeners;

import lombok.RequiredArgsConstructor;
import me.kallix.chats.data.PlayerDataCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public final class PlayerJoinListener implements Listener {

    private final PlayerDataCache dataCache;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        dataCache.cancelRemoveColor(player);
        dataCache.cacheColor(player);
    }
}
