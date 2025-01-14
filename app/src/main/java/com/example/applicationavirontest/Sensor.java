package com.example.applicationavirontest;

import java.net.Socket;

public class Sensor {
    private String id;
    private StatutConnexion statutConnexion;
    private StatutCalibration statutCalibration;
    private String port;
    private Socket clientSocket;

    // Enums pour les statuts
    public enum StatutConnexion {
        CONNECTE,
        NON_CONNECTE
    }

    public enum StatutCalibration {
        CALIBRE,
        NON_CALIBRE
    }

    // Constructeur
    public Sensor(String id, StatutConnexion statutConnexion, StatutCalibration statutCalibration, String port, Socket clientSocket) {
        this.id = id;
        this.statutConnexion = statutConnexion;
        this.statutCalibration = statutCalibration;
        this.port = port;
        this.clientSocket = clientSocket;
    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatutConnexion getStatutConnexion() {
        return statutConnexion;
    }

    public void setStatutConnexion(StatutConnexion statutConnexion) {
        this.statutConnexion = statutConnexion;
    }

    public StatutCalibration getStatutCalibration() {
        return statutCalibration;
    }

    public void setStatutCalibration(StatutCalibration statutCalibration) {
        this.statutCalibration = statutCalibration;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
