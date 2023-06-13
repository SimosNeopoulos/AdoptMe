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

public class EditPostActivity extends AppCompatActivity {

    private int postId;
    private EditText name, age, species, description, telephone, town;
    private Button editPostBtn;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //private SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
//        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        name = findViewById(R.id.editPostName);
        age = findViewById(R.id.editPostAge);
        species = findViewById(R.id.editPostSpecies);
        description = findViewById(R.id.editPostDescription);
        telephone = findViewById(R.id.editPostPhone);
        town = findViewById(R.id.editPostTown);
        editPostBtn = findViewById(R.id.editPostBtn);
        editPostBtn.setOnClickListener(this::editPost);



        Intent intent = getIntent();

        if (intent != null) {
            setTextFields(intent);
        }
        setUpToolbar();
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.mainpage){
                Intent intentMain = new Intent(EditPostActivity.this, MainActivity.class);
                startActivity(intentMain);

            }else if(menuItem.getItemId() == R.id.my_profile){
                Intent intentDonation = new Intent(EditPostActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);
            }else if(menuItem.getItemId() == R.id.logout){
                sessionManager.deleteSession();
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(EditPostActivity.this, LoginActivity.class);
                startActivity(intentDonation);
            }
            return false;
        });

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        Toolbar toolbar = findViewById(R.id.editPostToolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState( );
    }
    private void setTextFields(Intent intent) {
        ArrayList<String> data = intent.getStringArrayListExtra("postData");
        if (data == null)
            return;

        postId = Integer.parseInt(data.get(0));
        name.setText(data.get(1));
        age.setText(data.get(2));
        species.setText(data.get(3));
        description.setText(data.get(4));
        telephone.setText(data.get(5));
        town.setText(data.get(6));
    }

    private void editPost(View view) {
        String name, age, species, description, telephone, town;
        name = this.name.getText().toString();
        age = this.age.getText().toString();
        species = this.species.getText().toString();
        description = this.description.getText().toString();
        telephone = this.telephone.getText().toString();
        town = this.town.getText().toString();
        databaseHelper.updatePost(postId, town, species, name, Integer.parseInt(age), telephone, description);
    }
}