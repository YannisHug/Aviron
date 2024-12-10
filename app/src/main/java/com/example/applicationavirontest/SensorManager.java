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

    public List<Sensor> getSensors() {
        return new ArrayList<>(sensors);
    }

    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        sensors.remove(sensor);
    }

    public Sensor findSensorById(String id) {
        for (Sensor sensor : sensors) {
            if (sensor.getId().equals(id)) {
                return sensor;
            }
        }
        return null;
    }

    public void sendCommandToSensor(String command, Socket sensorSocket) {
        new Thread(() -> {
            try {
                PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sensorSocket.getOutputStream())), true);
                writer.println(command);
                writer.flush();
            } catch (IOException e) {
                Log.e("SensorManager", "Error sending command: " + e.getMessage(), e);
            }
        }).start();
    }

}
