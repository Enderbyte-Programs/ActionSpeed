# ActionSpeed Documentation
For version 1.4.x

## Installing the plugin

Like all Minecraft plugins, paste the JAR file into the server plugin folder (SERVER/plugins)

## Configuration

As of version 1.4, there is a configuration file with two fields. You can find this file in `SERVER/plugins/ActionSpeed/config.yml`. The two nodes are `runinvehicles` and `destroyplayerdataonleave`. `runinvehicles` sets if the plugin is allowed to display speeds while in vehicles. Since OnVehicleMove may call more frequently than OnPlayerMove, disabling this may improve performance if you are having issues. `destroyplayerdataonleave` is defaulted to OFF. If set to TRUE, a player's speedometer settings will be deleted when they leave the game.

Configuration may be updated with `/as reload`

## Permissions

ActionSpeed has two permissions nodes: `actionspeed.speedometer` and `actionspeed.admin`. `actionspeed.speedometer` gives a player control over their own personal speedoemter. `actionspeed.admin` gives the ability to reload the configuration, shutdown, and restart the plugin.

## Commands

Actionspeed has one command with aliases. The commands are `/actionspeed`, `/speedometer`, and `/as`. Sub-commands of this are as follows: (the root command may be substitued for an alias)

### /as 
Displays Plugin version information

### /as toggle
Toggle on and off the speedometer

### /as setunits (unitstr)
Set the displayed speed unit to one of the listed units:
(m/s) (default)
(m/min)
(km/h)
(mi/h)
(mi/min)
(ft/s)
(knots)

### /as allowcolour (yes/no)
Enable or disable speedometer colour

### /as dumpdata
Meant for admins only. Prints out internal contents and each player's settings

### /as reload
Meant for admins only. Reloads the configuration from config.yml

### /as forceshutdown
Meant for admins only. Physically disables plugin by shutting off Event Listener. If you are experiencing performance issues, run this

### /as restart
Meant for admins only. Re-registers the Listener after it has been shut down.

### A Notice about BSTATS
This plugin uses Bstats for analytical purposes. If you wish to opt out of this (please don't), please read bstats documentation.
