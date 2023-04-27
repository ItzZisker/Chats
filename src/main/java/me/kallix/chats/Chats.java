package me.kallix.chats;

import com.jeff_media.playerdataapi.PlayerDataAPI;
import lombok.Getter;
import me.kallix.chats.commands.chatcolor.ChatColorCommand;
import me.kallix.chats.commands.config.ReloadCommand;
import me.kallix.chats.commands.emotions.*;
import me.kallix.chats.data.ColorHandler;
import me.kallix.chats.data.Configuration;
import me.kallix.chats.data.PlayerDataCache;
import me.kallix.chats.hook.PlaceholdersHook;
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

    private Configuration configuration;
    private ColorHandler colorHandler;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {

        PluginManager manager = getServer().getPluginManager();

        saveDefaultConfig();
        reloadConfig();

        this.dataAPI = (PlayerDataAPI) manager.getPlugin("PlayerDataAPI");
        this.configuration = new Configuration(this.getConfig());
        this.dataCache = new PlayerDataCache(this);
        this.colorHandler = new ColorHandler(configuration);

        getCommand("uwu").setExecutor(new UwUCommand(this));
        getCommand("hug").setExecutor(new HugCommand(this));
        getCommand("kiss").setExecutor(new KissCommand(this));
        getCommand("strike").setExecutor(new StrikeCommand(this));

        PluginCommand chatcmd = getCommand("chatcolor");
        PluginCommand reloadcmd = getCommand("chatcolorreload");
        ChatColorCommand chatExec = new ChatColorCommand(colorHandler, dataCache);
        ReloadCommand reloadExec = new ReloadCommand(this);

        chatcmd.setExecutor(chatExec);
        chatcmd.setTabCompleter(chatExec);
        reloadcmd.setExecutor(reloadExec);

        manager.registerEvents(new AsyncChatListener(colorHandler, dataCache, configuration,
                manager.getPlugin("PlaceholderAPI") != null ? new PlaceholdersHook() : null), this);

        manager.registerEvents(new PlayerJoinListener(dataCache), this);
        manager.registerEvents(new PlayerQuitListener(dataCache), this);

        getServer().getOnlinePlayers().forEach(dataCache::cacheColor);
        getLogger().info("Enabled Chats v1.0 by Kallix_");
    }

    @Override
    public void onDisable() {
        EmotionCommand.destroy();

        if (dataCache != null) {
            dataCache.dispose();
        }
    }
}
