package com.example.adoptme;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private Context context;
    private ArrayList<Post> posts;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_entry, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = posts.get(position);
        holder.getTown().setText(post.getTownName());
        holder.getPosterName().setText(post.getPetName());
        holder.getAge().setText(post.getAge());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        EditText town, species, posterName, age;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            town = itemView.findViewById(R.id.city);
            species = itemView.findViewById(R.id.species);
            posterName = itemView.findViewById(R.id.poster_name);
            age = itemView.findViewById(R.id.animal_age);
        }

        public EditText getTown() {
            return town;
        }

        public EditText getSpecies() {
            return species;
        }

        public EditText getPosterName() {
            return posterName;
        }

        public EditText getAge() {
            return age;
        }
    }
}
