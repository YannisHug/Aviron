package com.example.applicationavirontest;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

public class ReglageActivity extends AppCompatActivity {

    private Button btnEnregistrer, btnStopEnregistrer, btnCalibrer1, btnCalibrer2, btnCalibrer3, btnCalibrer4;
    private TextView sensor1Status, sensor2Status, sensor3Status, sensor4Status;
    private Intent intentConnection = null;
    private BroadcastReceiver sensorStatusReceiver;

    private TcpServerService tcpServerService;
    private boolean isBound = false;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TcpServerService.LocalBinder binder = (TcpServerService.LocalBinder) service;
            tcpServerService = binder.getService();
            isBound = true;

            // Maintenant, vous pouvez accéder au SensorManager
            SensorManager sensorManager = tcpServerService.getSensorManager();
            // Exemple : Afficher tous les capteurs
            List<Sensor> sensors = sensorManager.getSensors();
            for (Sensor sensor : sensors) {
                Log.d("ReglageActivity", "Sensor ID: " + sensor.getId());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            tcpServerService = null;
        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage);

        btnEnregistrer = findViewById(R.id.btn_enregistrer);
        btnStopEnregistrer = findViewById(R.id.btn_stop_enregistrer);
        btnCalibrer1 = findViewById(R.id.btn_calibrate_sensor1);
        btnCalibrer2 = findViewById(R.id.btn_calibrate_sensor2);
        btnCalibrer3 = findViewById(R.id.btn_calibrate_sensor3);
        btnCalibrer4 = findViewById(R.id.btn_calibrate_sensor4);

        sensor1Status = findViewById(R.id.sensor1_status);
        sensor2Status = findViewById(R.id.sensor2_status);
        sensor3Status = findViewById(R.id.sensor3_status);
        sensor4Status = findViewById(R.id.sensor4_status);

        // Enregistrement du BroadcastReceiver
        sensorStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int sensorNumber = intent.getIntExtra("sensorNumber", -1);
                String status = intent.getStringExtra("status");

                if (sensorNumber != -1 && status != null) {
                    updateSensorStatus(sensorNumber, status);
                }
            }
        };

        IntentFilter filter = new IntentFilter("com.example.applicationavirontest.SENSOR_STATUS");
        registerReceiver(sensorStatusReceiver, filter, Context.RECEIVER_NOT_EXPORTED);

        btnEnregistrer.setOnClickListener(v -> {
            if (intentConnection == null) {
                intentConnection = new Intent(ReglageActivity.this, TcpServerService.class);
                ContextCompat.startForegroundService(this, intentConnection);
            } else {
                Toast.makeText(ReglageActivity.this, "L'enregistrement est déjà actif", Toast.LENGTH_SHORT).show();
            }
        });

        btnStopEnregistrer.setOnClickListener(v -> {
            if (intentConnection != null) {
                stopService(intentConnection);
                intentConnection = null;
            } else {
                Toast.makeText(ReglageActivity.this, "Aucun enregistrement est en cours", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalibrer1.setOnClickListener(v -> {
            if (tcpServerService != null) {
                SensorManager sensorManager = tcpServerService.getSensorManager();
                // Exemple d'ID pour le capteur 1 (adapter selon votre logique d'ID)
                String sensorId = "capteur1";
                sensorManager.sendCommandToSensor("CALIBRAGE", sensorManager.findSensorById(sensorId).getSocket());
            } else {
                Toast.makeText(this, "Le service n'est pas actif", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalibrer2.setOnClickListener(v -> {
            if (tcpServerService != null) {
                SensorManager sensorManager = tcpServerService.getSensorManager();
                // Exemple d'ID pour le capteur 1 (adapter selon votre logique d'ID)
                String sensorId = "capteur2";
                sensorManager.sendCommandToSensor("CALIBRAGE", sensorManager.findSensorById(sensorId).getSocket());
                Toast.makeText(this, "Commande de calibrage envoyée au capteur 2", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Le service n'est pas actif", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalibrer3.setOnClickListener(v -> {
            if (tcpServerService != null) {
                SensorManager sensorManager = tcpServerService.getSensorManager();
                // Exemple d'ID pour le capteur 1 (adapter selon votre logique d'ID)
                String sensorId = "capteur3";
                sensorManager.sendCommandToSensor( "CALIBRAGE", sensorManager.findSensorById(sensorId).getSocket());
                Toast.makeText(this, "Commande de calibrage envoyée au capteur 3", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Le service n'est pas actif", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalibrer4.setOnClickListener(v -> {
            if (tcpServerService != null) {
                SensorManager sensorManager = tcpServerService.getSensorManager();
                // Exemple d'ID pour le capteur 1 (adapter selon votre logique d'ID)
                String sensorId = "capteur4";
                sensorManager.sendCommandToSensor("CALIBRAGE", sensorManager.findSensorById(sensorId).getSocket());
                Toast.makeText(this, "Commande de calibrage envoyée au capteur 4", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Le service n'est pas actif", Toast.LENGTH_SHORT).show();
            }
        });
        // Lier le service
        Intent intent = new Intent(this, TcpServerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void updateSensorStatus(int sensorNumber, String newStatus) {
        TextView sensorStatus = null;
        switch (sensorNumber) {
            case 1: sensorStatus = sensor1Status; break;
            case 2: sensorStatus = sensor2Status; break;
            case 3: sensorStatus = sensor3Status; break;
            case 4: sensorStatus = sensor4Status; break;
        }
        if (sensorStatus != null) {
            sensorStatus.setText(newStatus);
        }
    }

    // Méthode pour envoyer un message à votre service TcpServer
    private void sendMessageToServer(String message) {
        if (intentConnection != null) {
            intentConnection.putExtra("message", message);
            ContextCompat.startForegroundService(this, intentConnection);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }
}
