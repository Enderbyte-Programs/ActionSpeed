package enderbyteprograms.actionspeed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static boolean playeronline(String name) {
        if (Bukkit.getServer().getPlayer(name) != null && Bukkit.getServer().getPlayer(name).getDisplayName().equalsIgnoreCase(name)){
            return true;
        }
        return false;
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
    public static ChatColor speedColour (double invalue) {
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
