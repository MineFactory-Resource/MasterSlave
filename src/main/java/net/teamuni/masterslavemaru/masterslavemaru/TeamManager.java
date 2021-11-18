package net.teamuni.masterslavemaru.masterslavemaru

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    private String name;
    private List<Player> players;
    public static List<Team> teams = new ArrayList<Team>();

    public TeamManager(Player player) {
        this.name = player.getDisplayName();
        this.players = new ArrayList<Player>();
        Team team = GameManager.board.registerNewTeam(players.get(0).getDisplayName());
        team.setAllowFriendlyFire(false);

        if (getTeam(name) == null) {
            teams.add((Team) this);
        }
    }

    public String getName() {
        return this.name;
    }


    public List<Player> getPlayers() {
        return this.players;
    }

    public static List<Team> createTeam(List<Player> players){
        List<Team> teams = null;
        for(Player p: players) {
            teams.add((Team) new TeamManager(p));
        }
    }

    public static boolean hasTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return true;
            }
        }
        return false;
    }

    public static Team getTeam(Player player) {
        if (hasTeam(player)) {
            for (Team team : teams) {
                if (team.getPlayers().contains(player)) {
                    return team;
                }
            }
        }
        return null;
    }

    public static Team getTeam(String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        String s = player.getName();
        if (s.length() >= 15) {
            s = player.getName().substring(0, player.getName().length() - 2);
        }
        player.setRemoveWhenFarAway(false);
        GameManager.board.getTeam(this.name).addPlayer(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        String s = player.getName();
        if (s.length() >= 15) {
            s = player.getName().substring(0, player.getName().length() - 2);
        }
        player.setDisplayName(player.getName());
        GameManager.board.getTeam(this.name).removePlayer(player);
    }
}