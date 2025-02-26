package enderbyteprograms.actionspeed;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;

public class PlayerData {
    public String username;
    public int unit = 0;
    public String unitstr = "m/s";
    public boolean allowcolour = true;
    public Location lastMoveLocation = null;//Purpose of this: If the user hasn't moved physically and
    public boolean active = ActionSpeedMain.CONFIG.getBoolean("onbydefault");

    public PlayerData(String name) {
        username = name;
    }
    public PlayerData(String name,int iunit,boolean iactive, boolean icolour) {
        //Constructor method for use with v1.4
        username = name;
        unit = iunit;
        active = iactive;
        allowcolour = icolour;
        UpdateUnits();
    }
    public void UpdateUnits() {
        if (unit == 0) {
            unitstr = "m/s";
        } else if (unit == 1) {
            unitstr = "km/h";
        } else if (unit==2) {
            unitstr = "mi/h";
        } else if (unit==3) {
            unitstr = "m/min";
        } else if (unit==4) {
            unitstr = "ft/s";
        } else if (unit == 5) {
            unitstr = "mi/min";
        } else if (unit == 6) {
            unitstr = "kt";
        } else if (unit == 7) {
            unitstr = "mach";
        } else if (unit == 8) {
            unitstr = "kJ";
        }
    }

    public void SendActionBarMessage(String message) {
        Bukkit.getPlayer(this.username).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
