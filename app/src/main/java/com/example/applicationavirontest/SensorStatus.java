package com.example.applicationavirontest;

public class SensorStatus {
    private String name;
    private int port;
    private boolean isConnected;
    private boolean isCalibrated;

    public SensorStatus(String name, int port) {
        this.name = name;
        this.port = port;
        this.isConnected = false;
        this.isCalibrated = false;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isCalibrated() {
        return isCalibrated;
    }

    public void setCalibrated(boolean calibrated) {
        isCalibrated = calibrated;
    }

    public String getStatusText() {
        return name + "\nStatut : " + (isConnected ? "Connecté" : "Déconnecté") +
                "\nCalibration : " + (isCalibrated ? "Calibré" : "Non calibré") +
                "\nPort : " + port;
    }

    class TcpClientHandler(private val dataInputStream: DataInputStream, private val dataOutputStream: DataOutputStream)
}
