package me.kallix.chats.commands.config;

import lombok.RequiredArgsConstructor;
import me.kallix.chats.Chats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class ReloadCommand implements CommandExecutor {

    private final @NotNull Chats plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        try {
            plugin.reloadConfig();
            plugin.getConfiguration().reloadConfig(plugin.getConfig());
            plugin.getColorHandler().reload();
            sender.sendMessage(ChatColor.GREEN + "Successfully reloaded configuration values!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "An error occurred during the configuration reloading process.");
            return false;
        }
    }
}
