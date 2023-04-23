package me.kallix.chats.commands.emotions;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class HugCommand extends EmotionCommand {

    public HugCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void handle(Player sender, Player target) {
        target.spawnParticle(Particle.BUBBLE_POP, target.getLocation(), 20, 4, 4, 4);
        target.sendMessage(ChatColor.WHITE + sender.getName() + ChatColor.GRAY + " gave you a " + ChatColor.BLUE + "warm hug!");
        sender.sendMessage(ChatColor.GRAY + "You've successfully " + ChatColor.BLUE + "hugged " + ChatColor.WHITE + target.getName());
    }
}
