package com.example.adoptme;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ViewPostActivity extends AppCompatActivity {
    SessionManager sessionManager;

    DatabaseHelper databaseHelper;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        setUpToolbar();
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.mainpage){
                Intent intentMain = new Intent(ViewPostActivity.this, MainActivity.class);
                startActivity(intentMain);

            }else if(menuItem.getItemId() == R.id.my_profile){
                Intent intentDonation = new Intent(ViewPostActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);
            }else if(menuItem.getItemId() == R.id.logout){
                sessionManager.deleteSession();
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(ViewPostActivity.this, LoginActivity.class);
                startActivity(intentDonation);
            }
            return false;
        });

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        Toolbar toolbar = findViewById(R.id.viewPostToolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState( );
    }

}