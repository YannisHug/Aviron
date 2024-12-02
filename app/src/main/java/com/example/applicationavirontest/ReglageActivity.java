//package com.example.applicationavirontest;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReglageActivity extends AppCompatActivity {
//
//    private Button btnEnregistrer, btnStopEnregistrer;
//    private TextView wifiStatus, sensor1Status, sensor2Status, sensor3Status, sensor4Status;
//    private Intent intentConnection = null;
//    private SensorManager sensorManager;
//    private List<SensorStatus> sensors;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reglage);
//
//        // Initialisation des éléments de la vue
//        btnEnregistrer = findViewById(R.id.btn_enregistrer);
//        btnStopEnregistrer = findViewById(R.id.btn_stop_enregistrer);
//
//        wifiStatus = findViewById(R.id.wifi_status);
//        sensor1Status = findViewById(R.id.sensor1_status);
//        sensor2Status = findViewById(R.id.sensor2_status);
//        sensor3Status = findViewById(R.id.sensor3_status);
//        sensor4Status = findViewById(R.id.sensor4_status);
//
//
//        // Gestion du bouton "Enregistrer"
//        btnEnregistrer.setOnClickListener(v -> {
//            intentConnection = new Intent(ReglageActivity.this, MultiTcpServerService.class);
//            startService(intentConnection);
//            Toast.makeText(ReglageActivity.this, "Enregistrement démarré", Toast.LENGTH_SHORT).show();
//            // Mettre à jour l'affichage initial
//            updateSensorStatus();
//        });
//
//        // Gestion du bouton "Stop enregistrer"
//        btnStopEnregistrer.setOnClickListener(v -> {
//            if (intentConnection != null) {
//                stopService(intentConnection);
//                Toast.makeText(ReglageActivity.this, "Enregistrement arrêté", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(ReglageActivity.this, "Aucun enregistrement actif", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void updateSensorStatus() {
//        sensor1Status.setText(sensors.get(0).getStatusText());
//        sensor2Status.setText(sensors.get(1).getStatusText());
//        sensor3Status.setText(sensors.get(2).getStatusText());
//        sensor4Status.setText(sensors.get(3).getStatusText());
//    }
//
//    public void setSensorConnected(int sensorIndex, boolean isConnected) {
//        if (sensorIndex >= 0 && sensorIndex < sensors.size()) {
//            sensors.get(sensorIndex).setConnected(isConnected);
//            updateSensorStatus();
//        }
//    }
//
//    public void setSensorCalibrated(int sensorIndex, boolean isCalibrated) {
//        if (sensorIndex >= 0 && sensorIndex < sensors.size()) {
//            sensors.get(sensorIndex).setCalibrated(isCalibrated);
//            updateSensorStatus();
//        }
//    }
//}

package com.example.applicationavirontest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    private MultiTcpServerService service;
    private boolean bound = false;
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
            intentConnection = new Intent(ReglageActivity.this, MultiTcpServerService.class);
            bindService(intentConnection, serviceConnection, BIND_AUTO_CREATE);
            Toast.makeText(ReglageActivity.this, "Enregistrement démarré", Toast.LENGTH_SHORT).show();
        });

        // Gestion du bouton "Stop enregistrer"
        btnStopEnregistrer.setOnClickListener(v -> {
            if (bound) {
                unbindService(serviceConnection);
                bound = false;
                Toast.makeText(ReglageActivity.this, "Enregistrement arrêté", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ReglageActivity.this, "Aucun enregistrement actif", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (bound && service != null) {
            // Accédez au SensorManager depuis le service lié
            SensorManager sensorManager = service.getSensorManager();
            // Utilisez sensorManager pour mettre à jour l'interface utilisateur
            updateSensorStatus(sensorManager);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MultiTcpServerService.LocalBinder binder = (MultiTcpServerService.LocalBinder) iBinder;
            service = binder.getService();
            bound = true;

            // Vous pouvez maintenant accéder au SensorManager
            SensorManager sensorManager = service.getSensorManager();
            updateSensorStatus(sensorManager);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }
    }

    private void updateSensorStatus(SensorManager sensorManager) {
        if (sensorManager != null) {
            sensors = sensorManager.getSensors(); // Assurez-vous que cette méthode retourne la liste des capteurs
            if (sensors != null && sensors.size() >= 4) {
                sensor1Status.setText(sensors.get(0).getStatusText());
                sensor2Status.setText(sensors.get(1).getStatusText());
                sensor3Status.setText(sensors.get(2).getStatusText());
                sensor4Status.setText(sensors.get(3).getStatusText());
            }
        }
    }
}
