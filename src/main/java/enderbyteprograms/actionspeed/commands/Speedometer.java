package enderbyteprograms.actionspeed.commands;

import enderbyteprograms.actionspeed.ActionSpeedData;
import enderbyteprograms.actionspeed.ActionSpeedMain;
import enderbyteprograms.actionspeed.PlayerData;
import enderbyteprograms.actionspeed.Utilities;
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
            commandSender.sendMessage("ActionSpeed Plugin by Enderbyte Programs v"+ActionSpeedData.version);
            Utilities.HelpMenu(commandSender);
        } else {
            if (strings[0].equals("help")) {
                Utilities.HelpMenu(commandSender);
            }
            else if (strings[0].equals("enable") || strings[0].equals("show")) {
                if (!commandSender.hasPermission("actionspeed.speedometer")) {
                    commandSender.sendMessage(ChatColor.RED+"Insufficient Permission");
                    return false;
                }
                //Do stuff
                Player p;
                if (!Utilities.commandendsinplayer(strings))
                {
                    if (commandSender instanceof Player) {
                        p = (Player) commandSender;
                    } else {
                        commandSender.sendMessage(ChatColor.RED+"Please specify a player name");
                        return false;
                    }

                } else  {
                    p = Utilities.GetPL(strings);
                    if ( commandSender instanceof  Player) {
                        if (! ((Player)(commandSender)).getDisplayName().equals(strings[strings.length-1])) {
                            if (!commandSender.hasPermission("actionspeed.admin")) {
                                commandSender.sendMessage(ChatColor.RED + "You lack the required permissions to edit someone else's speedometer");
                                return false;
                            }
                        }
                    }
                }
                try {
                    PlayerData ntp = ActionSpeedData.active.get(ActionSpeedData.getpdata(p.getDisplayName()));
                } catch (IndexOutOfBoundsException e) {
                    ActionSpeedData.active.add(new PlayerData(p.getDisplayName()));
                }
                PlayerData ntp = ActionSpeedData.active.get(ActionSpeedData.getpdata(p.getDisplayName()));
                if (ActionSpeedData.inlist(p)) {

                    ActionSpeedData.DestroyPlayer(p.getDisplayName());
                    ntp.active = true;
                    ActionSpeedData.active.add(ntp);

                    commandSender.sendMessage(ChatColor.GREEN+"Speedometer is now enabled for "+p.getDisplayName());
                } else {

                    commandSender.sendMessage(ChatColor.RED + "Error");
                    return true;
                }

            }
            else if (strings[0].equals("disable") || strings[0].equals("hide")) {
                if (!commandSender.hasPermission("actionspeed.speedometer")) {
                    commandSender.sendMessage(ChatColor.RED+"Insufficient Permission");
                    return false;
                }
                    //Do stuff
                    Player p;
                    if (!Utilities.commandendsinplayer(strings))
                    {
                        if (commandSender instanceof Player) {
                            p = (Player) commandSender;
                        } else {
                            commandSender.sendMessage(ChatColor.RED+"Please specify a player name");
                            return false;
                        }

                    } else  {
                        p = Utilities.GetPL(strings);
                        if ( commandSender instanceof  Player) {
                            if (! ((Player)(commandSender)).getDisplayName().equals(strings[strings.length-1])) {
                                if (!commandSender.hasPermission("actionspeed.admin")) {
                                    commandSender.sendMessage(ChatColor.RED + "You lack the required permissions to edit someone else's speedometer");
                                    return false;
                                }
                            }
                        }
                    }
                    try {
                        PlayerData ntp = ActionSpeedData.active.get(ActionSpeedData.getpdata(p.getDisplayName()));
                    } catch (IndexOutOfBoundsException e) {
                        ActionSpeedData.active.add(new PlayerData(p.getDisplayName()));
                    }
                    PlayerData ntp = ActionSpeedData.active.get(ActionSpeedData.getpdata(p.getDisplayName()));
                    if (ActionSpeedData.inlist(p)) {

                        ActionSpeedData.DestroyPlayer(p.getDisplayName());
                        ntp.active = false;
                        ActionSpeedData.active.add(ntp);

                        commandSender.sendMessage(ChatColor.GREEN+"Speedometer is now disabled for "+p.getDisplayName());
                    } else {

                        commandSender.sendMessage(ChatColor.RED + "Error");
                        return true;
                    }

            }
            else if (strings[0].equals("forceshutdown")) {
                if (commandSender.hasPermission("actionspeed.admin")) {
                    //Unregister
                    HandlerList.unregisterAll(ActionSpeedMain.LISTENER);
                    ActionSpeedData.isregistered = false;
                    commandSender.sendMessage(ChatColor.GREEN+"EventManager and Speedometer disabled.");
                }
                else {
                    commandSender.sendMessage(ChatColor.RED+"Insufficient permissions");
                }
            }
            else if (strings[0].equals("reload")) {
                if (commandSender.hasPermission("actionspeed.admin")) {
                    ActionSpeedMain.INSTANCE.reloadConfig();
                    ActionSpeedMain.CONFIG = ActionSpeedMain.INSTANCE.getConfig();
                    commandSender.sendMessage(ActionSpeedMain.INSTANCE.getConfig().saveToString());
                    commandSender.sendMessage(ChatColor.GREEN+"Configuration reloaded");
                }
                else {
                    commandSender.sendMessage(ChatColor.RED+"Insufficient permissions");
                }
            }
            else if (strings[0].equals("restart")) {
                if (commandSender.hasPermission("actionspeed.admin")) {
                    if (ActionSpeedData.isregistered) {
                        commandSender.sendMessage(ChatColor.YELLOW+"Listener is already registered");
                        return false;
                    }
                    ActionSpeedMain.INSTANCE.getServer().getPluginManager().registerEvents(ActionSpeedMain.LISTENER,ActionSpeedMain.INSTANCE);
                    ActionSpeedData.isregistered = true;
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
                else if (strings[1].equals("m/min")) {
                    pd.unit = 3;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    pd.UpdateUnits();
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage("Changed to m/min");
                    return true;
                }
                else if (strings[1].equals("ft/s")) {
                    pd.unit = 4;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    pd.UpdateUnits();
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage("Changed to ft/s");
                    return true;
                }
                else if (strings[1].equals("mi/min")) {
                    pd.unit = 5;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    pd.UpdateUnits();
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage("Changed to mi/min");
                    return true;
                } else if (strings[1].equals("knots")) {
                    pd.unit = 6;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    pd.UpdateUnits();
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage("Changed to knots");
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
                if (strings[1].toLowerCase().equals("yes") || strings[1].toLowerCase().equals("true")) {
                    pd.allowcolour = true;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage(ChatColor.GREEN+"Enabled colour");
                } else if (strings[1].toLowerCase().equals("no") || strings[1].toLowerCase().equals("false")) {
                    pd.allowcolour = false;
                    ActionSpeedData.DestroyPlayer(pd.username);
                    ActionSpeedData.active.add(pd);
                    commandSender.sendMessage(ChatColor.GREEN+"Disabled colour");
                } else {
                    commandSender.sendMessage(ChatColor.RED+"That is not a valid argument.");
                    return false;
                }
            }else if (strings[0].equals("dumpdata")) {
                if (!commandSender.hasPermission("actionspeed.admin")) {
                    commandSender.sendMessage(ChatColor.RED + "You lack permission to do this");
                    return false;
                }
                commandSender.sendMessage(ChatColor.LIGHT_PURPLE+"ActionSpeed Data Dump");
                commandSender.sendMessage("Datalist length: "+ActionSpeedData.active.size());
                commandSender.sendMessage("enabled="+ActionSpeedData.isregistered);
                for (PlayerData p:ActionSpeedData.active) {
                    commandSender.sendMessage(p.username);
                    commandSender.sendMessage("    unitstr="+p.unitstr);
                    commandSender.sendMessage("    unit="+p.unit);
                    commandSender.sendMessage("    allowcolour="+p.allowcolour);
                    commandSender.sendMessage("    active="+p.active);
                }

            }
        }
        return true;

    }
}
