//THE MAIN PAGE OF THE APP
package com.example.adoptme;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<Post> posts;
    PostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        if (sessionManager.getSessionId() == -1) {
            redirectToSignUp();
        }

        setUpToolbar();
        databaseHelper = new DatabaseHelper(this);
        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if(menuItem.getItemId() == R.id.mainpage){
                Intent intentMain = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentMain);

            }else if(menuItem.getItemId() == R.id.my_profile){
                Intent intentDonation = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);
            }else if(menuItem.getItemId() == R.id.logout){
                sessionManager.deleteSession();
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentDonation);
            }
            return false;
        });

//        recyclerView = findViewById(R.id.recyclerview);
//        posts = databaseHelper.getPosts(null,null,null, 0,0);
//        adapter = new PostAdapter(this, posts);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    private void redirectToSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    public void redirectToAddPost(View view) {
        Intent intent = new Intent(getApplicationContext(), AddPostActivity.class);
        startActivity(intent);
        finish();
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.my_drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
