package de.trekki03.trekkisessentials.commands

import de.trekki03.trekkisessentials.Main
import de.trekki03.trekkisessentials.commands.utility.CommandBooleans
import de.trekki03.trekkisessentials.utility.MojangApi
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*

class LoadInventoryCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(CommandBooleans.isConsole(sender)) {
            sender.sendMessage(Main.lang.getConsoleText("consoleMessage.onlyPlayer", ChatColor.RED))
            return true
        }
        return when (args.size) {
            // no arguments, sender is target, no inventory replace
            0 -> {
                loadInventory(sender as Player, sender as OfflinePlayer, false)
                true
            }

            // one arguments, target is arg[0], no inventory replace
            1 -> {
                withArgument(sender as Player, args[0], false)
                true
            }

            // one arguments, target is arg[0], replace -> arg[1]
            2 -> {
                when (args[1].lowercase())
                {
                    "true" -> {
                        withArgument(sender as Player, args[0], true)
                        true
                    }
                    "false" -> {
                        withArgument(sender as Player, args[0], false)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

            else -> {
                false
            }
        }
    }

    // fetches uuid of player for argument
    private fun withArgument(sender: Player, arg: String, replace: Boolean) {
        val uuid = MojangApi.getUUID(arg)
        when (uuid) {
            "invalid name", "error" -> {
                sender.sendMessage(Main.lang.getPlayerText("chatMessage.loadInventory.noPlayer", sender))
            }
            else -> {
                if(replace) {
                    loadInventory(sender,Bukkit.getOfflinePlayer(UUID.fromString(uuid)), true)
                }
                else {
                    loadInventory(sender,Bukkit.getOfflinePlayer(UUID.fromString(uuid)), false)
                }
            }
        }
    }

    // loads the inventory from file (if existing) and shows or replaces inventory of sender
    private fun loadInventory(sender: Player, target: OfflinePlayer, replaceInventory: Boolean) {
        val file = File(Main.plugin.dataFolder, String.format("inventories/%s.yml", target.uniqueId))
        val config: FileConfiguration = YamlConfiguration.loadConfiguration(file)

        if(!file.exists())
        {
            sender.sendMessage(Main.lang.getPlayerText("chatMessage.loadInventory.noPlayerData", sender))
            return
        }

        @Suppress("UNCHECKED_CAST")
        val contents = (config["inventory.content"] as List<ItemStack>).toTypedArray()

        if(replaceInventory) {
            sender.inventory.contents = contents
            sender.sendMessage(Main.lang.getPlayerText("chatMessage.loadInventory.inventoryReplaced", sender))
        }
        else
        {
            val inventory = Bukkit.createInventory(null, 54);
            inventory.contents = contents
            sender.openInventory(inventory)
        }
    }
}