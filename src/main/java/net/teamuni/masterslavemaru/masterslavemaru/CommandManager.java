package net.teamuni.masterslavemaru.masterslavemaru;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args[0].equals("start")) {
            if (GameManager.isGameRunning()) {
                sender.sendMessage("게임이 이미 진행중이라 사용할 수 없습니다.");
            }

            GameManager task = new GameManager(5, 5);
            task.runTask(MasterSlaveMaru.getInstance());
        }


        if(args[0].equals("stop")){

        }

        if(args[0].equals("realod")){

        }

        if(args[0].equals("setspawn")){

        }

        if(args[0].equals("gamespawn")){

        }

        return false;


    }
}
