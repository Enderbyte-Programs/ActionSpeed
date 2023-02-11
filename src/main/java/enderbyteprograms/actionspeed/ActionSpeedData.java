package enderbyteprograms.actionspeed;

import enderbyteprograms.actionspeed.files.ConfigFile;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionSpeedData {
    public static List<PlayerData> active = new ArrayList<PlayerData>();
    public static boolean inlist(Player pl) {
        for (PlayerData p:active) {
            if (p.username.equals(pl.getDisplayName())) {
                return true;
            }
        }
        return false;
    }
    public static boolean PlayerIsActive(String user) {
        for (PlayerData p:active) {
            if (p.username.equals(user) && p.active) {
                return true;
            }
        }
        return false;
    }
    public static void DestroyPlayer(String user) {
        int ix = 0;
        int ir = 0;
        for (PlayerData p:active) {
            if (p.username.equals(user)) {
                ir = ix;
            }
            ix++;
        }
        active.remove(ir);
    }
    public static int getpdata(String user) {
        int ix = 0;
        for (PlayerData p:active) {
            if (p.username.equals(user)) {
                return ix;
            }
            ix++;
        }
        return -1;
    }
    public static ConfigFile fmake = new ConfigFile("asconfig.txt");
    public static HashMap<String,Object> CONFIG = new HashMap<String,Object>();
}
