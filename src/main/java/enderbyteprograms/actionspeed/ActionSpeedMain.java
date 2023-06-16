package enderbyteprograms.actionspeed;

import enderbyteprograms.actionspeed.commands.Speedometer;
import enderbyteprograms.actionspeed.commands.SpeedometerTabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActionSpeedMain extends JavaPlugin {
    public static ActionSpeedMain INSTANCE;
    public static EventManager LISTENER;
    public static FileConfiguration CONFIG;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getCommand("speedometer").setExecutor(new Speedometer());
        this.getCommand("speedometer").setTabCompleter(new SpeedometerTabCompleter());
        LISTENER = new EventManager();
        this.getServer().getPluginManager().registerEvents(LISTENER,this);
        ActionSpeedData.isregistered = true;
        //Set up configs
        this.saveDefaultConfig(); //APparently this won't overwrite again
        CONFIG = this.getConfig();//Reduce load on Event calls NOTE: Directly call config on write operations
        ReadPlayerData();
        getLogger().info("ActionSpeed is ready");
    }
    @Override
    public void onDisable() {
        getLogger().info("Saving configuration");
        WritePlayerData();//Update config with latest round of player customizations
        this.saveConfig();
        getLogger().info("Bye Bye Everybody!");
    }
    private void WritePlayerData() {
        List<String> currentnplayers = this.getConfig().getStringList("nplayers");
        for (PlayerData pd:ActionSpeedData.active) {
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
                ActionSpeedData.active.add(new PlayerData(name,
                        this.getConfig().getInt(String.format("players.%s.unit",name)),
                        this.getConfig().getBoolean(String.format("players.%s.active",name)),
                        this.getConfig().getBoolean(String.format("players.%s.colour",name))));
            }
        }
    }

}
