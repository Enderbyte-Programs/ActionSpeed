package net.enderbyteprograms.actionspeed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpeedLooper extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player:Bukkit.getOnlinePlayers()) {
            if (!ActionSpeedData.Players.containsKey(player.getUniqueId().toString())) {
                //Ignore it for now and wait for join event handler to finish up
            } else {
                PlayerData assocdata = ActionSpeedData.Players.get(player.getUniqueId().toString());
                if (!assocdata.active) {
                    continue;//Don't waste time calculating for those who are not active
                }
                Location l = player.getLocation();
                if (assocdata.lastMoveLocation == null) {
                    assocdata.SendActionBarMessage("0 "+assocdata.unitstr);
                    assocdata.lastMoveLocation = l;
                } else {
                    Location previouslocation = assocdata.lastMoveLocation;
                    if (previouslocation.equals(l)) {
                        //User has not moved
                        if (ActionSpeedMain.CONFIG.getBoolean("showzeros")) {
                            assocdata.SendActionBarMessage("0 "+assocdata.unitstr);
                        }
                    } else {
                        double dist;
                        try {
                            dist = l.distance(previouslocation);
                        } catch (Exception e) {
                            dist = 0;
                        }
                        assocdata.lastMoveLocation = l;
                        dist *= ActionSpeedData.SampleRate;//Fired 10 times per second
                        ChatColor tcol = Utilities.speedColour(dist,assocdata);
                        double converteddist = Utilities.round(Utilities.convertspeed(assocdata.unit,dist),1);
                        String finalstr = String.valueOf(converteddist) + " " + assocdata.unitstr;

                        assocdata.SendActionBarMessage(tcol+finalstr+ChatColor.RESET);
                    }
                }
            }
        }
    }
}
