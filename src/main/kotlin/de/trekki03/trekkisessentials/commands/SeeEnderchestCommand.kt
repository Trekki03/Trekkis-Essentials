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

//TODO: sender is target


class SeeEnderchestCommand : CommandExecutor, Listener {
    private var seeInvPlayers: HashMap<Player, Player> = HashMap()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        // sender has to be a player
        if(CommandBooleans.isConsole(sender)) {
            sender.sendMessage(Main.lang.getConsoleText("consoleMessage.onlyPlayer", ChatColor.RED))
            return true
        }

         return when (args.size) {
             // no replacing
             1 -> {
                withArgument(sender as Player, args[0], null)
             }

             // replacing by argument
             2 -> {
                 withArgument(sender as Player, args[0], args[1])
             }

             else -> {
                  false
             }
         }

    }


    private fun withArgument (sender: Player, arg0: String, arg1: String?) : Boolean {

        // get target fro argument
        val target: Player? = Bukkit.getPlayer(arg0)
        if(target == null) {
            sender.sendMessage(Main.lang.getPlayerText("chatMassage.seeInventory.noPlayer", sender))
            return true
        }

        // create inventory with content of enderchest
        val targetContentEnderchest: Inventory = Bukkit.createInventory(null, InventoryType.ENDER_CHEST)
        targetContentEnderchest.contents = target.enderChest.contents

        if(arg1 != null)
        {
            return when (arg1.lowercase())
            {
                // put player and target in a hashmap. When inventory is closed,
                // target enderchest from player in hashmap will be replaced
                "true" -> {
                    sender.openInventory(targetContentEnderchest)
                    seeInvPlayers[sender] = target
                    true
                }

                "false" -> {
                    sender.openInventory(targetContentEnderchest)
                    true
                }

                else -> {
                    false
                }
            }
        }
        sender.openInventory(targetContentEnderchest);
        return true;
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent)
    {
        val p: Player = event.player as Player
        val enderchest = event.inventory
        if(enderchest.type == InventoryType.ENDER_CHEST) {
            if (seeInvPlayers.containsKey(p)) {
                seeInvPlayers[p]!!.enderChest.contents = enderchest.contents
                seeInvPlayers.remove(p)
            }
        }
    }
}