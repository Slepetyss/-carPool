package com.example.carpool.activiities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carpool.R;
import com.example.carpool.activiities.MainActivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityAuth extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailField = findViewById(R.id.editTextEmail);
        passwordField = findViewById(R.id.editTextPassword);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            System.out.println(currentUser.getEmail());
        } else {
            System.out.println("No users are logged");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }



    public void logIn(View v) {
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        mAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGN IN", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Success signing in!", Toast.LENGTH_LONG).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SIGN IN", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void sinUp(View v) {
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("SIGN UP", "Successfully signed up the user");
                    Toast.makeText(getApplicationContext(), "Success signing up!", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SIGN UP", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });
    }

    public void updateUI(FirebaseUser currentUser) {

        // Check if current user is already on fireBase database
        // If current user is on firebase database then go to mainActivity page (main page)
        // if current user is not on firebase database then go to User Info page and wait for update button to be clicked
        // When update button is clicked, go back to mainActivity page with current user information
        Intent intentMain = new Intent(this, MainActivity.class);
        Intent intentUserInfo = new Intent(this, UserInfo.class);

        if (currentUser != null) {
            DocumentReference docIdRef = firestore.collection("Users").document(currentUser.getUid());
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("EXITS", "Document exist!");
                            startActivity(intentMain);
                        } else {
                            Log.d("NOT EXITS", "Document does not exist!");
                            startActivity(intentUserInfo);
                        }
                    } else {
                        Log.d("FAILED", "Failed with: ", task.getException());
                    }
                }
            });
        }
    }


}