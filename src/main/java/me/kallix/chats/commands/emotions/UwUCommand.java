package me.kallix.chats.commands.emotions;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class UwUCommand extends EmotionCommand {

    public UwUCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void handle(Player sender, Player target) {
        target.spawnParticle(Particle.PORTAL, target.getLocation(), 20, 4, 4, 4);
        target.sendMessage(ChatColor.WHITE + sender.getName() + ChatColor.GRAY + " sent you an " + ChatColor.DARK_PURPLE + "UwU!");
        sender.sendMessage(ChatColor.GRAY + "You've successfully sent an " + ChatColor.DARK_PURPLE + "UwU " + ChatColor.GRAY + "to " + ChatColor.WHITE + target.getName());
    }
}
