package me.kallix.chats.listeners;

import me.kallix.chats.commands.ChatCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ChatCommand.destroy(event.getPlayer());
    }
}
