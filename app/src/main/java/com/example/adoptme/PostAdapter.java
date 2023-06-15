package com.example.adoptme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView petName, species, animal_age, city;
        Post post = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_entry, parent, false);

        petName = convertView.findViewById(R.id.pet_name);
        species = convertView.findViewById(R.id.species);
        animal_age = convertView.findViewById(R.id.animal_age);
        city = convertView.findViewById(R.id.city);
        petName.setText(post.getPetName());
        species.setText(post.getSpecies());
        animal_age.setText(Integer.toString(post.getAge()));
        city.setText(post.getTownName());
        return convertView;
    }

}
