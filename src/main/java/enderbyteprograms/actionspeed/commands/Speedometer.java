package enderbyteprograms.actionspeed.commands;

import enderbyteprograms.actionspeed.ActionSpeedData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speedometer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            //Do stuff
            Player p = (Player)commandSender;
            if (ActionSpeedData.inlist(p)) {
                ActionSpeedData.active.remove(p.getDisplayName());
                commandSender.sendMessage(ChatColor.GREEN+"Speedometer is now disabled");
            } else {
                ActionSpeedData.active.add(p.getDisplayName());
                commandSender.sendMessage(ChatColor.GREEN+"Speedometer is now enabled");
            }
            return true;
        } else {
            commandSender.sendMessage(ChatColor.RED+"Only players may execute this command");
            return false;
        }
    }
}
