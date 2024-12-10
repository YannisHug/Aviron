package com.example.applicationavirontest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.IOException
import java.lang.String
import java.net.ServerSocket
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class TcpServerService : Service() {
    private var serverSocket: ServerSocket? = null
    private val working = AtomicBoolean(true)
    private val threadPool = Executors.newCachedThreadPool() // Efficient thread management for clients
    private val sensorManager = SensorManager()

    // Binder pour exposer le service à l'activité
    inner class LocalBinder : Binder() {
        fun getService(): TcpServerService {
            return this@TcpServerService
        }
    }
    private val binder = LocalBinder()

    private val serverRunnable = Runnable {
        try {
            serverSocket = ServerSocket(PORT)
            Log.i(TAG, "Server initialized on port $PORT: isClosed=${serverSocket?.isClosed}")

            while (working.get()) {
                Log.i(TAG, "Waiting for connections")
                val clientSocket = serverSocket?.accept() // Wait for client connection
                Log.i(TAG, "New client connected: ${clientSocket?.inetAddress?.hostAddress}")

                if (clientSocket != null) {
                    // Ajout d'un sensor avec un ID unique basé sur l'adresse IP et le port
                    val sensor = Sensor(
                        clientSocket.inetAddress.hostAddress,
                        Sensor.StatutConnexion.CONNECTE,
                        Sensor.StatutCalibration.NON_CALIBRE,
                        String.valueOf(clientSocket.port),  // Utilisation du port de la socket comme chaîne de caractères
                        clientSocket // Passer la socket si besoin de l'utiliser plus tard
                    )
                    sensorManager.addSensor(sensor)

                    // Handle client communication in a separate thread
                    threadPool.execute(TcpClientHandler(clientSocket, applicationContext, sensorManager))
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error in server socket: ${e.message}", e)
        } finally {
            stopServer()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        Thread(serverRunnable).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        working.set(false)
        stopServer()
        threadPool.shutdown()
    }

    // Méthode publique pour accéder au SensorManager
    fun getSensorManager(): SensorManager {
        return sensorManager
    }


    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val NOTIFICATION_CHANNEL_ID = packageName
            val channelName = "Tcp Server Background Service"
            val chan = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_LOW)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(chan)
            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            val notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("TCP Server is running in the background")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
            startForeground(1, notification)
        }
    }

    private fun stopServer() {
        try {
            serverSocket?.close()
            Log.i(TAG, "Server stopped")
        } catch (e: IOException) {
            Log.e(TAG, "Error closing server socket: ${e.message}", e)
        }
    }

    companion object {
        private val TAG = TcpServerService::class.java.simpleName
        private const val PORT = 5037
    }
}