package enderbyteprograms.actionspeed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpeedLooper extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player:Bukkit.getOnlinePlayers()) {
            if (!ActionSpeedData.Players.containsKey(player.getDisplayName())) {
                //Ignore it for now and wait for join event handler to finish up
            } else {
                PlayerData assocdata = ActionSpeedData.Players.get(player.getDisplayName());
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
                        double dist = l.distance(previouslocation);
                        assocdata.lastMoveLocation = l;
                        dist *= 10;//Fired 10 times per second
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
