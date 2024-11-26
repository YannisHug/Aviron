package com.example.applicationavirontest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnVisualization).setOnClickListener(v -> startActivity(new Intent(this, VisualisationActivity.class)));
        findViewById(R.id.btnAideDeCoach).setOnClickListener(v -> startActivity(new Intent(this, AideDeCoachActivity.class)));
        findViewById(R.id.btnReglage).setOnClickListener(v -> startActivity(new Intent(this, ReglageActivity.class)));
        findViewById(R.id.btnBasededonnees).setOnClickListener(v -> startActivity(new Intent(this, BasededonneesActivity.class)));
        findViewById(R.id.btnDeconnexion).setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }
}