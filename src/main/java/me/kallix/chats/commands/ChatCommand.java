package me.kallix.chats.commands;

import com.google.common.collect.Sets;
import me.kallix.chats.Chats;
import me.kallix.chats.utils.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

public abstract class ChatCommand implements CommandExecutor {

    private static final Set<ChatCommand> chatCommands = Sets.newHashSet();

    protected final Plugin plugin;
    protected final Set<UUID> cooldown = Sets.newHashSet();

    public ChatCommand(Plugin plugin) {
        this.plugin = plugin;
        chatCommands.add(this);
    }

    public static void destroy(Player player) {
        chatCommands.forEach(cmd -> cmd.cooldown.remove(player.getUniqueId()));
    }

    public static void destroy() {
        Sets.newHashSet(chatCommands).forEach(ChatCommand::dispose);
    }

    public void dispose() {
        cooldown.clear();
        chatCommands.remove(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Executors must be players!");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /" + label + " <playerName>");
            return false;
        }

        if (!Validate.isValidUser(args[0])) {
            player.sendMessage(ChatColor.RED + "Invalid username!");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage(ChatColor.RED + "Player not found (or not online)!");
            return false;
        }

        if (cooldown.contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You are in cooldown!");
            return false;
        } else {
            cooldown.add(player.getUniqueId());
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                    cooldown.remove(player.getUniqueId()), 20 * 3);
            handle(player, target);
        }

        return true;
    }

    public abstract void handle(Player sender, Player target);
}
