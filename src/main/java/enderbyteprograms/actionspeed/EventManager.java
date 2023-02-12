package enderbyteprograms.actionspeed;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.List;

public class EventManager implements Listener {
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        if (!ActionSpeedData.inlist(event.getPlayer()))
        {ActionSpeedData.active.add(new PlayerData(event.getPlayer().getDisplayName()));}
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
            ActionSpeedData.active.add(new PlayerData(event.getPlayer().getDisplayName()));//Add to list if not found
        }
        if (!ActionSpeedData.active.get(ActionSpeedData.getpdata(event.getPlayer().getDisplayName())).active) {
            return;
        }
        double ndist = Utilities.convertspeed(ActionSpeedData.active.get(ActionSpeedData.getpdata(event.getPlayer().getDisplayName())).unit,dist);
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(Utilities.speedColour(dist*20,ActionSpeedData.active.get(ActionSpeedData.getpdata(event.getPlayer().getDisplayName())))+"Speed: "+Utilities.round(ndist*20,1)+" "+ActionSpeedData.active.get(ActionSpeedData.getpdata((event.getPlayer().getDisplayName()))).unitstr));
    }
    @EventHandler
    public void onvmove(VehicleMoveEvent event) {
        List<Entity> le = event.getVehicle().getPassengers();
        double dist = event.getFrom().distance(event.getTo());
        for (Entity e:le) {
            if (e instanceof Player) {
                if (!ActionSpeedData.inlist((Player)e)) {
                    ActionSpeedData.active.add(new PlayerData(((Player)e).getDisplayName()));
                }
                if (!ActionSpeedData.active.get(ActionSpeedData.getpdata(((Player)e).getDisplayName())).active) {
                    return;
                }
                double ndist = Utilities.convertspeed(ActionSpeedData.active.get(ActionSpeedData.getpdata(((Player)e).getDisplayName())).unit,dist);
                ((Player)e).spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(Utilities.speedColour(dist*20,ActionSpeedData.active.get(ActionSpeedData.getpdata(((Player)e).getDisplayName())))+"Speed: "+Utilities.round(ndist*20,1)+" "+ActionSpeedData.active.get(ActionSpeedData.getpdata(((Player)e).getDisplayName())).unitstr));
            }
        }

    }
}
