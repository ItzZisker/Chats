package me.kallix.chats.commands.emotions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class StrikeCommand extends EmotionCommand {

    public StrikeCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void handle(Player sender, Player target) {
        target.getWorld().strikeLightning(target.getLocation());
        target.sendMessage(ChatColor.WHITE + sender.getName() + ChatColor.DARK_GRAY + " STROKE you!");
        sender.sendMessage(ChatColor.GRAY + "You've successfully " + ChatColor.DARK_GRAY + "STROKE " + ChatColor.WHITE + target.getName());
    }
}
