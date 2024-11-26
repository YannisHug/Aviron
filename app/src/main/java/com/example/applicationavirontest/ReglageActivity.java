package com.example.applicationavirontest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReglageActivity extends AppCompatActivity {

    private Button btnEnregistrer, btnSelectTxt;
    private TextView wifiStatus, sensor1Status, sensor2Status, sensor3Status, sensor4Status;
    private ScrollView sensorScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage);

        // Initialisation des éléments de la vue
        btnEnregistrer = findViewById(R.id.btn_enregistrer);
        btnSelectTxt = findViewById(R.id.btn_select_txt);

        wifiStatus = findViewById(R.id.wifi_status);
        sensor1Status = findViewById(R.id.sensor1_status);
        sensor2Status = findViewById(R.id.sensor2_status);
        sensor3Status = findViewById(R.id.sensor3_status);
        sensor4Status = findViewById(R.id.sensor4_status);

        sensorScrollView = findViewById(R.id.sensor_scroll_view);

        // Gestion du bouton "Enregistrer"
        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer le service qui lance le serveur TCP
                Intent intent = new Intent(ReglageActivity.this, TcpServerService.class);
                startService(intent);
            }
        });
    }
}
