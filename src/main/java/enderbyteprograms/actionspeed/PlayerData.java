package enderbyteprograms.actionspeed;

public class PlayerData {
    public String username;
    public int unit = 0;
    public String unitstr = "m/s";
    public boolean allowcolour = true;
    public boolean active = false;
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
        }
    }
}
