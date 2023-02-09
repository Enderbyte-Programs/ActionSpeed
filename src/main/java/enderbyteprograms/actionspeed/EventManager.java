package enderbyteprograms.actionspeed;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.List;

public class EventManager implements Listener {
    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event) {
        ActionSpeedData.active.remove(event.getPlayer().getDisplayName());
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        //Move stuff
        Location prev = event.getFrom();
        Location to = event.getTo();
        double dist = to.distance(prev);
        if (dist == 0D) {
            return;
            //Improve performance
        }
        if (!ActionSpeedData.inlist(event.getPlayer())) {
            return;
        }
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("Speed: "+Utilities.round(dist*20,1)+" m/s"));
    }
    @EventHandler
    public void onvmove(VehicleMoveEvent event) {
        List<Entity> le = event.getVehicle().getPassengers();
        double dist = event.getFrom().distance(event.getTo());
        for (Entity e:le) {
            if (e instanceof Player) {
                if (!ActionSpeedData.inlist((Player)e)) {
                    return;
                }
                ((Player)e).spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText("Speed: "+Utilities.round(dist*20,1)+" m/s"));
            }
        }

    }
}
