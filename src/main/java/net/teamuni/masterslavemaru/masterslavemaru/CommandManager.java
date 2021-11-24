package net.teamuni.masterslavemaru.masterslavemaru;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args[0].equals("start")) {
            if (GameManager.isSetting()) {
                sender.sendMessage("스폰장소와 로비장소를 먼저 설정해주세요.");
            }
            if (GameManager.isGameRunning()) {
                sender.sendMessage("게임이 이미 진행중이라 사용할 수 없습니다.");
            }
            GameManager task = new GameManager(5, 5, Lists.newArrayList(Bukkit.getOnlinePlayers()));
            task.runTask(MasterSlaveMaru.getInstance());
        }


        if(args[0].equals("stop")){
            GameManager.stopGame();
        }

        if(args[0].equals("setspawn")){
            if(sender instanceof Player) {
                Player p = (Player) sender;
                Location loc = p.getLocation();
                MasterSlaveMaru.config.set("spawn", loc);
            }
        }

        if(args[0].equals("setlobby")){
            if(sender instanceof Player) {
                Player p = (Player) sender;
                Location loc = p.getLocation();
                MasterSlaveMaru.config.set("lobby", loc);
            }
        }
        return false;
    }
}
