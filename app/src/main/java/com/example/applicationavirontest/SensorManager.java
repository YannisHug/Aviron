package com.example.applicationavirontest;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SensorManager {
    private List<Sensor> sensors = new ArrayList<>();

    /**
     * Retourne une copie de la liste des capteurs.
     */
    public List<Sensor> getSensors() {
        return new ArrayList<>(sensors);
    }

    /**
     * Ajoute un capteur à la liste.
     */
    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    /**
     * Supprime un capteur de la liste et ferme son socket si nécessaire.
     */
    public void removeSensor(Sensor sensor) {
        try {
            if (sensor.getClientSocket() != null && !sensor.getClientSocket().isClosed()) {
                sensor.getClientSocket().close();
                Log.i("SensorManager", "Socket fermé pour le capteur " + sensor.getId());
            }
        } catch (IOException e) {
            Log.e("SensorManager", "Erreur lors de la fermeture du socket: " + e.getMessage(), e);
        }
        sensors.remove(sensor);
    }

    /**
     * Recherche un capteur par son ID.
     */
    public Sensor findSensorById(String id) {
        for (Sensor sensor : sensors) {
            if (sensor.getId().equals(id)) {
                return sensor;
            }
        }
        return null;
    }

    /**
     * Marque un capteur comme déconnecté et ferme son socket.
     */
    public void markSensorAsDisconnected(String id) {
        Sensor sensor = findSensorById(id);
        if (sensor != null) {
            try {
                if (sensor.getClientSocket() != null && !sensor.getClientSocket().isClosed()) {
                    sensor.getClientSocket().close();
                    Log.i("SensorManager", "Socket fermé pour le capteur déconnecté " + id);
                }
            } catch (IOException e) {
                Log.e("SensorManager", "Erreur lors de la fermeture du socket pour le capteur " + id + ": " + e.getMessage(), e);
            }
            sensor.setStatutConnexion(Sensor.StatutConnexion.NON_CONNECTE);
            Log.i("SensorManager", "Capteur " + id + " marqué comme déconnecté");
        }
    }

    /**
     * Envoie une commande au capteur via son socket.
     */
    public void sendCommandToSensor(String command, Socket sensorSocket) {
        new Thread(() -> {
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sensorSocket.getOutputStream())), true);
                writer.println(command);
                writer.flush();
                Log.i("SensorManager", "Commande envoyée: " + command);
            } catch (IOException e) {
                Log.e("SensorManager", "Erreur lors de l'envoi de la commande: " + e.getMessage(), e);
            }
        }).start();
    }

    /**
     * Déconnecte tous les capteurs et ferme leurs sockets.
     */
    public void disconnectAllSensors() {
        for (Sensor sensor : sensors) {
            markSensorAsDisconnected(sensor.getId());
        }
        sensors.clear(); // Supprime tous les capteurs après déconnexion
        Log.i("SensorManager", "Tous les capteurs ont été déconnectés");
    }
}
