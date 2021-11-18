package net.teamuni.masterslavemaru.masterslavemaru;

import org.bukkit.plugin.java.JavaPlugin;

public final class MasterSlaveMaru extends JavaPlugin {

    private static MasterSlaveMaru instance;

    public static MasterSlaveMaru getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("masterslave").setExecutor(new CommandManager());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
