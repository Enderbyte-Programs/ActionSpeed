package net.enderbyteprograms.actionspeed;
import net.enderbyteprograms.epdb.Table;
import org.bukkit.Location;

import java.util.HashMap;

public class ActionSpeedData {

    public static HashMap<String,PlayerData> Players = new HashMap<>();//This dict keeps track of players and their player data
    //public static HashMap<String, Location> Playerlocs = new HashMap<>();
    public static String version = "1.9.1";
    public static Table MainDataTable;
    public static double SampleRate = 10;//Hertz
    public static long TickRate = 2;
}
