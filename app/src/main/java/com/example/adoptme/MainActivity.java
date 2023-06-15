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
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView postListView;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<Post> posts;
    PostAdapter adapter;

    Switch aSwitch;

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
            if (menuItem.getItemId() == R.id.mainpage) {
                Intent intentMain = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentMain);

            } else if (menuItem.getItemId() == R.id.my_profile) {
                Intent intentDonation = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);
            } else if (menuItem.getItemId() == R.id.logout) {
                sessionManager.deleteSession();
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentDonation);
            }
            return false;
        });

        aSwitch = findViewById(R.id.switch1);
        setSwitchValue();

        postListView = findViewById(R.id.postListView);
        posts = getPosts();
        adapter = new PostAdapter(getApplicationContext(), posts);
        postListView.setAdapter(adapter);
        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Post selectedPost = (Post) postListView.getItemAtPosition(position);
                //TODO: Nα αλλαξω το EditPostActivity στην κλάση που θα έχουμε για να φαίνεται το post
                Intent editNoteIntent = new Intent(getApplicationContext(), EditPostActivity.class);
                editNoteIntent.putExtra("inspect", selectedPost.getPostId());
                startActivity(editNoteIntent);
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String queryParameters = isChecked ? "uid" : null;
                posts = databaseHelper.getPosts(queryParameters, null, null,
                        0, sessionManager.getSessionId());
                adapter.clear();
                if (posts != null)
                    adapter.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void setSwitchValue() {
        Intent intent = getIntent();
        if (intent == null)
            return;

        aSwitch.setChecked(intent.getBooleanExtra("switchValue", false));
    }

    private ArrayList<Post> getPosts() {
        String queryParameters = aSwitch.isChecked() ? "uid" : null;

        return databaseHelper.getPosts(queryParameters, null, null, 0, sessionManager.getSessionId());
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
