package com.example.applicationavirontest;

import java.util.ArrayList;
import java.util.List;

public class SensorManager {
    private List<SensorStatus> sensors;

    public SensorManager() {
        this.sensors = new ArrayList<>();
    }

    public void addSensor(SensorStatus sensor) {
        sensors.add(sensor);
    }

    public SensorStatus getSensorByPort(int port) {
        for (SensorStatus sensor : sensors) {
            if (sensor.getPort() == port) {
                return sensor;
            }
        }
        return null; // Retourne null si aucun capteur n'est trouvé pour ce port
    }

    public List<SensorStatus> getSensors() {
        return sensors;
    }

    public void setSensorConnected(int port, boolean isConnected) {
        SensorStatus sensor = getSensorByPort(port);
        if (sensor != null) {
            sensor.setConnected(isConnected);
        }
    }

    public void setSensorCalibrated(int port, boolean isCalibrated) {
        SensorStatus sensor = getSensorByPort(port);
        if (sensor != null) {
            sensor.setCalibrated(isCalibrated);
        }
    }

    public List<SensorStatus> getAllSensors() {
        return new ArrayList<>(sensors);
    }

}
