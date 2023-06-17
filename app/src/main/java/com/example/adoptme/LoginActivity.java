package com.example.adoptme;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    Button b1;
    EditText email;
    EditText password;
    TextView logInText;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(LoginActivity.this);
        email = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.PasswordEmail);
        logInText = findViewById(R.id.logInText);
        b1 = findViewById(R.id.button);
        databaseHelper = new DatabaseHelper(this);
        b1.setOnClickListener(this::logInUser);
        logInText.setOnClickListener(this::goToSignUp);
    }

    // Έναρξη του activity SignUpActivity και τερματισμος του τορινού
    public void goToSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    // Έναρξη του activity MainActivity και τερματισμος του τορινού
    private void goToMainPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void logInUser(View view) {
        String emailTxt, passwordTxt;
        emailTxt = email.getText().toString();
        passwordTxt = password.getText().toString();

        // Ελενχος αν κάποιο απο τα πεδία είναι άδιο
        if (emailTxt.equals("") || passwordTxt.equals("")) {
            Toast.makeText(LoginActivity.this, "All fields are mandatory",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        User user = databaseHelper.checkEmailPassword(emailTxt, passwordTxt);

        // Έλενχος για την ύπαρξη χρήστη
        if (user == null) {
            Toast.makeText(LoginActivity.this, "User was not found",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Δημιουργία session για τον user
        sessionManager.saveSession(user);
        goToMainPage();
    }
}
