package de.trekki03.trekkisessentials
import de.trekki03.trekkisessentials.bstats.Metrics
import de.trekki03.trekkisessentials.commands.LoadInventoryCommand
import de.trekki03.trekkisessentials.commands.SaveInventoryCommand
import de.trekki03.trekkisessentials.commands.SeeEnderchestCommand
import de.trekki03.trekkisessentials.commands.SeeInventoryCommand
import de.trekki03.trekkisessentials.utility.Language
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    companion object {
        lateinit var plugin: Main
        lateinit var lang: Language
    }

    override fun onEnable() {
        //Setup plugin and bStats
        plugin = this
        lang = Language()
        @Suppress("UNUSED_VARIABLE")
        Metrics(plugin, 13658)

        //create class objects for event registration
        val seeEnd = SeeEnderchestCommand()
        val seeInv = SeeInventoryCommand()

        //register events
        Bukkit.getPluginManager().registerEvents(seeEnd, plugin)
        Bukkit.getPluginManager().registerEvents(seeInv, plugin)

        //register commands
        getCommand("seeend")?.setExecutor(seeEnd)
        getCommand("loadinv")?.setExecutor(LoadInventoryCommand())
        getCommand("seeinv")?.setExecutor(seeInv)
        getCommand("saveinv")?.setExecutor(SaveInventoryCommand())

        Bukkit.getConsoleSender().sendMessage(lang.getConsoleText("consoleMessage.load", ChatColor.GREEN))
    }

    override fun onDisable() {
        server.consoleSender.sendMessage(lang.getConsoleText("consoleMessage.unload", ChatColor.GREEN))
    }
}