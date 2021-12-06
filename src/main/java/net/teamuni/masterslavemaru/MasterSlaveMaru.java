package net.teamuni.masterslavemaru;

import net.teamuni.masterslavemaru.managers.CommandManager;
import net.teamuni.masterslavemaru.managers.ListenerManager;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.Iterator;

public final class MasterSlaveMaru extends JavaPlugin {

    private static MasterSlaveMaru instance;
    public static FileConfiguration config;

    public static MasterSlaveMaru getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        config = this.getConfig();
        getCommand("masterslave").setExecutor(new CommandManager());
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ListenerManager(), this);
    }

    @Override
    public void onDisable() {
        for (Iterator<KeyedBossBar> it = getServer().getBossBars(); it.hasNext(); ) {
            BossBar bossBar = it.next();
            bossBar.removeAll();
        }
        for (Player player : getServer().getOnlinePlayers()) {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        }
    }
}