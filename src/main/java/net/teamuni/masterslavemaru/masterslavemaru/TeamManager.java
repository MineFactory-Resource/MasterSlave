package net.teamuni.masterslavemaru.masterslavemaru

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeamManager implements Listener {

    public static Map<Team, UUID> teamMap;
    public static List<Team> teams = new ArrayList<Team>();
    public static void createTeam(List<Player> players){
        for(Player player: players)
        {
            Team team = GameManager.board.registerNewTeam(player.getDisplayName());
            team.setAllowFriendlyFire(false);
            team.addPlayer(player);
            teamMap.put(team, player.getUniqueId());
            teams.add(team);

        }
    }

    public static boolean isTeam(Player player) {
        return teamMap.containsValue(player);
    }

    public static Team hasTeam(Player player) {
        for (Team team : teams) {
            if (team.getEntries().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public static void setTeam(Team targetTeam, Player player){
        Team team = TeamManager.hasTeam(player);\
        if(!(team ==null)){
            team.removePlayer(player);
            targetTeam.addPlayer(player);
            return;
        }
        targetTeam.addPlayer(player);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player deather = event.getEntity().getPlayer();
        TeamManager.setTeam(TeamManager.hasTeam(killer), deather);

    }
}