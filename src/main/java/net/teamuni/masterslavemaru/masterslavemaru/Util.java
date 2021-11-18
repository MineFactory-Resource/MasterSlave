package net.teamuni.masterslavemaru.masterslavemaru;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
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
