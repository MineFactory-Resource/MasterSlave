package net.teamuni.masterslavemaru.managers;

import net.teamuni.masterslavemaru.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Team;

public class ListenerManager implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player deather = event.getEntity().getPlayer();
        if(killer == null) return;
        Team team = TeamManager.hasTeam(killer);
        if(team == null) return;
        TeamManager.setTeam(team, deather);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location loc = player.getLocation();
        Util.runTaskLater(() -> {
            player.spigot().respawn();
            player.teleport(loc);
        }, 1L);
        Util.runTaskLater(() -> player.teleport(player), 10L);
    }
}
