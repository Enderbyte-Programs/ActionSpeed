package enderbyteprograms.actionspeed;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionSpeedData {

    public static List<PlayerData> active = new ArrayList<PlayerData>();
    public static boolean isregistered = false;
    public static boolean inlist(Player pl) {
        for (PlayerData p:active) {
            if (p.username.equals(pl.getDisplayName())) {
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
    public static String version = "1.5.0";
}
