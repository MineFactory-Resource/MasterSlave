package net.teamuni.masterslavemaru.masterslavemaru;

import com.google.common.collect.Lists;
import net.teamuni.masterslavemaru.masterslavemaru.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class Util {

    public static BukkitTask runTask(Runnable runnable) {
        return Bukkit.getScheduler().runTask(MasterSlaveMaru.getInstance(), runnable);
    }

    public static BukkitTask runTaskLater(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(MasterSlaveMaru.getInstance(), runnable, delay);
    }

    public static BukkitTask runTaskTimer(Runnable runnable, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimer(MasterSlaveMaru.getInstance(), runnable, delay, ticks);
    }

    public static BukkitTask runTaskTimerAsynchronously(Runnable runnable, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(MasterSlaveMaru.getInstance(), runnable, delay, ticks);
    }

    public static int scheduleSyncDelayedTask(Runnable runnable, long delay) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(MasterSlaveMaru.getInstance(), runnable, delay);
    }

    public static BukkitTask broadcastMessage(String msg, long delay) {
        return runTaskLater(() -> Bukkit.broadcastMessage(msg), delay);
    }

    public static String toArrayString(Object[] objects) {
        return Arrays.toString(objects).replaceAll("\\[", "").replaceAll("\\]", "");
    }

    public static String[] getPlayers(Object... uuids) {
        List<Player> players = Lists.newArrayList();
        for (Object uuid : uuids) {
            players.add(Bukkit.getPlayer(UUID.fromString(uuid.toString())));
        }
        return players.stream().map(HumanEntity::getName).toArray(String[]::new);
    }

    public static void initTeam() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Team team = GameManager.board.getTeam(player.getName());
            if(team == null) continue;
            team.unregister();
        }
    }

    public static List<Player> randomPickPlayer(int amount, List<Player> players) {
        List<Player> pickPlayers = new ArrayList<>();
        for(int i=0; i < amount; i++)
        {
            int randomNumber = new Random().nextInt(players.size());
            pickPlayers.add(players.get(randomNumber));
            players.remove(randomNumber);
        }

        return pickPlayers;
    }
}