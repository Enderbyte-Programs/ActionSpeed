package enderbyteprograms.actionspeed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utilities {
    public static void HelpMenu(CommandSender sender) {
        String nadhelp = ChatColor.AQUA+"Usage: as <command> [options]\n"+ChatColor.RESET +
                "/as allowcolour <"+ChatColor.GREEN+"yes"+ChatColor.RESET+"|"+ChatColor.RED+"no"+ChatColor.RESET+">\n" +
                "    Control if the speedometer will show colours\n" +
                "/as disable|hide "+ChatColor.BLUE+"[player]"+ChatColor.RESET+"\n" +
                "    Both of these do the same thing. They hide the speedometer\n" +
                "/as enable|show "+ChatColor.BLUE+"[player]"+ChatColor.RESET+"\n" +
                "    Both of these do the same thing. They show the speedometer\n" +
                "/as setunits "+ChatColor.BLUE+"[unit]"+ChatColor.RESET+"\n" +
                "    Set the units of your speedometer to [unit]. Refer to the tab completer.\n";
        //adhelp is for admins
        String adhelp = "/as dumpdata\n" +
                "    Output all stored data, including player configurations\n" +
                "/as forceshutdown\n" +
                "    De-register event listeners and shutdown the plugin (useful in emergencies)\n" +
                "/as restart\n" +
                "    Restart the plugin after forceshutdown\n" +
                "/as reload\n" +
                "    Reload plugin configuration";
        sender.sendMessage(nadhelp);
        if (sender.hasPermission("actionspeed.admin")) {
            sender.sendMessage(adhelp);
        }
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public static List<String> comp_startswith(List<String> input, String phrase) {
        List<String> result = new ArrayList<String>();
        for (String p:input) {
            if (p.startsWith(phrase) || phrase.replace(" ","").equals("")) {
                result.add(p);
            }
        }
        return result;
    }
    public static Player GetPL(String[] strings) {
        List<Player> players = new ArrayList<Player>(ActionSpeedMain.INSTANCE.getServer().getOnlinePlayers());
        List<String> names = new ArrayList<String>();
        for (Player p: players) {
            names.add(p.getDisplayName());
        }
         if (!names.contains(strings[strings.length-1])) {
             return null;
         } else {
             return players.get(names.indexOf(strings[strings.length-1]));
         }
    }
    public static boolean commandendsinplayer(String[] strings) {
        List<Player> players = new ArrayList<Player>(ActionSpeedMain.INSTANCE.getServer().getOnlinePlayers());
        List<String> names = new ArrayList<String>();
        for (Player p: players) {
            names.add(p.getDisplayName());
        }
        return names.contains(strings[strings.length-1]);

    }
    public static ChatColor speedColour (double invalue,PlayerData dat) {
        if (!dat.allowcolour) {
            return ChatColor.WHITE;
        }
        if (invalue < 5) {
            return ChatColor.AQUA;
        } else if (invalue > 4 && invalue < 20) {
            return ChatColor.GREEN;
        } else if (invalue > 19 && invalue < 50) {
            return ChatColor.YELLOW;
        } else if (invalue > 49) {
            return ChatColor.RED;
        }
        return ChatColor.RESET;
    }
    public static double convertspeed(int converter,double m_s) {
        //converter int, 0: m/s 1: km/h 2: mi/h
        if (converter == 0) {
            return m_s;
        } else if (converter == 1) {
            return m_s * (double)3;
        } else if (converter == 2) {
            return m_s * (double)2.237;
        } else if (converter == 3) {
            return m_s * (double)60;
        } else if (converter == 4) {
            return m_s * 3.28084;
        } else if (converter == 5) {
            return m_s * 0.037;
        } else if (converter == 6) {
            return m_s * 1.94;
        }
        else {
            return m_s;
        }
    }
}
