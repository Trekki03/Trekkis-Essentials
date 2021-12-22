package de.trekki03.trekkisessentials.commands.utility

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandBooleans {
    companion object {

        //returns in sender is console
        fun isConsole(sender: CommandSender) : Boolean {
            return sender !is Player
        }

        //returns in sender is player
        fun isPlayer(sender: CommandSender) : Boolean {
            return sender is Player
        }
    }
}