package com.example.adoptme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPostActivity extends AppCompatActivity {

    private EditText name, age, species, description, telephone, town;
    private Button createPostBtn;
    private SessionManager sessionManager;
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