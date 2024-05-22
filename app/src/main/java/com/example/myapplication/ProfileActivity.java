package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextOldPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSave;
    private Button buttonChangePassword;
    private Button buttonBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextName);
        editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSave = findViewById(R.id.buttonSave);
//        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        buttonBack = findViewById(R.id.buttonBack);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
                changePassword();
            }
        });

//        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changePassword();
//            }
//        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackToMainMenu();
            }
        });
    }

    private void saveChanges() {
        String newName = editTextName.getText().toString().trim();

        if (TextUtils.isEmpty(newName)) {
            Toast.makeText(this, "Please enter a new name", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Get the document reference for the current user
            DocumentReference userRef = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.getUid());

            // Update the name field
            userRef.update("name", newName)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity.this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Failed to update name: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void changePassword() {
        String oldPassword = editTextOldPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Reauthenticate user with their old password
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
            user.reauthenticate(credential)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Update user's password
                            user.updatePassword(newPassword)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ProfileActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfileActivity.this, "Failed to update password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Failed to reauthenticate: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void navigateBackToMainMenu() {
        Intent intent = new Intent(ProfileActivity.this, MainPageActivity.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent navigating back to it
    }
}
