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
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.function.Consumer


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

       getVersion { version ->
            if (description.version == version) {
                Bukkit.getConsoleSender().sendMessage(lang.getConsoleText("consoleMessage.noUpdate", ChatColor.GREEN))
            } else {
                Bukkit.getConsoleSender().sendMessage(lang.getConsoleText("consoleMessage.update", ChatColor.RED))
            }
        }
    }

    override fun onDisable() {
        server.consoleSender.sendMessage(lang.getConsoleText("consoleMessage.unload", ChatColor.GREEN))
    }

    private fun getVersion(consumer: Consumer<String?>) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            try {
                URL("https://api.spigotmc.org/legacy/update.php?resource=98613").openStream()
                    .use { inputStream ->
                        Scanner(inputStream).use { scanner ->
                            if (scanner.hasNext()) {
                                consumer.accept(scanner.next())
                            }
                        }
                    }
            }
            catch (exception: IOException) {
                plugin.logger.info("Unable to check for updates: " + exception.message)
            }
        })
    }
}