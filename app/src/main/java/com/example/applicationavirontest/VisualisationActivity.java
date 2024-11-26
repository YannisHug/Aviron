package com.example.applicationavirontest;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VisualisationActivity extends AppCompatActivity {

    private TextView textViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation);

        // Initialisation de la TextView pour afficher les données
        textViewData = findViewById(R.id.textViewData);

        // Lire et afficher les données du fichier "data.txt"
        readDataFromAssets();
    }

    private void readDataFromAssets() {
        try {
            // Ouverture du fichier "data.txt" depuis le dossier assets
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("data.txt")));
            StringBuilder content = new StringBuilder();
            String line;

            // Lecture de chaque ligne et ajout au StringBuilder
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            // Affichage du contenu dans le TextView
            textViewData.setText(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la lecture du fichier", Toast.LENGTH_SHORT).show();
        }
    }
}
