package enderbyteprograms.actionspeed;

import enderbyteprograms.actionspeed.commands.Speedometer;
import enderbyteprograms.actionspeed.commands.SpeedometerTabCompleter;
import enderbyteprograms.actionspeed.files.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;

import static enderbyteprograms.actionspeed.ActionSpeedData.fmake;

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
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        if (!fmake.Exists()) {
            HashMap<String,Object> hashMap = new HashMap<String,Object>();
            hashMap.put("allowcolour",true);
            hashMap.put("enabled",true);
            ActionSpeedData.CONFIG = hashMap;
        } else {
            try {
                ActionSpeedData.CONFIG = fmake.Load();
            } catch (IOException e) {
                getLogger().warning("File read error: "+e.getMessage());
                HashMap<String,Object> hashMap = new HashMap<String,Object>();
                hashMap.put("allowcolour",true);
                hashMap.put("enabled",true);
                ActionSpeedData.CONFIG = hashMap;
            }
        }
        getLogger().info("ActionSpeed is ready");
    }
    @Override
    public void onDisable() {
        getLogger().info(ActionSpeedData.CONFIG.toString());
        try {
            fmake.Dump(ActionSpeedData.CONFIG);
        } catch (IOException e) {
            getLogger().warning("Failed to save file: "+e.getMessage());
        }
        getLogger().info("Bye Bye Everybody!");
    }
}
