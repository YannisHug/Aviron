package com.example.applicationavirontest;

import java.net.Socket;

public class Sensor {
    public enum StatutConnexion {
        CONNECTE, DECONNECTE;
    }
    public enum StatutCalibration {
        CALIBRE, NON_CALIBRE;
    }

    private String id;
    private StatutConnexion statutConnexion;
    private StatutCalibration statutCalibration;
    private String port;
    private Socket socket;

    public Sensor(String id, StatutConnexion statutConnexion, StatutCalibration statutCalibration, String port, Socket socket) {
        this.id = id;
        this.statutConnexion = statutConnexion;
        this.statutCalibration = statutCalibration;
        this.port = port;
        this.socket = socket;
    }

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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
