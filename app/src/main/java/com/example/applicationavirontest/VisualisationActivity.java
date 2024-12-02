package com.example.applicationavirontest;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
        readDataFromInternalStorage();
    }

    private void readDataFromInternalStorage() {
        try {
            // Chemin complet vers le fichier dans le dossier de fichiers internes
            File file = new File(getFilesDir(), "data.txt"); //donnees_{Port du capteur}.txt pour récupérer les données du capteurs considéré

            // Vérifiez si le fichier existe
            if (!file.exists()) {
                Toast.makeText(this, "Fichier non trouvé", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ouverture du fichier pour la lecture
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
