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

public class AddPostActivity extends AppCompatActivity {

    private EditText name, age, species, description, telephone, town;
    private Button createPostBtn;
    private SessionManager sessionManager;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        name = findViewById(R.id.addPostName);
        age = findViewById(R.id.addPostAge);
        species = findViewById(R.id.addPostSpecies);
        description = findViewById(R.id.addPostDescription);
        telephone = findViewById(R.id.addPostPhone);
        town = findViewById(R.id.addPostTown);
        createPostBtn = findViewById(R.id.addPostBtn);
        createPostBtn.setOnClickListener(this::addPost);
        setUpToolbar();
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.mainpage){
                Intent intentMain = new Intent(AddPostActivity.this, MainActivity.class);
                startActivity(intentMain);

            }else if(menuItem.getItemId() == R.id.my_profile){
                Intent intentDonation = new Intent(AddPostActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);
            }else if(menuItem.getItemId() == R.id.logout){
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(AddPostActivity.this, LoginActivity.class);
                startActivity(intentDonation);
            }
            return false;
        });

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        Toolbar toolbar = findViewById(R.id.addPostToolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState( );
    }


    private void addPost(View view) {
        String name, age, species, description, telephone, town;
        name = this.name.getText().toString();
        age = this.age.getText().toString();
        species = this.species.getText().toString();
        description = this.description.getText().toString();
        telephone = this.telephone.getText().toString();
        town = this.town.getText().toString();
        //TODO: Να βάλω να ελενχει αν όλα έχουν συπμληρωθει.

        databaseHelper.insertPost(town, species, name, Integer.parseInt(age), telephone,
                "image_path", sessionManager.getSessionId(), description);
    }
}