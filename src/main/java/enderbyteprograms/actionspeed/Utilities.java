package enderbyteprograms.actionspeed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
}
