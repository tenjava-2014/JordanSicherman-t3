JordanSicherman's ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Theme:__ This submission fulfills both! (more info further down).
- __Time:__ Time 3 (7/12/2014 14:00 to 7/13/2014 00:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ None

---------------------------------------

Compilation
-----------

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/tenjava/JordanSicherman-t3`
- Compile and create the plugin package using Maven: `mvn`

Maven will download all required dependencies and build a ready-for-use plugin package!

---------------------------------------

Usage
-----

1. Install plugin
2. Choose from PRIMARY mode or SECONDARY mode in the config.
2a. PRIMARY mode is the intended mode and fulfills 'What random events can occur in Minecraft?'. SECONDARY mode is just for fun and fulfills the second theme option.
3. Join the server.

Description
-----

Over time, the plugin will automatically schedule random events. Every event will occur one-after-another after a period of 5-20 minutes elapses. A list of events can be found by using the command /event begin.

There is one event that is always active - the food rot event. There is a random chance that, as a player holding a food item (any non-goldenapple non-rotten item in the Foodstuffs tab), your food decays into rotten flesh. Gross!

Commands
-----

One command drives this plugin - /event.
/event begin <event> [player]      will begin a specified event and, if given, at a specific player's location.
/event end      will end all events on the server.
/event end <event>      will end all of the specified events on the server.

Events
-----

Apocalypse: A random humanoid entity will be chosen to be infected. This entity will become a zombie villager with a potion effect and any humanoid entity it touches will become infected too. This event ends when either all infected entities die or the command is executed (all changed entities revert to normal).

Smog: The air becomes poisonous! Players must take shelter underwater (or lava) to avoid taking constant damage. This event ends after a time, or the end command is executed.

Piranhas: Unlike the smog event, water becomes the issue. Players in the water experience constant damage from piranhas. This event is also timed.

Forest Fire: When initialized, lightning will strike down from the heavens upon a nearby tree grove igniting them and allowing nature to take its course.

Mushroom Rain: By some unknown miracle, red and brown mushrooms begin to fall from the heavens around all players. This event is timed.