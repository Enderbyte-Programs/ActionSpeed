package enderbyteprograms.actionspeed;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventManager implements Listener {
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        String username = event.getPlayer().getDisplayName();
        if (!ActionSpeedData.Players.containsKey(username))
        {
            ActionSpeedData.Players.put(username,new PlayerData(username));
            //Add a new empty player data set
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (ActionSpeedMain.CONFIG.getBoolean("destroyplayerdataonleave")) {
            //ActionSpeedData.DestroyPlayer(event.getPlayer().getDisplayName());
            ActionSpeedData.Players.remove(event.getPlayer().getDisplayName());
        }
    }
}
