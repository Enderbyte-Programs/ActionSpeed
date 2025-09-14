package net.enderbyteprograms.actionspeed;

import net.enderbyteprograms.actionspeed.commands.Speedometer;
import net.enderbyteprograms.actionspeed.commands.SpeedometerTabCompleter;
import net.enderbyteprograms.epdb.DataTypes;
import net.enderbyteprograms.epdb.EPDatabase;
import net.enderbyteprograms.epdb.Table;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.enderbyteprograms.actionspeed.bstats.Metrics;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionSpeedMain extends JavaPlugin {
    public static ActionSpeedMain INSTANCE;
    public static EventManager LISTENER;
    public static FileConfiguration CONFIG;
    public BukkitRunnable MAINLOOP;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getCommand("speedometer").setExecutor(new Speedometer());
        this.getCommand("speedometer").setTabCompleter(new SpeedometerTabCompleter());
        LISTENER = new EventManager();
        this.getServer().getPluginManager().registerEvents(LISTENER,this);
        //Set up configs
        this.saveDefaultConfig(); //APparently this won't overwrite again
        CONFIG = this.getConfig();//Reduce load on Event calls NOTE: Directly call config on write operations
        if (!CONFIG.contains("onbydefault",true)) {
            this.getConfig().set("onbydefault",true);
        }
        if (!CONFIG.contains("showzeros",true)) {
            this.getConfig().set("showzeros",true);
        }
        if (!CONFIG.contains("sample-rate",true)) {
            this.getConfig().set("sample-rate",10);

        }
        this.saveConfig();
        CONFIG = this.getConfig();
        //Load sample rate
        double rawsamplerate = CONFIG.getDouble("sample-rate");
        if (rawsamplerate > 20) {
            this.getLogger().warning("The maximum sample rate is 20hz.");
            rawsamplerate = 20;
        }
        double tickcount = 20D/rawsamplerate;
        long newtickcount = (long)tickcount;
        if ((tickcount % 1D) != 0) {
            newtickcount = Math.round(tickcount);
            this.getLogger().warning("The requested sample rate is not precisely possible. It has been rounded to "+Double.toString(20D /newtickcount)+"hz from "+ Double.toString(rawsamplerate) + "hz.");
        }
        this.getLogger().info("Samples will be conducted every "+Long.toString(newtickcount) + " ticks.");
        ActionSpeedData.TickRate = newtickcount;
        ActionSpeedData.SampleRate = rawsamplerate;

        ReadPlayerData();

        SpeedLooper mainloop = new SpeedLooper();
        mainloop.runTaskTimer(this,1L,newtickcount);//1L delay may help this not start until the server is properly online and ticking
        this.MAINLOOP = mainloop;

        int pluginid = 19237;
        Metrics metrics = new Metrics(this, pluginid);

        getLogger().info("ActionSpeed by Enderbyte Programs (c) 2023-2025, some rights reserved. Plugin is now initialized.");
    }
    @Override
    public void onDisable() {
        getLogger().info("Shutting up the loop");
        this.MAINLOOP.cancel();
        getLogger().info("Saving configuration");
        WritePlayerData();//Update config with latest round of player customizations
        this.saveConfig();
        getLogger().info("Bye Bye Everybody!");
    }
    private void WritePlayerData() {
        ActionSpeedData.MainDataTable.SetAutoSave(false);
       // ActionSpeedData.MainDataTable.Clear();
        for (PlayerData pd:ActionSpeedData.Players.values()) {
            if (ActionSpeedData.MainDataTable.ExistsWhere("uuid",pd.UUID)){
                ActionSpeedData.MainDataTable.DeleteWhere("uuid",pd.UUID);
            }
            ActionSpeedData.MainDataTable.Insert(
                    new HashMap<String,Object>(Map.of(
                            "username",pd.username,
                            "uuid",pd.UUID,
                            "unit",pd.unit,
                            "active",pd.active,
                            "colour",pd.allowcolour,
                            "migrated",true
                    ))
            );
        }
        ActionSpeedData.MainDataTable.SetAutoSave(true);
        try {
            ActionSpeedData.MainDataTable.Save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void ReadPlayerData() {

        ActionSpeedData.MainDataTable = new EPDatabase(this).GetTable("data");
        ActionSpeedData.MainDataTable.AddColumn("uuid", DataTypes.String,"");
        ActionSpeedData.MainDataTable.AddColumn("username", DataTypes.String,"");
        ActionSpeedData.MainDataTable.AddColumn("unit", DataTypes.Number,"");
        ActionSpeedData.MainDataTable.AddColumn("colour", DataTypes.Boolean,"");

        ActionSpeedData.MainDataTable.AddColumn("active", DataTypes.Boolean,true);
        ActionSpeedData.MainDataTable.AddColumn("migrated", DataTypes.Boolean,false);

        //New program - Migrate old data

        if (this.getConfig().contains("nplayers",true)) {
            ActionSpeedData.MainDataTable.SetAutoSave(false);
            this.getLogger().info("Migrating old data...");
            List<String> playernames = this.getConfig().getStringList("nplayers");
            for (String name:playernames) {
                //Iterate through names and add each through referencing data list
                if (!name.equals(".placeholder")) {
                    //Ignore placeholder item


                    int unit = this.getConfig().getInt(String.format("players.%s.unit",name));
                    boolean isactive = this.getConfig().getBoolean(String.format("players.%s.active",name));
                    boolean iscolour = this.getConfig().getBoolean(String.format("players.%s.colour",name));

                    ActionSpeedData.MainDataTable.Insert(
                            new HashMap<String,Object>(Map.of(
                                "username",name,
                                "uuid","",
                                "unit",unit,
                                "active",isactive,
                                "colour",iscolour,
                                    "migrated",false
                            ))
                    );
                    this.getLogger().info("Migrated "+name);
                }
            }

            this.CONFIG.set("nplayers",null);
            this.CONFIG.set("players",null);//Clear data
            saveConfig();
            ActionSpeedData.MainDataTable.SetAutoSave(true);

            try {
                ActionSpeedData.MainDataTable.Save();
            } catch (IOException e) {

            }
            this.getLogger().info("Finished migration");
        }

        for (HashMap<String,Object> row:ActionSpeedData.MainDataTable.GetWhere("migrated",true)) {
            ActionSpeedData.Players.put((String)row.get("uuid"),new PlayerData(row));
        }

    }

}
