package enderbyteprograms.actionspeed.commands;

import enderbyteprograms.actionspeed.ActionSpeedData;
import enderbyteprograms.actionspeed.ActionSpeedMain;
import enderbyteprograms.actionspeed.PlayerData;
import enderbyteprograms.actionspeed.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeedometerTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> validplexc = Arrays.asList("enable","disable","show","hide");
        List<String> validplexc2 = Arrays.asList("setunits","allowcolour");
        if (strings.length == 1) {
            List<String> nl =  Arrays.asList("enable","disable","setunits","allowcolour","forceshutdown","restart","dumpdata","reload","show","hide","help");
            return Utilities.comp_startswith(nl,strings[strings.length-1]);
        }
        else {
            if ((strings.length > 1 && validplexc.contains(strings[strings.length-2])) || (strings.length > 2 && validplexc2.contains((strings[strings.length-3])))) {
                List<Player> list = new ArrayList<>(ActionSpeedMain.INSTANCE.getServer().getOnlinePlayers());
                List<String> nl = new ArrayList<String>();
                for (Player p : list) {
                    nl.add(p.getDisplayName());
                }
                return Utilities.comp_startswith(nl,strings[strings.length-1]);
            }
            if (Arrays.asList(strings).contains("setunits")) {

                List<String> nl = Arrays.asList("m/s", "km/h", "mi/h", "m/min", "ft/s", "mi/min", "knots");

                return Utilities.comp_startswith(nl,strings[strings.length-1]);
            } else if (Arrays.asList(strings).contains("allowcolour")) {
                List<String> nl = Arrays.asList("yes", "no");
                return Utilities.comp_startswith(nl, strings[strings.length - 1]);

            } else {
                List<Player> list = new ArrayList<>(ActionSpeedMain.INSTANCE.getServer().getOnlinePlayers());
                List<String> nl = new ArrayList<String>();
                for (Player p : list) {
                    nl.add(p.getDisplayName());
                }
                return Utilities.comp_startswith(nl,strings[strings.length-1]);
            }
        }
    }
}
