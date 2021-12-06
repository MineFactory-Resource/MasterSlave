package net.teamuni.masterslavemaru.managers;

import net.teamuni.masterslavemaru.Util;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TimerManager extends BukkitRunnable {

    public static TimerManager startTimer(String title, float time, BarColor color, Player... players) {
        TimerManager manager = new TimerManager(title, time, color, players);
        manager.setTask(Util.runTaskTimerAsynchronously(manager, 20, 20));
        Util.runTaskLater(manager::cancel, (long) time * 20L);
        return manager;
    }

    private final BossBar bossBar;
    private final String title;
    private final float max;
    private float tmp; //seconds

    private BukkitTask task;

    public TimerManager(String title, float time, BarColor color, Player... players) {
        this.bossBar = Bukkit.createBossBar(title, color, BarStyle.SOLID, BarFlag.values());
        this.tmp = time;
        this.max = time;
        this.title = title;
        this.bossBar.setProgress(1);
        for (Player player : players) {
            this.bossBar.addPlayer(player);
        }
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void run() {
        if(tmp <= 0) {
            this.task.cancel();
            this.bossBar.removeAll();
            this.bossBar.setVisible(false);
            this.bossBar.hide();
            return;
        }
        tmp--;
        this.bossBar.setTitle(String.format(this.title, (int) tmp / 60, (int) tmp % 60));
        double progress = (double) this.tmp / 10.0D / (double) this.max * 10.0D;
        this.bossBar.setProgress(progress);
    }

    public void cancel() {
        this.task.cancel();
        this.bossBar.removeAll();
        this.bossBar.setVisible(false);
        this.bossBar.hide();
    }

    public BossBar getBossBar() {
        return bossBar;
    }
}
