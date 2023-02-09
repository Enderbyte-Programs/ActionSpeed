package enderbyteprograms.actionspeed;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ActionSpeedData {
    public static List<String> active = new ArrayList<String>();
    public static boolean inlist(Player p) {
        return active.contains(p.getDisplayName());
    }
}
