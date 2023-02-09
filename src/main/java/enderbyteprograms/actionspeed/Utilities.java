package enderbyteprograms.actionspeed;

import org.bukkit.Bukkit;

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
}
