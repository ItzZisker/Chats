package me.kallix.chats;

import com.jeff_media.playerdataapi.PlayerDataAPI;
import lombok.Getter;
import me.kallix.chats.commands.chatcolor.ChatColorCommand;
import me.kallix.chats.commands.emotions.*;
import me.kallix.chats.data.Colors;
import me.kallix.chats.data.PlayerDataCache;
import me.kallix.chats.listeners.AsyncChatListener;
import me.kallix.chats.listeners.PlayerJoinListener;
import me.kallix.chats.listeners.PlayerQuitListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Chats extends JavaPlugin {

    private PlayerDataCache dataCache;
    private PlayerDataAPI dataAPI;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {

        PluginManager manager = getServer().getPluginManager();

        this.dataAPI = (PlayerDataAPI) manager.getPlugin("PlayerDataAPI");
        this.dataCache = new PlayerDataCache(this);

        getCommand("uwu").setExecutor(new UwUCommand(this));
        getCommand("hug").setExecutor(new HugCommand(this));
        getCommand("kiss").setExecutor(new KissCommand(this));
        getCommand("strike").setExecutor(new StrikeCommand(this));

        PluginCommand chatcmd = getCommand("chatcolor");
        ChatColorCommand executor = new ChatColorCommand(dataCache);

        chatcmd.setExecutor(executor);
        chatcmd.setTabCompleter(executor);

        manager.registerEvents(new AsyncChatListener(dataCache), this);
        manager.registerEvents(new PlayerQuitListener(dataCache), this);
        manager.registerEvents(new PlayerJoinListener(dataCache), this);

        getServer().getOnlinePlayers().forEach(dataCache::cacheColor);
        getLogger().info("Enabled Chats v1.0 by Kallix_");
    }

    @Override
    public void onDisable() {
        EmotionCommand.destroy();
        Colors.destroy();

        if (dataCache != null) {
            dataCache.dispose();
        }
    }
}
