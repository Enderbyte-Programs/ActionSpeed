package enderbyteprograms.actionspeed;

import enderbyteprograms.actionspeed.commands.Speedometer;
import enderbyteprograms.actionspeed.commands.SpeedometerTabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import enderbyteprograms.actionspeed.bstats.Metrics;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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
        if (!CONFIG.contains("onbydefault")) {
            this.getConfig().set("onbydefault",true);
        }
        if (!CONFIG.contains("showzeros")) {
            this.getConfig().set("showzeros",true);
        }

        if (!CONFIG.getBoolean("destroyplayerdataonleave")) {
            ReadPlayerData();
        }

        SpeedLooper mainloop = new SpeedLooper();
        mainloop.runTaskTimer(this,1L,2L);//1L delay may help this not start until the server is properly online and ticking
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
        List<String> currentnplayers = this.getConfig().getStringList("nplayers");
        for (PlayerData pd:ActionSpeedData.Players.values()) {
            String name = pd.username;
            if (!currentnplayers.contains(name)) {
                currentnplayers.add(name);
            }
            this.getConfig().set(String.format("players.%s.active",name),pd.active);
            this.getConfig().set(String.format("players.%s.unit",name),pd.unit);
            this.getConfig().set(String.format("players.%s.colour",name),pd.allowcolour);
        this.getConfig().set("nplayers",currentnplayers);
        }
    }
    private void ReadPlayerData() {
        List<String> playernames = this.getConfig().getStringList("nplayers");
        for (String name:playernames) {
            //Iterate through names and add each through referencing data list
            if (!name.equals(".placeholder")) {
                //Ignore placeholder item
                ActionSpeedData.Players.put(name,new PlayerData(name,
                        this.getConfig().getInt(String.format("players.%s.unit",name)),
                        this.getConfig().getBoolean(String.format("players.%s.active",name)),
                        this.getConfig().getBoolean(String.format("players.%s.colour",name))));
            }
        }
    }

}
