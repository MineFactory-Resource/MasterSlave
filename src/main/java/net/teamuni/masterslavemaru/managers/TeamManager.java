package net.teamuni.masterslavemaru.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

@SuppressWarnings("deprecation")
public class TeamManager {

    private static Iterator<ChatColor> team_colors = Arrays.asList(ChatColor.DARK_RED,
            ChatColor.RED,
            ChatColor.YELLOW,
            ChatColor.GREEN,
            ChatColor.BLUE,
            ChatColor.LIGHT_PURPLE).listIterator();

    public static HashMap<String, Team> teams = new HashMap<>(); //UUID(주인), 노예팀(주인 포함)

    static void init() {
        team_colors = Arrays.asList(ChatColor.DARK_RED,
                ChatColor.RED,
                ChatColor.YELLOW,
                ChatColor.GREEN,
                ChatColor.BLUE,
                ChatColor.LIGHT_PURPLE).listIterator();
    }

    public static void createTeam(List<Player> players) {
        init();
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Player player : players) {
            ChatColor color = team_colors.next();
            Team team = scoreboard.registerNewTeam(player.getName());
//            team.addEntry(player.getName());
            team.addPlayer(player);
            team.setAllowFriendlyFire(false);
            team.setColor(color);
//            player.setGlowing(true);
            player.setScoreboard(scoreboard);
            teams.put(player.getUniqueId().toString(), team);
        }
    }

    public static Team hasTeam(Player player) {
        Team team = teams.get(player.getUniqueId().toString());
        return team == null ? teams.values().stream().filter(t -> t.getPlayers().contains(player)).findFirst().orElse(null) : team;
    }

    public static void setTeam(Team targetTeam, Player player) {
        Team team = TeamManager.hasTeam(player);
        if (teams.containsKey(player.getUniqueId().toString())) { //주인일 경우 그 주인의 모든 노예가 주인을 죽인 주인의 팀으로 흡수됨.
            Team prov = teams.get(player.getUniqueId().toString());
            Set<OfflinePlayer> players = prov.getPlayers();
            for (OfflinePlayer p : players) {
                prov.removePlayer(p);
                targetTeam.addPlayer(p);
            }
            prov.unregister();
            teams.remove(player.getUniqueId().toString());
            if (teams.keySet().size() <= 1) GameManager.stopGame();
            return;
        } else if (team != null) {
            team.removePlayer(player);
            targetTeam.addPlayer(player);
            return;
        }
        targetTeam.addPlayer(player);
    }
}