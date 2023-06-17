//THE MAIN PAGE OF THE APP
package com.example.adoptme;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
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

            // Μεταφορά στο MainActivity
            if (menuItem.getItemId() == R.id.mainpage) {
                Intent intentMain = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intentMain);

            // Μεταφορά στο MyProfileActivity
            } else if (menuItem.getItemId() == R.id.my_profile) {
                Intent intentDonation = new Intent(MainActivity.this, MyProfileActivity.class);
                startActivity(intentDonation);

            // Αποσύνδεση χρήστη
            } else if (menuItem.getItemId() == R.id.logout) {
                sessionManager.deleteSession();
                Toast.makeText(this, "Επιτυχής Αποσύνδεση", Toast.LENGTH_SHORT).show();
                Intent intentDonation = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentDonation);
                finish();
            }
            return false;
        });

        aSwitch = findViewById(R.id.switch1);
        postListView = findViewById(R.id.postListView);

        // Τα posts για το activity
        posts = getPosts();
        adapter = new PostAdapter(getApplicationContext(), (posts == null) ? new ArrayList<>() : posts);
        postListView.setAdapter(adapter);
        postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Το post που επιλέχθηκε
                Post selectedPost = (Post) postListView.getItemAtPosition(position);
                int userId = selectedPost.getUserId();

                // Αν ο χρήστης που επέλεξε το post είναι και αυτός που το ανέρτησε τοτε μετα
                // φέρεται στο EditPostActivity με τα δεδομένα του post
                if (userId == sessionManager.getSessionId()) {
                    editPost(selectedPost);
                    return;
                }

                // Σε κάθε αλλη περίπτοση μεταφέρεται στο ViePostActivity για προβολή του post
                Intent editNoteIntent = new Intent(getApplicationContext(), ViewPostActivity.class);
                editNoteIntent.putExtra("inspect", selectedPost.toStringArraylist());
                startActivity(editNoteIntent);
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Αν είναι επιλεγμένο θα γίνεί προβολη των posts του χρήστη μου είναι συνδεδεμένος.
                // Αν οχι θα γίνει προβολή όλων των posts
                String queryParameters = isChecked ? "uid" : null;
                posts = databaseHelper.getPosts(queryParameters, 0, sessionManager.getSessionId());
                adapter.clear();
                if (posts != null)
                    adapter.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void editPost(Post post) {
        Intent editNoteIntent = new Intent(getApplicationContext(), EditPostActivity.class);
        editNoteIntent.putExtra("edit", post.toStringArraylist());
        startActivity(editNoteIntent);
    }

    private ArrayList<Post> getPosts() {
        String queryParameters = aSwitch.isChecked() ? "uid" : null;

        return databaseHelper.getPosts(queryParameters, 0, sessionManager.getSessionId());
    }

    // Έναρξη του activity SignUpActivity και τερματισμος του τορινού
    private void redirectToSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    // Έναρξη του activity AddPostActivity και τερματισμος του τορινού
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
