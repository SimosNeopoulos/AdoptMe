package com.example.adoptme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MyProfileActivity extends AppCompatActivity {


    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    EditText name, email, password, passwordVerification;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        password = findViewById(R.id.profilePassword);
        passwordVerification = findViewById(R.id.profilePasswordVerification);
        submitBtn = findViewById(R.id.profileBtn);
        submitBtn.setOnClickListener(this::editProfile);
        writeAccountData();

    }

    private void writeAccountData() {
        ArrayList<String> userData = databaseHelper.getUserById(sessionManager.getSessionId());
        email.setText(userData.get(0));
        password.setText(userData.get(1));
        name.setText(userData.get(2));
    }

    private void editProfile(View view) {
        String name, email, password, passwordVerification;
        name = this.name.getText().toString();
        email = this.email.getText().toString();
        password = this.password.getText().toString();
        passwordVerification = this.passwordVerification.getText().toString();
        if (name.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordVerification)) {
            Toast.makeText(this, "Ο κωδικός στα πεδία \"Κωδικός\" και " +
                    "\"Επαλήθευση Κωδικου \" πρέπει να είναι ίδια", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseHelper.updateProfile(Integer.toString(sessionManager.getSessionId()), email, password, name);
    }
}