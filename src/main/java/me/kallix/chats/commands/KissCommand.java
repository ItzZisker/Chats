package me.kallix.chats.commands;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class KissCommand extends ChatCommand {

    public KissCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void handle(Player sender, Player target) {
        target.spawnParticle(Particle.PORTAL, target.getLocation(), 20, 4, 4, 4);
        target.sendMessage(ChatColor.WHITE + sender.getName() + ChatColor.DARK_PURPLE + " kissed you!");
        sender.sendMessage(ChatColor.GRAY + "You've successfully " + ChatColor.DARK_PURPLE + "kissed " + ChatColor.WHITE + target.getName());
    }
}
