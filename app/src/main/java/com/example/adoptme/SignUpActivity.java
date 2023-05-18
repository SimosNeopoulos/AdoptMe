package com.example.adoptme;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

        databaseHelper = new DatabaseHelper(this);
        submitBtn.setOnClickListener(view -> {
            String emailStr, passwordStr, passwordVerStr,nameStr;
            nameStr=name.getText().toString();
            emailStr = email.getText().toString();
            passwordStr = password.getText().toString();
            passwordVerStr = passwordVerification.getText().toString();
            if (emailStr.equals("") || passwordStr.equals("") || passwordVerStr.equals(""))
                Toast.makeText(SignUpActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            else {
                if (passwordStr.equals(passwordVerStr)) {
                    Boolean checkUserEmail = databaseHelper.checkEmail(emailStr);
                    if (!checkUserEmail) {
                        Boolean insert = databaseHelper.insertData(emailStr, passwordStr,nameStr);
                        if (insert) {
                            Toast.makeText(SignUpActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
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



    public void goToLogInPage(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}