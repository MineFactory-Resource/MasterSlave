package net.teamuni.masterslavemaru.masterslavemaru.managers;

import com.google.common.collect.Lists;
import net.teamuni.masterslavemaru.masterslavemaru.MasterSlaveMaru;
import net.teamuni.masterslavemaru.masterslavemaru.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.isOp()) return false;
        if(args.length <= 0) {
            sender.sendMessage("/ms start <주인 수>");
            sender.sendMessage("/ms stop");
            sender.sendMessage("/ms setspawn");
            sender.sendMessage("/ms setlobby");
            return false;
        }
        else if(args[0].equals("test")) {

            return false;
        }
        else if(args[0].equals("start")) {
            if (!GameManager.isSetting()) {
                sender.sendMessage("스폰장소와 로비장소를 먼저 설정해주세요.");
                return false;
            }
            if (GameManager.isGameRunning()) {
                sender.sendMessage("게임이 이미 진행중이라 사용할 수 없습니다.");
                return false;
            }
            if(args.length <= 1) {
                sender.sendMessage("주인수를 설정해주세요!");
                return false;
            }
            try {
                if(Integer.parseInt(args[1]) <= 1) {
                    sender.sendMessage("1보다 작은 수는 설정할 수 없습니다!");
                    return false;
                }
            } catch (NumberFormatException e) {
                sender.sendMessage("숫자를 입력해주세요.");
                return false;
            }
            Bukkit.getOnlinePlayers().forEach(player -> player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR));
            ArrayList<Player> playerList = Lists.newArrayList(Bukkit.getOnlinePlayers());
            playerList.forEach(p -> p.setGlowing(false));
            Util.initTeam();
            GameManager task = new GameManager(Integer.parseInt(args[1]), 5, playerList);
            task.runTask(MasterSlaveMaru.getInstance());
            return false;
        }

        else if(args[0].equals("stop")){
            if (!GameManager.isGameRunning()) {
                sender.sendMessage("게임이 시작되지 않았습니다.");
                return false;
            }
            GameManager.stopGame();
            return false;
        }

        else if(args[0].equals("setspawn")){
            if(sender instanceof Player) {
                Player p = (Player) sender;
                Location loc = p.getLocation();
                MasterSlaveMaru.config.set("spawn", loc);
                MasterSlaveMaru.getInstance().saveConfig();
            }
            return false;
        }

        else if(args[0].equals("setlobby")){
            if(sender instanceof Player) {
                Player p = (Player) sender;
                Location loc = p.getLocation();
                MasterSlaveMaru.config.set("lobby", loc);
                MasterSlaveMaru.getInstance().saveConfig();
            }
            return false;
        }
        return false;
    }
}
