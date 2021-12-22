package de.trekki03.trekkisessentials.commands

import de.trekki03.trekkisessentials.Main
import de.trekki03.trekkisessentials.commands.utility.CommandBooleans
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

class SeeInventoryCommand : CommandExecutor, Listener {

    private val seeInvPlayers: HashMap<Player, Player> = HashMap()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        // sender has to be player
        if(CommandBooleans.isConsole(sender)) {
            sender.sendMessage(Main.lang.getConsoleText("consoleMessage.onlyPlayer", ChatColor.RED))
            return true
        }

        return when (args.size) {
            //no replace
            1 -> {
                withArgument(sender as Player, args[0], null)
            }
            //replace by arg
            2 -> {
                withArgument(sender as Player, args[0], args[1])
            }
            else -> {
                false
            }
        }
    }

    private fun withArgument(sender: Player, arg0: String, arg1: String?) : Boolean {
        val  target: Player? = Bukkit.getPlayer(arg0)
        if(target == null) {
            sender.sendMessage(Main.lang.getPlayerText("chatMessage.seeInventory.noPlayer", sender))
            return true
        }

        val targetContentInventory: Inventory = Bukkit.createInventory(null, InventoryType.PLAYER)
        targetContentInventory.contents = target.inventory.contents

        if(arg1 == null)
        {
          sender.openInventory(targetContentInventory)
          return true
        }

        return when(arg1.lowercase()) {

            // put player on replace hashmap
            "true" -> {
                sender.openInventory(targetContentInventory)
                seeInvPlayers[sender] = target
                true
            }

            // do not replace
            "false" -> {
                sender.openInventory(targetContentInventory)
                true
            }
            else -> {
                false
            }
        }

    }

    //replace inventories from hashmap
    @EventHandler
    fun oonInventoryClose(event: InventoryCloseEvent) {
        val p: Player = event.player as Player
        val inventory = event.inventory
        if(seeInvPlayers.containsKey(p)) {
            seeInvPlayers[p]!!.inventory.contents = inventory.contents
            seeInvPlayers.remove(p)
        }
    }
}