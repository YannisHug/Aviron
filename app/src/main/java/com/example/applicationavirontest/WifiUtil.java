package com.example.applicationavirontest;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

public class WifiUtil {

    // Méthode pour récupérer l'adresse IPv4 WiFi
    public static String getWifiIpv4Address(Context context) {
        // Récupérer le WifiManager
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // Vérifier si le WiFi est activé et connecté
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return Formatter.formatIpAddress(ipAddress); // Formater l'adresse IP en chaîne
        } else {
            return null; // Retourne null si le Wi-Fi est désactivé ou non connecté
        }
    }
}
