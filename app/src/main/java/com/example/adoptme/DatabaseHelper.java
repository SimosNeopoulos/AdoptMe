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

        MyDatabase.execSQL("CREATE TABLE posts (id INTEGER PRIMARY KEY AUTOINCREMENT, town TEXT," +
                "species TEXT, pet_name TEXT, age INTEGER, phone_number TEXT, userId INTEGER," +
                "post_discription TEXT)");
        MyDatabase.execSQL("CREATE INDEX idx_town ON posts(town)");
        MyDatabase.execSQL("CREATE INDEX idx_species ON posts(species)");
        MyDatabase.execSQL("CREATE INDEX idx_userId ON posts(userId)");
        MyDatabase.execSQL("CREATE INDEX idx_age ON posts(age)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public int createProfile(String email, String password, String name) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        long result = MyDatabase.insert("users", null, contentValues);
        return Integer.parseInt(String.valueOf(result));
    }

    public boolean updateProfile(String id, String email, String password, String name) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        int rowsAffected = MyDatabase.update("users", contentValues, "id = ?", new String[]{id});
        return rowsAffected > 0;
    }

    public boolean insertPost(String town, String species, String petName, int age, String phoneNumber,
                              int userId, String post_discription) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("town", town);
        contentValues.put("species", species);
        contentValues.put("pet_name", petName);
        contentValues.put("age", age);
        contentValues.put("phone_number", phoneNumber);
        contentValues.put("userId", userId);
        contentValues.put("post_discription", post_discription);
        long post_id = MyDatabase.insert("posts", null, contentValues);
        return post_id != -1;
    }
    public void updatePost(int postId, String town, String species, String petName, int age, String phoneNumber,
                              String post_discription) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("town", town);
        contentValues.put("species", species);
        contentValues.put("pet_name", petName);
        contentValues.put("age", age);
        contentValues.put("phone_number", phoneNumber);
        contentValues.put("post_discription", post_discription);
        String[] whereArgs = {String.valueOf(postId)};
        MyDatabase.update("posts", contentValues, "id = ?", whereArgs);
    }

    public boolean deletePost(int postId) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        int deletedRows = myDatabase.delete("posts", "id = ?", new String[]{String.valueOf(postId)});
        return deletedRows > 0;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    public ArrayList<Post> getPosts(String queryParameters, String townName, String species, int postId, int userId) {
        ArrayList<Post> posts = new ArrayList<>();
        Cursor cursor = getPostsFromDatabase(queryParameters, townName, species, postId, userId);
        if (cursor == null)
            return null;

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String town = cursor.getString(1);
                String postSpecies = cursor.getString(2);
                String petName = cursor.getString(3);
                int postId2 = cursor.getInt(4);
                String phoneNumber = cursor.getString(5);
                int postUserId = cursor.getInt(6);
                String postDescription = cursor.getString(7);
                posts.add(new Post(id, town, postSpecies, petName, postId2, postUserId, postDescription, phoneNumber));
            } while (cursor.moveToNext());
        }
        return posts;
    }

    public ArrayList<String> getUserById(int id) {
        ArrayList<String> userData = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor;
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        cursor = myDatabase.rawQuery("Select * from users where id = ?", new String[]{Integer.toString(id)});

        if (cursor.moveToFirst()) {
            userData.add(cursor.getString(1));
            userData.add(cursor.getString(2));
            userData.add(cursor.getString(3));
            return userData;
        }
        return null;
    }

    private Cursor getPostsFromDatabase(String queryParameters, String townName, String species, int postId, int userId) {
        @SuppressLint("Recycle") Cursor cursor = null;
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        if (queryParameters == null) {
            cursor = myDatabase.rawQuery("Select * from posts ", null);
        } else if (queryParameters.equals("uid")) {
            cursor = myDatabase.rawQuery("Select * from posts where " +
                    "userId = ?", new String[]{Integer.toString(userId)});
        } else if (queryParameters.equals("pid")) {
            cursor = myDatabase.rawQuery("Select * from posts where " +
                    "id = ?", new String[]{Integer.toString(postId)});
        }
        return (cursor.getCount() > 0) ? cursor : null;
    }

    public User checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                String id = cursor.getString(0);
                email = cursor.getString(1);
                password = cursor.getString(2);
                String name = cursor.getString(3);
                return new User(Integer.parseInt(id), name, email, password);
            }
        }
        return null;
    }
}