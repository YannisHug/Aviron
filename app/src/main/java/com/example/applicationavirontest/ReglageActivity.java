package com.example.applicationavirontest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class ReglageActivity extends AppCompatActivity {

    // Déclaration des éléments de la vue
    private Button btnEnregistrer, btnSelectTxt;
    private TextView wifiStatus, sensor1Status, sensor2Status, sensor3Status, sensor4Status;
    private ScrollView sensorScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglage);

        // Initialisation des éléments de la vue
        btnEnregistrer = findViewById(R.id.btn_enregistrer);
        btnSelectTxt = findViewById(R.id.btn_select_txt;

        wifiStatus = findViewById(R.id.wifi_status);
        sensor1Status = findViewById(R.id.sensor1_status);
        sensor2Status = findViewById(R.id.sensor2_status);
        sensor3Status = findViewById(R.id.sensor3_status);
        sensor4Status = findViewById(R.id.sensor4_status);

        sensorScrollView = findViewById(R.id.sensor_scroll_view);

        // Gestion du bouton "Connecter"
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToWifi();
            }
        });
    }
}
