package enderbyteprograms.actionspeed;

import enderbyteprograms.actionspeed.commands.Speedometer;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionSpeedMain extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("speedometer").setExecutor(new Speedometer());
        this.getServer().getPluginManager().registerEvents(new EventManager(),this);
        getLogger().info("ActionSpeed is ready");
    }
    @Override
    public void onDisable() {
        getLogger().info("Bye Bye Everybody!");
    }
}
