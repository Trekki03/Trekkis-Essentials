### The Plugin:

This plugin is the successor of Trekki's Inventory Manager. In this plugin I want to go beyond the inventory area. So e.g. fly, heal etc. commands could follow. All ideas are welcome in the comments.

At this time the plugin contains all features of Trekkis Inventory Manager including some minor improvements.

### Commands:

/saveinv [Online Player]

    - Permission: te.save - default: op

    - Saves the content of the players inventory in a .yml file

/loadinv [saved player] [replace inventory (true/false)]

Attention, this command uses the MojangAPI. If you use it more than 600 times in 10 minutes, your ip can get banned from the MojangAPI. This has an impact on all plugins using the Mojang API.
(No safty feature implemented yet)

    Permission: te.load - default: op

    - Loads the inventory data of a saved file. You can choose if your inventory gets replaced or the loaded data is shown in a chest like inventory. With no arguments you invenory didn't get replaced.

/seeinv <Online Player> [interact true/false]

    Permission: te.see - default: op

    Let you see and interact with the targets inventory. You can choose if items get removed of the targets inventory. With no Argument you only see the inventory.

/seeend <Online Player> [interact true/false]

    Permission: te.end - default: op

    Let you see and interact with the targets enderchest. You can choose if items get removed of the targets enderchest. With no Argument you only see the enderchest.

### Permissions:

All permissions have the prefix te (Trekkis Essentials).
The All-In permission is te.*
You find all the permissions and there default configuration above.

### Language:

The plugin supports english and german.
The chat messages are automatically in the language of the commmand senders minecraft client.
If your language isn't supported it gets automatically changed to english.
Console messages are automatically in english.

### Stats:

The plugin collects anonymous data with bstats.
It is possible to turn it off in the bstats config. But I ask you to leave it on, because the data (most notably the Java and Minecraft Version) helps me a lot and is totaly anonymous.
And if you want, you can also see this data.
HERE
bStats is also used by the most other plugins (e.g. World Edit, World Guard...).
