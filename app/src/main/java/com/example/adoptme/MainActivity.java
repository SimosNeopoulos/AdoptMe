//THE MAIN PAGE OF THE APP
package com.example.adoptme;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem a = menu.add("Create Post");
        MenuItem b = menu.add("View Posts");
        MenuItem c = menu.add("Edit Post");
        MenuItem d = menu.add("Log out");
        return true;
    }
}
