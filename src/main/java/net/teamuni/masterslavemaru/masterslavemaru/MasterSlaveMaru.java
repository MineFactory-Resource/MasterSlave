package net.teamuni.masterslavemaru.masterslavemaru;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class MasterSlaveMaru extends JavaPlugin {

    private static MasterSlaveMaru instance;
    public static FileConfiguration config;

    public static MasterSlaveMaru getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.config = this.getConfig();
        getCommand("masterslave").setExecutor(new CommandManager());
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
