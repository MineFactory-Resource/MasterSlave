package net.teamuni.masterslavemaru.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public class SideBarManager extends BukkitRunnable {

    public void run() {
        try {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective score = board.registerNewObjective("Master Slave", "sc", "§c");
            score.setDisplayName("§7§lMaster Slave");
            score.setDisplaySlot(DisplaySlot.SIDEBAR);
            int size = TeamManager.teams.size() + 2;
            score.getScore("§e").setScore(size + 1);
            for (String master_uuid : TeamManager.teams.keySet()) {
                OfflinePlayer master = Bukkit.getPlayer(UUID.fromString(master_uuid));
                if (master == null) master = Bukkit.getOfflinePlayer(UUID.fromString(master_uuid));
                score.getScore("§c주인 이름: §f" + master.getName() + "(" + (TeamManager.hasTeam(master.getPlayer()).getPlayers().size() - 1) + "명 소유)").setScore(size);
                size--;
            }
            int slave_count = 0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if(TeamManager.teams.containsKey(player.getUniqueId().toString())) continue;
                if(TeamManager.teams.values().stream().anyMatch(team -> team.getPlayers().contains(player))) continue;
                slave_count++;
            }
            score.getScore("§d").setScore(size + 1);
            score.getScore("남은 노예: " + slave_count + "명").setScore(size);
            score.getScore("§f").setScore(0);
            Bukkit.getServer().getOnlinePlayers().forEach(player -> player.setScoreboard(board));
        } catch (Exception e) {
            e.printStackTrace();
            cancel();
        }
    }
}