package enderbyteprograms.actionspeed.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeedometerTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            return List.of(new String[]{"toggle","setunits","allowcolour","forceshutdown","restart","dumpdata"});
        }
        else {
            if (Arrays.stream(strings).toList().contains("setunits")) {
                return List.of(new String[]{"m/s","km/h","mi/h","m/min","ft/s","mi/min"});
            } else if (Arrays.stream(strings).toList().contains("allowcolour")) {
                return List.of(new String[]{"yes","no"});
            } else {
                return List.of(new String[]{"toggle","setunits","allowcolour","forceshutdown","restart","dumpdata"});
            }
        }
    }
}
