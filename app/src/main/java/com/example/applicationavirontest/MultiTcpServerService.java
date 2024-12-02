// package com.example.applicationavirontest;

// import android.app.Service;
// import android.content.Intent;
// import android.os.IBinder;
// import android.util.Log;

// import java.io.*;
// import java.net.ServerSocket;
// import java.net.Socket;

// public class TcpServerService extends Service {

//     private static final String TAG = "TcpServerService";
//     private static final int PORT = 5036;
//     private boolean isRunning = false;
//     private ServerSocket serverSocket;

//     @Override
//     public void onCreate() {
//         super.onCreate();
//         Log.d(TAG, "Service créé");
//         isRunning = true;

//         // Démarrer le serveur dans un thread séparé
//         new Thread(() -> startServer()).start();
//     }

//     private void startServer() {
//         String filePath = getFilesDir() + "/donnees.txt"; // Répertoire interne de l'application
//         Log.d(TAG, "Fichier de sortie : " + filePath);


//         try {
//             serverSocket = new ServerSocket(PORT);
//             Log.d(TAG, "Serveur en attente de connexions...");

//             try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath, true))) { // Mode append
//                 while (isRunning) {
//                     try {
//                         // Accepter une nouvelle connexion
//                         Socket clientSocket = serverSocket.accept();
//                         Log.d(TAG, "Connexion établie avec " + clientSocket.getInetAddress());

//                         // Lire les données envoyées par le client
//                         try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//                             String data;
//                             while ((data = input.readLine()) != null) {
//                                 Log.d(TAG, "Données reçues : " + data);

//                                 // Écrire les données dans le fichier
//                                 fileWriter.write(data);
//                                 fileWriter.newLine();
//                                 fileWriter.flush();
//                             }
//                         }
//                         clientSocket.close();
//                         Log.d(TAG, "Connexion fermée");
//                     } catch (IOException e) {
//                         Log.e(TAG, "Erreur avec le client : " + e.getMessage());
//                     }
//                 }
//             }
//         } catch (IOException e) {
//             Log.e(TAG, "Erreur serveur : " + e.getMessage());
//         } finally {
//             stopSelf();
//         }
//     }

//     @Override
//     public void onDestroy() {
//         super.onDestroy();
//         Log.d(TAG, "Service arrêté");
//         isRunning = false;

//         try {
//             if (serverSocket != null && !serverSocket.isClosed()) {
//                 serverSocket.close();
//             }
//         } catch (IOException e) {
//             Log.e(TAG, "Erreur lors de la fermeture du serveur : " + e.getMessage());
//         }
//     }

//     @Override
//     public IBinder onBind(Intent intent) {
//         return null; // Pas de liaison nécessaire
//     }
// }

//package com.example.applicationavirontest;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.util.Log;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class TcpServerService extends Service {
//
//    private static final String TAG = "TcpServerService";
//    private static final int PORT = 5036;
//    private boolean isRunning = false;
//    private ServerSocket serverSocket;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d(TAG, "Service créé");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent, flags, startId);
//
//        if (!isRunning) {
//            isRunning = true;
//            Log.d(TAG, "Démarrage du serveur...");
//            new Thread(() -> startServer()).start();
//        }
//
//        // Retourne START_STICKY pour redémarrer le service automatiquement si le système le tue
//        return START_STICKY;
//    }
//
//    private void startServer() {
//        String filePath = getFilesDir() + "/donnees.txt"; // Répertoire interne pour les fichiers
//        Log.d(TAG, "Fichier de sortie : " + filePath);
//
//        try {
//            serverSocket = new ServerSocket(PORT);
//            Log.d(TAG, "Serveur en attente de connexions sur le port " + PORT);
//
//            // Ouverture du fichier pour l'écriture en mode append
//            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath, true))) {
//                while (isRunning) {
//                    try {
//                        // Attente d'une connexion client
//                        Socket clientSocket = serverSocket.accept();
//                        Log.d(TAG, "Connexion établie avec " + clientSocket.getInetAddress());
//
//                        // Lecture des données reçues
//                        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//                            String data;
//                            while ((data = input.readLine()) != null) {
//                                Log.d(TAG, "Données reçues : " + data);
//
//                                // Écrire les données dans le fichier
//                                fileWriter.write(data);
//                                fileWriter.newLine();
//                                fileWriter.flush();
//                            }
//                        }
//
//                        // Fermeture de la connexion client
//                        clientSocket.close();
//                        Log.d(TAG, "Connexion fermée");
//                    } catch (IOException e) {
//                        Log.e(TAG, "Erreur avec le client : " + e.getMessage());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "Erreur serveur : " + e.getMessage());
//        } finally {
//            stopSelf(); // Arrête le service en cas de problème
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "Service arrêté");
//        isRunning = false;
//
//        // Fermeture du serveur si nécessaire
//        try {
//            if (serverSocket != null && !serverSocket.isClosed()) {
//                serverSocket.close();
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "Erreur lors de la fermeture du serveur : " + e.getMessage());
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null; // Pas de liaison avec d'autres composants (non utilisé ici)
//    }
//}

