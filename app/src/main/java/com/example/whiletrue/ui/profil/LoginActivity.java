package com.example.whiletrue.ui.profil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.whiletrue.MainActivity;
import com.example.whiletrue.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;

    FirebaseAuth auth;
    DatabaseReference users;
    FirebaseUser currentUser;

    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        email =findViewById(R.id.email);
        password=findViewById(R.id.password);


        layout = findViewById(R.id.root);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference("User");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void regClick(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }

    public void signIn(View view) {
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if(TextUtils.isEmpty(emailS)) {
            Snackbar.make(layout, "Enter your email", Snackbar.LENGTH_SHORT);
            return;
        }

        if(TextUtils.isEmpty(passwordS)) {
            Snackbar.make(layout, "Enter your password", Snackbar.LENGTH_SHORT).show();
        }

        auth.signInWithEmailAndPassword(emailS, passwordS)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(layout, "Failed to login. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}