package com.example.adoptme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password, passwordVerification;
    Button submitBtn;
    FirebaseAuth mAuth;
    TextView goToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.signUpName);
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        passwordVerification = findViewById(R.id.signUpPasswordVerification);
        submitBtn = findViewById(R.id.signUpBtn);
        goToLogIn = findViewById(R.id.signUpToLogIn);
        mAuth = FirebaseAuth.getInstance();

    }

    public void signUp(View view) {
        String email, name, password, passwordVerification;
        name = String.valueOf(this.name.getText());
        email = String.valueOf(this.email.getText());
        password = String.valueOf(this.password.getText());
        passwordVerification = String.valueOf(this.passwordVerification.getText());

        // TODO: Να το ολοκληρώσω
        if (!checkForEmptyStrings(name, email, password, passwordVerification)) {

        }

        // TODO: Να το ολοκληρώσω
        if (!password.equals(passwordVerification)) {

        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // TODO: Να το ολοκληρώσω
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                        }
                    }
                });
    }

    private boolean checkForEmptyStrings(String name, String email, String password, String password2) {
        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) && !TextUtils.isEmpty(password2);
    }

    public void goToLogInPage(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}