package com.example.applicationavirontest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiTcpServerService extends Service {

    private static final String TAG = "MultiTcpServerService";
    private static final int[] PORTS = {5036, 5037, 5038, 5039}; // Ports pour les capteurs
    private boolean isRunning = false;
    private SensorManager sensorManager; // Manager des capteurs
    private final List<ServerThread> serverThreads = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = new SensorManager();

        // Ajouter les capteurs au gestionnaire (exemple)
        sensorManager.addSensor(new SensorStatus("Capteur 1", PORTS[0]));
        sensorManager.addSensor(new SensorStatus("Capteur 2", PORTS[1]));
        sensorManager.addSensor(new SensorStatus("Capteur 3", PORTS[2]));
        sensorManager.addSensor(new SensorStatus("Capteur 4", PORTS[3]));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (!isRunning) {
            isRunning = true;
            Log.d(TAG, "Démarrage des serveurs pour plusieurs capteurs...");

            // Démarrage d'un serveur pour chaque port
            for (int port : PORTS) {
                ServerThread serverThread = new ServerThread(port);
                serverThreads.add(serverThread);
                new Thread(serverThread).start();
            }
        }

        // Retourne START_STICKY pour redémarrer le service automatiquement si le système le tue
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public class LocalBinder extends Binder {
        MultiTcpServerService getService() {
            return MultiTcpServerService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Arrêt du service multi-capteurs");
        isRunning = false;

        // Arrêter tous les serveurs
        for (ServerThread thread : serverThreads) {
            thread.stopServer();
        }
        serverThreads.clear();
    }

    /**
     * Classe interne pour gérer un serveur pour un port spécifique
     */
    private class ServerThread implements Runnable {
        private final int port;
        private ServerSocket serverSocket;

        public ServerThread(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            String filePath = getFilesDir() + "/donnees_" + port + ".txt"; // Fichier spécifique au port
            Log.d(TAG, "Serveur démarré pour le port " + port + ", fichier : " + filePath);

            try {
                serverSocket = new ServerSocket(port);
                Log.d(TAG, "Serveur en attente de connexions sur le port " + port);

                try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath, true))) {
                    while (isRunning) {
                        try {
                            // Attente d'une connexion client
                            Socket clientSocket = serverSocket.accept();
                            Log.d(TAG, "Connexion établie avec " + clientSocket.getInetAddress() + " sur le port " + port);
                            sensorManager.setSensorConnected(port, true);

                            // Lecture des données reçues
                            try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                                String data;
                                while ((data = input.readLine()) != null) {
                                    Log.d(TAG, "Données reçues sur le port " + port + " : " + data);

                                    // Écrire les données dans le fichier
                                    fileWriter.write(data);
                                    fileWriter.newLine();
                                    fileWriter.flush();
                                }
                            }
                            clientSocket.close();
                            Log.d(TAG, "Connexion fermée sur le port " + port);
                        } catch (IOException e) {
                            Log.e(TAG, "Erreur avec le client sur le port " + port + " : " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Erreur serveur sur le port " + port + " : " + e.getMessage());
            } finally {
                sensorManager.setSensorConnected(port, false);
                stopServer();
            }
        }

        public void stopServer() {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
                Log.d(TAG, "Serveur arrêté pour le port " + port);
            } catch (IOException e) {
                Log.e(TAG, "Erreur lors de la fermeture du serveur pour le port " + port + " : " + e.getMessage());
            }
        }
    }
}
