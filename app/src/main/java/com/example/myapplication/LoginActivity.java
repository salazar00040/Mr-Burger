package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button loginButton;
    private Button createAccountButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
            // Log any errors during Firebase initialization
            Log.e("FirebaseInit", "Error initializing Firebase: " + e.getMessage());
        }

        // Get FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        createAccountButton = findViewById(R.id.buttonCreateAccount);

        loginButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Perform login authentication using Firebase Authentication
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Login successful, navigate to main activity
                                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Login failed, display error message
                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                Log.e("LoginActivity", "Login failed: " + task.getException().getMessage());
                            }
                        }
                    });
        });

        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}
