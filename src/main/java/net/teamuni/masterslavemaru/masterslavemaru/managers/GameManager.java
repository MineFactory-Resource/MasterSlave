package net.teamuni.masterslavemaru.masterslavemaru.managers;

import com.google.common.collect.Lists;
import net.teamuni.masterslavemaru.masterslavemaru.MasterSlaveMaru;
import net.teamuni.masterslavemaru.masterslavemaru.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager extends BukkitRunnable {

    public static boolean GAME_STATUS;
    public static final Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
    private static final List<BukkitTask> GAME_TASKS = new ArrayList<>();
    private static final List<TimerManager> GAME_BOSSBARS = new ArrayList<>();

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
        FileConfiguration config = MasterSlaveMaru.config;
        return (config.isSet("spawn") && config.isSet("lobby")) && (config.get("spawn") instanceof Location && config.get("lobby") instanceof Location);
    }

    public void run() {
        GAME_STATUS = true;
        Util.broadcastMessage(String.format("총 참여자 %d명", this.players.size()), 1L);
        Util.broadcastMessage("5초 뒤, 게임 월드로 이동됩니다.", 30L);
        //주인 5명 설정하기
        Util.broadcastMessage(this.amount + "명의 주인을 설정합니다.", 60L);
        TeamManager.createTeam(Util.randomPickPlayer(this.amount, Lists.newArrayList(this.players)));
        //플레이어 이동시키기, 게임 참여 등록
        Util.runTaskLater(() -> {
            for (Player p : this.players) {
                p.teleport(MasterSlaveMaru.config.getLocation("spawn"));
                p.sendMessage("경기장으로 이동되었습니다! 30초 뒤에 무적이 풀립니다!");
                p.setNoDamageTicks(30 * 20);
            }
            GAME_BOSSBARS.add(TimerManager.startTimer("§c무적 해제§f까지 남은 시간: §e%s§f분 §e%s§f초", 30, BarColor.RED, this.players.toArray(new Player[0])));
        }, 5L * 20L);
        Util.broadcastMessage("주인은 " + Util.toArrayString(Util.getPlayers(TeamManager.teams.keySet().toArray())) + "님들 입니다!", 10 * 20L);
        GAME_TASKS.add(Util.broadcastMessage("§c무적시간이 끝났습니다! 주의해주세요!", 35 * 20));
        GAME_TASKS.add(Util.runTaskLater(GameManager::stopGame, this.playTime * 20L * 60L));
        GAME_TASKS.add(Util.runTaskTimer(new SideBarManager(), 10L, 10L));
        GAME_BOSSBARS.add(TimerManager.startTimer("§f게임 종료까지 남은 시간: §e%s§f분 §e%s§f초", playTime * 60, BarColor.GREEN, this.players.toArray(new Player[0])));}

    public static void stopGame() {
        GAME_STATUS = false;
        Bukkit.broadcastMessage("게임이 종료되었습니다! 우승자는 " + Bukkit.getPlayer(UUID.fromString((String) TeamManager.teams.keySet().toArray()[0])).getName() + " 주인님 입니다!");
        Util.initTeam();
        GAME_TASKS.forEach(BukkitTask::cancel);
        GAME_BOSSBARS.forEach(timerManager -> timerManager.getTask().cancel());
        GAME_BOSSBARS.forEach(TimerManager::cancel);
        TeamManager.teams.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            player.setGlowing(false);
            player.removePotionEffect(PotionEffectType.GLOWING);
        });
    }
}