package enderbyteprograms.actionspeed;

import enderbyteprograms.actionspeed.commands.Speedometer;
import enderbyteprograms.actionspeed.commands.SpeedometerTabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

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
        this.getConfig().addDefault("runinvehicles",true);
        this.getConfig().setComments("runinvehicles", Arrays.stream(new String[] {"Set if plugin is allowed to display speed on a vehicle move (disabling may improve performance if you are having issues)"}).toList());
        this.getConfig().addDefault("destroyplayerdataonleave",false);
        this.getConfig().setComments("destroyplayerdataonleave", Arrays.stream(new String[] {"Set if plugin resets a players customizations when they leave"}).toList());
        this.saveDefaultConfig(); //APparently this won't overwrite again
        CONFIG = this.getConfig();//Reduce load on Event calls
        getLogger().info("ActionSpeed is ready");
    }
    @Override
    public void onDisable() {
        this.saveConfig();
        getLogger().info("Bye Bye Everybody!");
    }

}
