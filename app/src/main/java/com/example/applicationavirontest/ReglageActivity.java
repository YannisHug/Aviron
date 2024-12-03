package com.example.applicationavirontest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ReglageActivity extends AppCompatActivity {

    private Button btnEnregistrer, btnStopEnregistrer;
    private TextView wifiStatus, sensor1Status, sensor2Status, sensor3Status, sensor4Status;
    private Intent intentConnection = null;
    private SensorManager sensorManager;
    private List<SensorStatus> sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage);

        // Initialisation des éléments de la vue
        btnEnregistrer = findViewById(R.id.btn_enregistrer);
        btnStopEnregistrer = findViewById(R.id.btn_stop_enregistrer);

        wifiStatus = findViewById(R.id.wifi_status);
        sensor1Status = findViewById(R.id.sensor1_status);
        sensor2Status = findViewById(R.id.sensor2_status);
        sensor3Status = findViewById(R.id.sensor3_status);
        sensor4Status = findViewById(R.id.sensor4_status);


        // Gestion du bouton "Enregistrer"
        btnEnregistrer.setOnClickListener(v -> {
            intentConnection = new Intent(ReglageActivity.this, TcpServerService.class);
            startService(intentConnection);
            Toast.makeText(ReglageActivity.this, "Enregistrement démarré", Toast.LENGTH_SHORT).show();
            // Mettre à jour l'affichage initial
            updateSensorStatus();
        });

        // Gestion du bouton "Stop enregistrer"
        btnStopEnregistrer.setOnClickListener(v -> {
            if (intentConnection != null) {
                stopService(intentConnection);
                Toast.makeText(ReglageActivity.this, "Enregistrement arrêté", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ReglageActivity.this, "Aucun enregistrement actif", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSensorStatus() {
        sensor1Status.setText(sensors.get(0).getStatusText());
        sensor2Status.setText(sensors.get(1).getStatusText());
        sensor3Status.setText(sensors.get(2).getStatusText());
        sensor4Status.setText(sensors.get(3).getStatusText());
    }

    public void setSensorConnected(int sensorIndex, boolean isConnected) {
        if (sensorIndex >= 0 && sensorIndex < sensors.size()) {
            sensors.get(sensorIndex).setConnected(isConnected);
            updateSensorStatus();
        }
    }

    public void setSensorCalibrated(int sensorIndex, boolean isCalibrated) {
        if (sensorIndex >= 0 && sensorIndex < sensors.size()) {
            sensors.get(sensorIndex).setCalibrated(isCalibrated);
            updateSensorStatus();
        }
    }
}
