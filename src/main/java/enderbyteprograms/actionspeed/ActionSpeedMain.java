package enderbyteprograms.actionspeed;

import enderbyteprograms.actionspeed.commands.Speedometer;
import enderbyteprograms.actionspeed.commands.SpeedometerTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionSpeedMain extends JavaPlugin {
    public static ActionSpeedMain INSTANCE;
    public static EventManager LISTENER;

    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getCommand("speedometer").setExecutor(new Speedometer());
        this.getCommand("speedometer").setTabCompleter(new SpeedometerTabCompleter());
        LISTENER = new EventManager();
        this.getServer().getPluginManager().registerEvents(LISTENER,this);

        getLogger().info("ActionSpeed is ready");
    }
    @Override
    public void onDisable() {
        getLogger().info("Bye Bye Everybody!");
    }
}
