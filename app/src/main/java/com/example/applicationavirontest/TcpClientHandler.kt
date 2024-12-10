package com.example.applicationavirontest

import android.content.Context
import android.util.Log
import java.io.*
import java.net.Socket

class TcpClientHandler(
    private val clientSocket: Socket,
    private val context: Context,
    private val sensorManager: SensorManager
) : Runnable {

    override fun run() {
        val clientAddress = clientSocket.inetAddress.hostAddress
        val fileName = "sensor_data_$clientAddress.txt" // Nom du fichier basé sur l'adresse IP du client

        try {
            val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val output = PrintWriter(clientSocket.getOutputStream(), true)
            Log.i(TAG, "Handling client at $clientAddress")

            // Lecture unique du premier message pour obtenir l'ID
            val firstMessage = input.readLine() // Lecture de la première ligne
            Log.i(TAG, "Received first message: $firstMessage")

            // Découpage et traitement du message
            if (!firstMessage.isNullOrEmpty()) {
                val dataParts = firstMessage.split(",")
                if (dataParts.isNotEmpty()) {
                    val sensorId = dataParts[0].trim() // Supposons que le premier élément soit l'ID
                    handleSensorInitialization(sensorId, clientAddress)
                }
            }

            // Boucle principale : traitement des données envoyées après l'initialisation
            while (!clientSocket.isClosed) {
                if (clientSocket.inputStream.available() > 0) {
                    val message = input.readLine()
                    if (!message.isNullOrEmpty()) {
                        writeDataToFile(fileName, message) // Écriture des données dans le fichier
                    }
                } else {
                    // Attente active pour éviter une boucle rapide
                    Thread.sleep(100)
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Client handler error: ${e.message}", e)
        } finally {
            try {
                clientSocket.close()
                Log.i(TAG, "Client disconnected: $clientAddress")
            } catch (e: IOException) {
                Log.e(TAG, "Error closing client socket: ${e.message}", e)
            }
        }
    }

    /**
     * Gère l'initialisation du capteur avec l'ID reçu dans le premier message.
     */
    private fun handleSensorInitialization(sensorId: String, clientAddress: String) {
        // Recherche du capteur existant par son adresse IP
        val existingSensor = sensorManager.findSensorById(clientAddress)

        if (existingSensor != null) {
            // Mise à jour de l'ID si un capteur existant est trouvé
            existingSensor.setId(sensorId)
            Log.i(TAG, "Updated sensor ID to: $sensorId")
        } else {
            // Création d'un nouveau capteur si aucun n'existe
            val newSensor = Sensor(
                sensorId,
                Sensor.StatutConnexion.CONNECTE,
                Sensor.StatutCalibration.NON_CALIBRE,
                clientSocket.port.toString(),
                clientSocket
            )
            sensorManager.addSensor(newSensor)
            Log.i(TAG, "New sensor added with ID: $sensorId")
        }
    }

    /**
     * Écrit les données dans un fichier local.
     */
    private fun writeDataToFile(fileName: String, data: String) {
        try {
            val file = File(context.filesDir, fileName)
            file.appendText("$data\n")
            Log.i(TAG, "Data written to ${file.absolutePath}")
        } catch (e: IOException) {
            Log.e(TAG, "Error writing to file $fileName", e)
        }
    }

    companion object {
        private val TAG = TcpClientHandler::class.java.simpleName
    }
}
