package com.example.applicationavirontest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordverificationEditText;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        // Initialiser les vues
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordverificationEditText = findViewById(R.id.passwordverificationEditText);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Définir le gestionnaire d'événements pour le bouton de création de compte
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenir les valeurs des champs d'entrée
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String password_verification = passwordverificationEditText.getText().toString().trim();

                // Valider les entrées
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(CreateAccountActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(CreateAccountActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(password_verification)) {
                    Toast.makeText(CreateAccountActivity.this, "The passwords don't match", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Répertorier le nouvel utilisateur dans une BDD
                try {
                    // Créer une chaîne représentant le nouvel utilisateur
                    String userRecord = username + ":" + password + "\n";

                    // Écrire dans un fichier interne
                    FileOutputStream fos = openFileOutput("BDD.txt", MODE_APPEND);  // Utiliser MODE_APPEND pour ajouter sans écraser le fichier existant
                    fos.write(userRecord.getBytes());
                    fos.close();

                    // Succès de la création du compte
                    Toast.makeText(CreateAccountActivity.this, "Your account was successfully created.", Toast.LENGTH_SHORT).show();

                    // Rediriger vers une autre activité si besoin
                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Ferme la page actuelle
                } catch (Exception e) {
                    // Gérer les erreurs
                    Toast.makeText(CreateAccountActivity.this, "An error occurred while creating your account. Please try again.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Log l'erreur pour faciliter le débogage
                }
            }
        });
    }
}
