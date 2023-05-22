package com.example.adoptme;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "AdoptMe.sqlite";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " email TEXT UNIQUE, password TEXT, name TEXT)");
        MyDatabase.execSQL("CREATE INDEX idx_email ON users(email)");

        MyDatabase.execSQL("CREATE TABLE image_path (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "post_id INTEGER, path TEXT)");
        MyDatabase.execSQL("CREATE INDEX idx_post ON image_path(post_id)");


        MyDatabase.execSQL("CREATE TABLE posts (id INTEGER PRIMARY KEY AUTOINCREMENT, town TEXT," +
                "species TEXT, pet_name TEXT, age INTEGER, phone_number TEXT)");
        MyDatabase.execSQL("CREATE INDEX idx_town ON posts(town)");
        MyDatabase.execSQL("CREATE INDEX idx_species ON posts(species)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String email, String password, String name) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        long result = MyDatabase.insert("users", null, contentValues);
        return result != -1;
    }

    public boolean insertPost(String town, String species, String petName, int age, String phoneNumber, ArrayList<String> images) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("town", town);
        contentValues.put("species", species);
        contentValues.put("pet_name", petName);
        contentValues.put("age", age);
        contentValues.put("phone_number", phoneNumber);
        long post_id = MyDatabase.insert("posts", null, contentValues);
        if(post_id != -1) {
            insertImagePaths(images, post_id);
            return true;
        }
        return false;
    }

    private void insertImagePaths(ArrayList<String> image_paths, long postId) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        for (String image_path : image_paths) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("path", image_path);
            contentValues.put("post_id", postId);
            MyDatabase.insert("image_path", null, contentValues);
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    private Cursor getPosts(String queryParameters, String townName, String species, int age) {
        @SuppressLint("Recycle") Cursor cursor;
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        switch (queryParameters) {
            case "t":
                cursor = myDatabase.rawQuery("Select * from posts where town = ?", new String[]{townName});
                break;

            case "ts":
                cursor = myDatabase.rawQuery("Select * from posts where town = ? and" +
                        " species = ?", new String[]{townName, species});
                break;

            case "tsa":
                cursor = myDatabase.rawQuery("Select * from posts where town = ? and" +
                        " species = ? and age = ?", new String[]{townName, species, Integer.toString(age)});
                break;

            case "sa":
                cursor = myDatabase.rawQuery("Select * from posts where" +
                        " species = ? and age = ?", new String[]{species, Integer.toString(age)});
                break;

            case "s":
                cursor = myDatabase.rawQuery("Select * from posts where town = ? and" +
                        " species = ?", new String[]{species});
                break;

            case "a":
                cursor = myDatabase.rawQuery("Select * from posts where " +
                        "age = ?", new String[]{Integer.toString(age)});
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + queryParameters);
        }

        return (cursor.getCount() > 0) ? cursor : null;
    }

    private ArrayList<String> getImagePaths(int postId) {
        ArrayList<String> images = new ArrayList<>();
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = myDatabase.rawQuery("Select path from image_path where post_id = ?", new String[]{Integer.toString(postId)});
        if (cursor.getCount() < 1)
            return images;
        do {
            images.add(cursor.getString(2));
        } while (cursor.moveToNext());

        return images;
    }

    public User checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                email = cursor.getString(0);
                password = cursor.getString(1);
                String name = cursor.getString(2);
                return new User(name, email, password);
            }
        }
        return null;
    }
}