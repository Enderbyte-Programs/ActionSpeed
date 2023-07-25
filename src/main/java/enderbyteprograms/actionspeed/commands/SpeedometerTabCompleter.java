package enderbyteprograms.actionspeed.commands;

import enderbyteprograms.actionspeed.ActionSpeedData;
import enderbyteprograms.actionspeed.ActionSpeedMain;
import enderbyteprograms.actionspeed.PlayerData;
import enderbyteprograms.actionspeed.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeedometerTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            List<String> nl =  Arrays.asList("toggle","setunits","allowcolour","forceshutdown","restart","dumpdata","reload");
            return Utilities.comp_startswith(nl,strings[strings.length-1]);
        }
        else {
            if (Arrays.asList(strings).contains("setunits")) {
                List<String> nl = Arrays.asList("m/s","km/h","mi/h","m/min","ft/s","mi/min","knots");
                return Utilities.comp_startswith(nl,strings[strings.length-1]);
            } else if (Arrays.asList(strings).contains("allowcolour")) {
                List<String> nl = Arrays.asList("yes", "no");
                return Utilities.comp_startswith(nl, strings[strings.length - 1]);

            } else {
                List<String> nl = Arrays.asList("toggle","setunits","allowcolour","forceshutdown","restart","dumpdata","reload");
                return Utilities.comp_startswith(nl,strings[strings.length-1]);
            }
        }
    }
}
