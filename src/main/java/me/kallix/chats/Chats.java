package me.kallix.chats;

import me.kallix.chats.commands.*;
import me.kallix.chats.listeners.AsyncChatListener;
import me.kallix.chats.listeners.PlayerQuitListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Chats extends JavaPlugin {

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onEnable() {

        getCommand("uwu").setExecutor(new UwUCommand(this));
        getCommand("hug").setExecutor(new HugCommand(this));
        getCommand("kiss").setExecutor(new KissCommand(this));
        getCommand("strike").setExecutor(new StrikeCommand(this));

        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new AsyncChatListener(), this);
        manager.registerEvents(new PlayerQuitListener(), this);

        getLogger().info("Enabled Chats v1.0 by Kallix_");
    }

    @Override
    public void onDisable() {
        ChatCommand.destroy();
    }
}
