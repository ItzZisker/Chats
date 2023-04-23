package me.kallix.chats.listeners;

import lombok.RequiredArgsConstructor;
import me.kallix.chats.commands.emotions.EmotionCommand;
import me.kallix.chats.data.Colors;
import me.kallix.chats.data.PlayerDataCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public final class PlayerQuitListener implements Listener {

    private final PlayerDataCache dataCache;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        EmotionCommand.destroy(player);
        Colors.destroy(player);

        dataCache.saveAndClearColor(player, 1200);
    }
}
