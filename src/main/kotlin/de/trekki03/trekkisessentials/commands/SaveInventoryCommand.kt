package de.trekki03.trekkisessentials.commands

import de.trekki03.trekkisessentials.Main
import de.trekki03.trekkisessentials.commands.utility.CommandBooleans
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException

class SaveInventoryCommand : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(CommandBooleans.isConsole(sender)) {
            sender.sendMessage(Main.lang.getConsoleText("consoleMessage.onlyPlayer", ChatColor.RED))
            return true
        }

        return when(args.size) {
            // sender is the target
            0 -> {
                saveInventory(sender as Player, sender)
                true
            }

            // target is arg[0]
            1 -> {
                oneArgument(sender as Player, args[0])
                true
            }
            else -> {
                false
            }
        }
    }

    // get player from argument
    private fun oneArgument(sender: Player, arg: String) {
        val target: Player? = Bukkit.getPlayer(arg)
        if(target == null) {
            sender.sendMessage(Main.lang.getPlayerText("chatMessage.saveInventory.noPlayer", sender))
            return
        }
        saveInventory(sender, target)
    }

    // save inventory
    private fun saveInventory(sender: Player, target: Player) {
        val file = File(Main.plugin.dataFolder, String.format("inventories/%s.yml", target.uniqueId))
        val cfg: FileConfiguration = YamlConfiguration.loadConfiguration(file)

        if(!file.exists()) {
            file.parentFile.mkdir()
        }

        cfg.set("inventory.content", target.inventory.contents)

        try {
            cfg.save(file)
            sender.sendMessage(Main.lang.getPlayerText("chatMessage.saveInventory.inventorySaved", sender))
        }
        catch (e: IOException) {
            //TODO: Error Management
            e.printStackTrace()
        }
    }
}