package com.example.adoptme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

public class Menu {
    public Context context;
    Menu(Context context){
        this.context=context;
    }

    public void createPost(){
        Intent createPost=new Intent(Intent.ACTION_VIEW);
        createPost.setData();//Se petaei sto createPost activity
        context.startActivity(createPost);
    }
    public void viewPost(){
        Intent viewPost=new Intent(Intent.ACTION_VIEW);
        viewPost.setData();//se petaei sto viewpost activity
        context.startActivity(viewPost);
    }
    public void editPost(){
        Intent editPost=new Intent(Intent.ACTION_VIEW);
        editPost.setData();//se petaei sto edit post activity
        context.startActivity(editPost);
    }

    public MenuItem add(String create_post) {
    }
}
