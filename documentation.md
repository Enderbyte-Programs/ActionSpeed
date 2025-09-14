# ActionSpeed Documentation
For version 1.8 or newer

## Installing the plugin

Like all Minecraft plugins, paste the JAR file into the server plugin folder (SERVER/plugins)

## Configuration

The following configuration options are available in `plugins/ActionSpeed/config.yml`

`destroydataonplayerleave` (default FALSE) - If set to true, will reset the player's configuration when they leave the server.

`onbydefault` (default TRUE) - When enabled, new players will have their speedometer turned on immediately when they join.

`showzeros` (default True) - When enabled, the plugin will broadcast "0" to the client when they are not moving. If it is set to false, no action bar message will be broadcast if the user is not moving.

`sample-rate` (default 10) - The speed at which every player's location is sampled. The default is 10hz, and the maximum is 20hz. Since schedules are done by the tick, your input will be rounded to the nearest tick rate.

Configuration may be updated with `/as reload`

## Permissions

ActionSpeed has two permissions nodes: `actionspeed.speedometer` and `actionspeed.admin`. `actionspeed.speedometer` gives a player control over their own personal speedoemter. `actionspeed.admin` gives the ability to reload the configuration, shutdown, and restart the plugin.

## Commands

Actionspeed has one command with aliases. The commands are `/actionspeed`, `/speedometer`, and `/as`. Sub-commands of this are as follows: (the root command may be substitued for an alias)

### /as
Displays Plugin version information

### /as enable (/as show)
Show the speedometer

### /as disable (/as hide)
Hide the speedometer

### /as help
Display a help menu. The help menu will only show commands that you have permission to run.

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

### /as reload
Meant for admins only. Reloads the configuration from config.yml

## What's new in 1.9?

- Add the ability to change the sample rate
  - it is the number of times per second the player's location will be sampled.

## What's new in 1.8?

- Rewrite the data storage system
  - This will reduce the opportunity for data corruption
  - This also makes the plugin name-change safe
  - Data will migrate automatically from config.yml to data/data.ept

- Remove the /as dumpdata command
  - This is because it is useless in non-debugging contexts