package net.teamuni.masterslavemaru.masterslavemaru;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class GameManager extends BukkitRunnable{

    public static boolean GAME_STATUS;
    public static BukkitRunnable GAME_TASK;
    public static final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

    private final int amount;
    private final int playTime;
    private final List<Player> players;

    public GameManager(int amount, int playTime, List<Player> players) {
        this.amount = amount;
        this.playTime = playTime;
        this.players = players;
    }

    public static boolean isGameRunning() {
        return GAME_STATUS;
    }

    public static boolean isSetting() {
        return MasterSlaveMaru.config.isSet("spawn") && MasterSlaveMaru.config.isSet("setspawn");
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage(String.format("총 참여자 %d명", this.players.size()));
        Bukkit.broadcastMessage("5초 뒤, 게임 월드로 이동됩니다.");

        //주인 5명 설정하기
        Bukkit.broadcastMessage("5명의 주인을 설정합니다.");
        TeamManager.createTeam(Util.randomPickPlayer(5, this.players));

        //플레이어 이동시키기, 게임 참여 등록
        Bukkit.getScheduler().runTaskLater(MasterSlaveMaru.getInstance(), () -> {
            for (Player p : this.players) {
                p.teleport(MasterSlaveMaru.config.getLocation("spawn"));
                p.sendMessage("경기장으로 이동되었습니다! 30초 뒤에 무적이 풀립니다!");
                p.setNoDamageTicks(30 * 20);
            }
        }, 5 * 20);

    }

    public static void stopGame() {
        GAME_TASK.cancel();
        GAME_STATUS = false;
    }

}


