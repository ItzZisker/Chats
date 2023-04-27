package me.kallix.chats.commands.chatcolor;

import lombok.RequiredArgsConstructor;
import me.kallix.chats.data.ColorHandler;
import me.kallix.chats.data.PlayerDataCache;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class ChatColorCommand implements TabExecutor {

    private final ColorHandler colorHandler;
    private final PlayerDataCache dataCache;

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
            dataCache.getColor(player).ifPresent(key ->
                    player.sendMessage(ChatColor.GRAY + "current: " + colorHandler.getColorfulKey(key)));
            help(player);
            return true;
        }

        if (!colorHandler.hasKey(args[0])) {
            player.sendMessage(ChatColor.RED + "Invalid chatcolor \"" + args[0] + "\", press tab to see all");
            return false;
        }

        dataCache.putColor(player, args[0]);
        player.sendMessage(ChatColor.GREEN + "ChatColor changed successfully!");

        return true;
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.GRAY + "use: /chatcolor <color> (press tab to see available chat colors)");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String label,
                                                @NotNull String[] args) {
        if (args.length > 0) {
            return colorHandler.getKeys()
                    .stream()
                    .filter(color -> color.startsWith(args[0]))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
