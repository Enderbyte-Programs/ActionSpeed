package enderbyteprograms.actionspeed.commands;

import enderbyteprograms.actionspeed.ActionSpeedData;
import enderbyteprograms.actionspeed.ActionSpeedMain;
import enderbyteprograms.actionspeed.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class Speedometer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("ActionSpeed Plugin v1.2");
        } else {
            if (strings[0].equals("toggle")) {
                if (!commandSender.hasPermission("actionspeed.speedometer")) {
                    commandSender.sendMessage(ChatColor.RED+"Insufficient Permission");
                    return false;
                }
                if (commandSender instanceof Player) {
                    //Do stuff
                    Player p = (Player)commandSender;
                    try {
                        PlayerData ntp = ActionSpeedData.active.get(ActionSpeedData.getpdata(p.getDisplayName()));
                    } catch (IndexOutOfBoundsException e) {
                        ActionSpeedData.active.add(new PlayerData(p.getDisplayName()));
                    }
                    PlayerData ntp = ActionSpeedData.active.get(ActionSpeedData.getpdata(p.getDisplayName()));
                    if (ActionSpeedData.inlist(p)) {

                        ActionSpeedData.DestroyPlayer(p.getDisplayName());
                        ntp.active = !ntp.active;
                        ActionSpeedData.active.add(ntp);

                        commandSender.sendMessage(ChatColor.GREEN+"Speedometer is toggled");
                    } else {

                        commandSender.sendMessage(ChatColor.RED + "Error");
                        return true;
                    }
                } else {
                    commandSender.sendMessage(ChatColor.RED+"Only players may execute this command");
                    return false;
                }
            }
            else if (strings[0].equals("forceshutdown")) {
                if (commandSender.hasPermission("actionspeed.admin")) {
                    //Unregister
                    HandlerList.unregisterAll(ActionSpeedMain.LISTENER);

                    commandSender.sendMessage(ChatColor.GREEN+"EventManager and Speedometer disabled.");
                }
                else {
                    commandSender.sendMessage(ChatColor.RED+"Insufficient permissions");
                }
            }
            else if (strings[0].equals("restart")) {
                if (commandSender.hasPermission("actionspeed.admin")) {
                    ActionSpeedMain.INSTANCE.getServer().getPluginManager().registerEvents(ActionSpeedMain.LISTENER,ActionSpeedMain.INSTANCE);
                    commandSender.sendMessage(ChatColor.GREEN+"Actionspeed is now ready");
                }else {
                    commandSender.sendMessage(ChatColor.RED+"Insufficient permissions");
                }
            } else if (strings[0].equals("setunits")) {
                if (!commandSender.hasPermission("actionspeed.speedometer")) {
                    commandSender.sendMessage(ChatColor.RED+"You lack permission to do this");
                    return false;
                }
                try {
                    String pls = strings[1];
                } catch (Exception e) {
                    commandSender.sendMessage("Please provide unit");
                    return false;
                }
                commandSender.sendMessage(strings[1]);
                PlayerData pd = ActionSpeedData.active.get(ActionSpeedData.getpdata(((Player)commandSender).getDisplayName()));
                if (strings[1].equals("m/s")) {
                    pd.unit = 0;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    pd.UpdateUnits();
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage("Changed to m/s");
                    return true;
                }
                else if (strings[1].equals("km/h")) {
                    pd.unit = 1;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    pd.UpdateUnits();
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage("Changed to km/h");
                    return true;
                }
                else if (strings[1].equals("mi/h")) {
                    pd.unit = 2;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    pd.UpdateUnits();
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage("Changed to mi/h");
                    return true;
                }
            }
            else if (strings[0].equals("allowcolour")) {
                if (!commandSender.hasPermission("actionspeed.speedometer")) {
                    commandSender.sendMessage(ChatColor.RED + "You lack permission to do this");
                    return false;
                }
                try {
                    String __p = strings[1];
                } catch (Exception e) {
                    commandSender.sendMessage("Please provide \"yes\" or \"no\"");
                    return false;
                }
                PlayerData pd = ActionSpeedData.active.get(ActionSpeedData.getpdata(((Player)commandSender).getDisplayName()));
                if (strings[1].toLowerCase().equals("yes")) {
                    pd.allowcolour = true;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage(ChatColor.GREEN+"Enabled colour");
                } else if (strings[1].toLowerCase().equals("no")) {
                    pd.allowcolour = false;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage(ChatColor.GREEN+"Disabled colour");
                } else {
                    commandSender.sendMessage(ChatColor.RED+"Please use either yes or no");
                }
            }
        }
        return true;

    }
}
