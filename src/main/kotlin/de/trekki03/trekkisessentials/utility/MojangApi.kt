package de.trekki03.trekkisessentials.utility

import org.bukkit.Bukkit
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MojangApi {
    companion object {
        //Get UUID from player name wia Mojang API
        fun getUUID(name: String): String {
            Bukkit.broadcastMessage("trying to get UUID")
            //create api query
            val url = "https://api.mojang.com/users/profiles/minecraft/$name"
            val json = getJsonStringFromURL(url)

            //check if query was successful
            if(json == "error" || json == "invalid name")
            {
                return "error"
            }

            //get id from query and add hyphen
            val idWithoutHyphen = getID(json)
            var idWithHyphen = addHyphen(idWithoutHyphen, 8)
            idWithHyphen = addHyphen(idWithHyphen, 13)
            idWithHyphen = addHyphen(idWithHyphen, 18)
            idWithHyphen = addHyphen(idWithHyphen, 23)
            return idWithHyphen
        }

        //Get Json String from api via url
        private fun getJsonStringFromURL(targetUrl: String): String {
            Bukkit.broadcastMessage("Trying to get Json String from URL")
            try {
                Bukkit.broadcastMessage(targetUrl)
                val url = URL(targetUrl)
                val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                connection.requestMethod = "GET"
                Bukkit.broadcastMessage("1")
                val inputReader = BufferedReader(InputStreamReader(connection.inputStream))
                var inputLine: String?
                var inputBuffer = ""
                Bukkit.broadcastMessage("2")
                //Add all lines from api response to one string
                while ((inputReader.readLine().also { inputLine = it }) != null) {
                    Bukkit.broadcastMessage("3")
                    inputBuffer += inputLine
                }
                Bukkit.broadcastMessage("4")
                inputReader.close()
                Bukkit.broadcastMessage("5")
                //check if response is valid
                return if (inputBuffer.contains("error") || inputBuffer == "") {
                    "invalid name"
                } else {
                    inputBuffer
                }
            }
            catch (e: MalformedURLException) {
                //TODO: Error handling
                e.printStackTrace()
                return "error"
            }
            catch (e: IOException) {
                e.printStackTrace()
                return "error"
            }
        }

        //get player uuid from json string
        private fun getID(jsonString: String) : String {
            Bukkit.broadcastMessage("Try to extract ID")
            val startOfID = jsonString.indexOf("id") + 5
            val endOfID = jsonString.indexOf("\"", startOfID)
            return jsonString.substring(startOfID, endOfID)
        }

        //add hyphen to position in string
        private fun addHyphen(inputString: String, position: Int): String {
            Bukkit.broadcastMessage("Ad Hyphens to ID")
            var buffer = inputString.substring(0, position)
            buffer += '-'
            return buffer + inputString.substring(position,inputString.length)
        }
    }
}

