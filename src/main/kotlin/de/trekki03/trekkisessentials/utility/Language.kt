package de.trekki03.trekkisessentials.utility

import de.trekki03.trekkisessentials.Main
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException
import java.lang.Exception

class Language {
    private val langConfig: YamlConfiguration
    private val langFile = File(Main.plugin.dataFolder, "lang.yml")
    private val supportedLanguages = arrayOf("de_de", "en_en")

    // oad or create the lang file
    init {
        langConfig = YamlConfiguration.loadConfiguration(langFile)

        if (!langFile.exists()) {
            try {
                langFile.parentFile.mkdirs()
                langFile.createNewFile()
            } catch (e: IOException) {
                //TODO: Proper error management
                e.printStackTrace()
            }
        }

        addDefaults()
    }

    // add default values to langfile
    private fun addDefaults() {
        /* Console Messages: */
        langConfig.addDefault("en_en.consoleMessage.load", "--Trekkis Inventory Manager-- was loaded")
        langConfig.addDefault("de_de.consoleMessage.load", "--Trekkis Inventory Manager-- wurde geladen")

        langConfig.addDefault("en_en.consoleMessage.unload", "Trekkis Inventory Manager-- was unloaded")
        langConfig.addDefault("de_de.consoleMessage.unload", "Trekkis Inventory Manager-- wurde entladen")

        langConfig.addDefault("en_en.consoleMessage.onlyPlayer", "This command can't be executed by a console")
        langConfig.addDefault("de_de.consoleMessage.onlyPlayer", "Dieser Befehl kann nicht von einer Konsole ausgeführt werden")



        /* Chat Messages */
        langConfig.addDefault("en_en.chatMessage.error", "An Error occurred")
        langConfig.addDefault("de_de.chatMessage.error", "Es ist ein Fehler aufgetreten")



        /* COMMAND: LoadInventory */
        langConfig.addDefault("en_en.chatMessage.loadInventory.noPlayer", "The player doesn't exists")
        langConfig.addDefault("de_de.chatMessage.loadInventory.noPlayer", "Dieser Spieler existiert nicht")

        langConfig.addDefault("en_en.chatMessage.loadInventory.noPlayerData", "No saved data for this player exists")
        langConfig.addDefault("de_de.chatMessage.loadInventory.noPlayerData", "Es existieren keine gespeicherten Daten für diesen Spieler")

        langConfig.addDefault("en_en.chatMessage.loadInventory.inventoryReplaced", "Your inventory was replaced")
        langConfig.addDefault("de_de.chatMessage.loadInventory.inventoryReplaced", "Dein Inventar wurde ersetzt")



        /* COMMAND: SaveInventory */
        langConfig.addDefault("en_en.chatMessage.saveInventory.noPlayer", "No online player with this name")
        langConfig.addDefault("de_de.chatMessage.saveInventory.noPlayer", "Es ist kein Spieler mit diesem Namen online")

        langConfig.addDefault("en_en.chatMessage.saveInventory.inventorySaved", "Inventory data saved")
        langConfig.addDefault("de_de.chatMessage.saveInventory.inventorySaved", "Inventar Daten gespeichert")




        /* COMMAND: SeeInventory */
        langConfig.addDefault("en_en.chatMessage.seeInventory.noPlayer", "The targeted player isn't online")
        langConfig.addDefault("de_de.chatMessage.seeInventory.noPlayer", "Der Spieler ist nicht online")



        langConfig.options().copyDefaults(true)

        try {
            langConfig.save(langFile)
        } catch (e: Exception) {
            //TODO: Proper error management
            e.printStackTrace()
        }
    }

    // get message for console (with prefix and color
    fun getConsoleText(path: String, color: ChatColor): String {
        val language = "en_en"
        val message = langConfig.getString("$language.$path")
        return if (message == null) {
            "An error occurred while reading the lang.yml. If this is the first server start with the plugin, please restart. Elsewise please report this in the comments on the Spigot or Github page of this plugin."
        } else {
            "${ChatColor.GRAY}[TrekkisEssentials] $color$message"
        }

    }

    // get message for player chat in player language
    fun getPlayerText(path: String, sender: Player) : String {
        var language = sender.locale
        if(language !in supportedLanguages) {
            language = "en_en"
        }
        return langConfig.getString("$language.$path")
            ?: "An error occurred while reading the lang.yml. If this is the first server start with the plugin, please restart. Elsewise please report this in the comments on the Spigot or Github page of this plugin. Lang-Message: $path"
    }
}