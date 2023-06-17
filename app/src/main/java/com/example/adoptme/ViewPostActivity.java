package com.example.adoptme;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// Activity για αναλυτική προβολή Post
public class ViewPostActivity extends AppCompatActivity {
    SessionManager sessionManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    TextView petName, species, region, petAge, phoneNumber, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        setUpToolbar();
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            // Μεταφορά στο MainActivity
            if (menuItem.getItemId() == R.id.mainpage) {
                Intent intentMain = new Intent(ViewPostActivity.this, MainActivity.class);
                startActivity(intentMain);

            // Μεταφορά στο MyProfileActivity
            } else if (menuItem.getItemId() == R.id.my_profile) {
                Intent intentDonation = new Intent(ViewPostActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);

            // Αποσύνδεση χρήστη
            } else if (menuItem.getItemId() == R.id.logout) {
                sessionManager.deleteSession();
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(ViewPostActivity.this, LoginActivity.class);
                startActivity(intentDonation);
                finish();
            }
            return false;
        });
        initialiseTextViews();
        setTextViews(getIntent());
    }

    private void initialiseTextViews() {
        petName = findViewById(R.id.viewPostName);
        species = findViewById(R.id.viewPostSpecies);
        region = findViewById(R.id.viewPostTown);
        petAge = findViewById(R.id.viewPostAge);
        phoneNumber = findViewById(R.id.viewPostPhone);
        description = findViewById(R.id.viewPostDescription);
    }

    private void setTextViews(Intent intent) {
        // Αν δεν υπάρχει intent στα ματάει η κλήση της συνάρτησης
        if (intent == null) {
            return;
        }

        ArrayList<String> data = intent.getStringArrayListExtra("inspect");
        if (data == null)
            return;

        // Συμπληρώση των παιδίων με τα δεδομένα του post
        petName.setText(data.get(1));
        petAge.setText(data.get(2));
        species.setText(data.get(3));
        description.setText(data.get(4));
        phoneNumber.setText(data.get(5));
        region.setText(data.get(6));
    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        Toolbar toolbar = findViewById(R.id.viewPostToolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

}