package dev.mrbutters.me.slimeindicator;

import dev.mrbutters.me.slimeindicator.commands.SlimeChunkCheck;
import org.bukkit.plugin.java.JavaPlugin;

public final class SlimeIndicator extends JavaPlugin {

    public static SlimeIndicator instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Commands
        getCommand("slimechunkcheck").setExecutor(new SlimeChunkCheck());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SlimeIndicator getInstance() {
        return instance;
    }
}
