package me.kallix.chats.commands.chatcolor;

import lombok.RequiredArgsConstructor;
import me.kallix.chats.data.Colors;
import me.kallix.chats.data.PlayerDataCache;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public final class ChatColorCommand implements TabExecutor {

    private final PlayerDataCache dataCache;
    private final List<String> colors = Arrays.stream(Colors.values())
            .map(Colors::name)
            .toList();

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players should execute this command!");
            return false;
        }

        if (args.length == 0) {
            dataCache.getColor(player).ifPresent(color -> player.sendMessage(ChatColor.GRAY + "current: " + color));
            help(player);
            return true;
        }

        return false;
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.GRAY + "use: /chatcolor <color> (press tab to see available chat colors)");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        return colors;
    }
}
