package com.example.adoptme;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password, passwordVerification;
    Button submitBtn;

    TextView goToLogIn;
    private DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sessionManager = new SessionManager(this);
        name = findViewById(R.id.signUpName);
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        passwordVerification = findViewById(R.id.signUpPasswordVerification);
        submitBtn = findViewById(R.id.signUpBtn);
        goToLogIn = findViewById(R.id.signUpToLogIn);

        databaseHelper = new DatabaseHelper(this);
        submitBtn.setOnClickListener(view -> {
            String emailStr, passwordStr, passwordVerStr,nameStr;
            nameStr=name.getText().toString();
            emailStr = email.getText().toString();
            passwordStr = password.getText().toString();
            passwordVerStr = passwordVerification.getText().toString();

            // Έλενχος για το αν κάποιο απο τα παιδία είναι άδιο
            if (emailStr.equals("") || passwordStr.equals("") || passwordVerStr.equals(""))
                Toast.makeText(SignUpActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            else {

                // Έλενχος αν τα πεδία password και passwordVerification έχουν τις ίδιες τιμές
                if (passwordStr.equals(passwordVerStr)) {
                    // Έλενχος αν το email χρησιμοποιείτε ήδη
                    Boolean checkUserEmail = databaseHelper.checkEmail(emailStr);
                    if (!checkUserEmail) {

                        // Δημιουργία profile για τον χρήστη
                        int insertId = databaseHelper.createProfile(emailStr, passwordStr,nameStr);
                        if (insertId != -1) {
                            sessionManager.saveSession(new User(insertId, nameStr, emailStr, passwordStr));
                            Toast.makeText(SignUpActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Έναρξη του activity LoginActivity και τερματισμος του τορινού
    public void goToLogInPage(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}