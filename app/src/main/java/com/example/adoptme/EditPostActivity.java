package com.example.adoptme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditPostActivity extends AppCompatActivity {

    private int postId;
    private EditText name, age, species, description, telephone, town;
    private Button editPostBtn;
//    private SessionManager sessionManager;
    DatabaseHelper databaseHelper;

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