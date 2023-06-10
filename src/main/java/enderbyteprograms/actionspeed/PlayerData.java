package enderbyteprograms.actionspeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerData {
    public String username;
    public int unit = 0;
    public String unitstr = "m/s";
    public boolean allowcolour = true;
    public boolean active = false;
    public double topspeed = 0;
    public boolean showtopspeed = false;
    public PlayerData(String name) {
        username = name;
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
        }
    }
    public HashMap<String,String> ToYML() {
        HashMap<String,String> result = new HashMap<String,String>();
        result.put("name",this.username);
        result.put("unit",String.valueOf(unit));
        result.put("unitstr",unitstr);
        result.put("active",String.valueOf(active));
        return result;
    }
}
