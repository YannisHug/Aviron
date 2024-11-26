package com.example.applicationavirontest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialiser les vues
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Définir le gestionnaire d'événements pour le bouton de connexion
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenir les valeurs des champs d'entrée
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Valider les entrées
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Logique de connexion simulée : lecture du fichier et vérification des identifiants
                try {
                    FileInputStream fis = openFileInput("BDD.txt");  // Lecture du fichier de données
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

                    String line;
                    boolean loginSuccessful = false;
                    while ((line = reader.readLine()) != null) {
                        // Séparer les éléments de la ligne par ":"
                        String[] credentials = line.split(":");
                        if (credentials.length == 2) {
                            String storedUsername = credentials[0].trim();
                            String storedPassword = credentials[1].trim();

                            // Vérifier si les identifiants correspondent
                            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                                // Connexion réussie
                                Toast.makeText(LoginActivity.this, "Login successful, welcome " + username, Toast.LENGTH_SHORT).show();
                                // Rediriger vers une autre activité
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Ferme la page de connexion
                                loginSuccessful = true;
                                break; // Sort de la boucle si l'utilisateur est trouvé
                            }
                        }
                    }

                    // Si la connexion échoue après avoir parcouru toutes les lignes
                    if (!loginSuccessful) {
                        Toast.makeText(LoginActivity.this, "Login failed. Create an account first.", Toast.LENGTH_SHORT).show();
                    }

                    // Fermer le flux après la lecture
                    reader.close();
                } catch (IOException e) {
                    // En cas d'erreur de lecture du fichier
                    Toast.makeText(LoginActivity.this, "Error reading the file", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        // Définir le gestionnaire d'événements pour le bouton de création de compte
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers une activité de création de compte
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
