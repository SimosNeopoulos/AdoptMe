package com.example.adoptme;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MyProfileActivity extends AppCompatActivity {


    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    EditText name, email, password, passwordVerification;
    Button submitBtn;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

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

        setUpToolbar();
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.mainpage){
                Intent intentMain = new Intent(MyProfileActivity.this, MainActivity.class);
                startActivity(intentMain);

            }else if(menuItem.getItemId() == R.id.my_profile){
                Intent intentDonation = new Intent(MyProfileActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);
            }else if(menuItem.getItemId() == R.id.logout){
                sessionManager.deleteSession();
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(MyProfileActivity.this, LoginActivity.class);
                startActivity(intentDonation);
                finish();
            }
            return false;
        });

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        Toolbar toolbar = findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState( );
    }

    private void writeAccountData() {
        ArrayList<String> userData = databaseHelper.getUserById(sessionManager.getSessionId());
        if (userData == null)
            return;
        email.setText(userData.get(0));
        password.setText(userData.get(1));
        name.setText(userData.get(2));
    }

    public void editProfile(View view) {
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

        boolean updateSuccessful = databaseHelper.updateProfile(Integer.toString(sessionManager.getSessionId()), email, password, name);
        if (updateSuccessful) {
            Toast.makeText(this, "Τα στοιχεία του profile ενημερώθηκαν επιτυχώς", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Παρουσιάστηκε κάποιο πρόβλημα στην ενημέρωση των στοιχείων", Toast.LENGTH_SHORT).show();
            this.passwordVerification.setText("");
        }
    }
}