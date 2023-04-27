package me.kallix.chats.data;

import com.google.common.collect.Maps;
import com.jeff_media.playerdataapi.PlayerDataAPI;
import com.jeff_media.playerdataapi.table.VarCharTable;
import lombok.RequiredArgsConstructor;
import me.kallix.chats.Chats;
import me.kallix.chats.exceptions.PlayerDataError;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static me.kallix.chats.data.ColorHandler.UNDEFINED;

@RequiredArgsConstructor
public final class PlayerDataCache {

    private final Map<UUID, String> colors = Maps.newConcurrentMap();
    private final Map<UUID, BukkitTask> removeTimers = Maps.newHashMap();

    private final Plugin plugin;
    private final PlayerDataAPI dataAPI;

    public PlayerDataCache(Chats plugin) {
        this.plugin = plugin;
        this.dataAPI = plugin.getDataAPI();
    }

    public synchronized Optional<String> getColor(Player player) {
        return Optional.ofNullable(colors.get(player.getUniqueId()));
    }

    public void putColor(Player player, String color) {
        colors.put(player.getUniqueId(), color);
    }

    public boolean hasColor(Player player) {
        return colors.containsKey(player.getUniqueId());
    }

    public void cacheColor(Player player) {
        if (!hasColor(player)) {
            try {
                VarCharTable table = dataAPI.getProvider().getOrCreateVarCharTable("data", 9, 20);

                table.get(player.getUniqueId(), "chatcolor").whenComplete((str, thr) -> {
                    if (str != null && player.isOnline()) {
                        colors.putIfAbsent(player.getUniqueId(), str);
                    } else if (thr != null) {
                        throw new PlayerDataError(thr);
                    } else if (str == null) {
                        table.set(player.getUniqueId(), "chatcolor", UNDEFINED);
                        colors.putIfAbsent(player.getUniqueId(), UNDEFINED);
                    }
                });
            } catch (Exception e) {
                throw new PlayerDataError(e);
            }
        }
    }

    public void clearColor(Player player, int delay) {
        if (delay > 0) {
            cancelRemoveColor(player);
            removeTimers.put(player.getUniqueId(), Bukkit.getScheduler()
                    .runTaskLater(plugin, () -> colors.remove(player.getUniqueId()), delay));
        } else {
            colors.remove(player.getUniqueId());
        }
    }

    public void saveColor(Player player) {
        getColor(player).ifPresent(color -> saveColor(player.getUniqueId(), color));
    }

    public CompletableFuture<Void> saveColor(UUID uuid, String color) {
        try {
            return dataAPI.getProvider()
                    .getOrCreateVarCharTable("data")
                    .set(uuid, "chatcolor", color);
        } catch (Exception e) {
            throw new PlayerDataError(e);
        }
    }

    public void saveAndClearColor(Player player, int delay) {
        getColor(player).ifPresent(color -> {
            saveColor(player.getUniqueId(), color);
            clearColor(player, delay);
        });
    }

    public void cancelRemoveColor(Player player) {
        cancelRemoveColor(player.getUniqueId());
    }

    public void cancelRemoveColor(UUID player) {
        BukkitTask lastTimer = removeTimers.remove(player);

        if (lastTimer != null) {
            lastTimer.cancel();
        }
    }

    public void dispose() {
        Maps.newHashMap(removeTimers).forEach((uuid, color) -> cancelRemoveColor(uuid));
        removeTimers.clear();
        colors.forEach((uuid, color) -> saveColor(uuid, color)
                .orTimeout(3, TimeUnit.SECONDS)
                .join());
        colors.clear();
    }
}
