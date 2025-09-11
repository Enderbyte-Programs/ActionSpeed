package net.enderbyteprograms.actionspeed;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class EventManager implements Listener {
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();
        if (!ActionSpeedData.Players.containsKey(uuid))
        {
            //Check if unmigrated

            boolean ismigrated = false;
            for (HashMap<String,Object> unmigratedrows:ActionSpeedData.MainDataTable.GetWhere("migrated",false))

            ActionSpeedData.Players.put(uuid,new PlayerData(event.getPlayer()));
            //Add a new empty player data set
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (ActionSpeedMain.CONFIG.getBoolean("destroyplayerdataonleave")) {
            //ActionSpeedData.DestroyPlayer(event.getPlayer().getDisplayName());
            ActionSpeedData.Players.remove(event.getPlayer().getUniqueId().toString());
        }
    }
}
