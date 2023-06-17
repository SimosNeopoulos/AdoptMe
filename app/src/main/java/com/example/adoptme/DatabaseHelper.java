package com.example.adoptme;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.ArrayList;

//Κλάση που χειρίζεται την βάση δεδομένων
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "AdoptMe.sqlite";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        // Table για τους users με index στο column email
        MyDatabase.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " email TEXT UNIQUE, password TEXT, name TEXT)");
        MyDatabase.execSQL("CREATE INDEX idx_email ON users(email)");

        //Table για τα posts με index στα columns town, species, userId και age
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

    // Δημιουργία χρήστη
    public int createProfile(String email, String password, String name) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        long result = MyDatabase.insert("users", null, contentValues);
        return Integer.parseInt(String.valueOf(result));
    }

    // Update στα δεδομένα ενός χρηστη με id ίδιο με την τιμη της μεταβλητητς id
    public boolean updateProfile(String id, String email, String password, String name) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        int rowsAffected = MyDatabase.update("users", contentValues, "id = ?", new String[]{id});
        return rowsAffected > 0;
    }

    // Δημιουργία Post
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

    // Update στα δεδομένα ενός post με id ίδιο με την τιμη της μεταβλητητς postId
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

    // Delete post με id ίδιο με την τιμη της μεταβλητητς postId
    public boolean deletePost(int postId) {
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        int deletedRows = myDatabase.delete("posts", "id = ?", new String[]{String.valueOf(postId)});
        return deletedRows > 0;
    }

    // Ελενχος για την ύπαρξη χρηστη με email ίδιο με την μεταβλητη email
    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    // Συνάρτηση που επιστρέφει ArrayList με Posts.
    // Τα posts τα οποία επίστρεφει εξαρτόνται απο τιμη που έχει η μεταβλητη queryParameters
    // Οι πιθανές τιμες είναι οι εξείς: null, "uid", "pid"
    // null: Επιστρέφει όλα τα posts
    // "uid": Επιστέφει όλα τα posts με id ίδιο με την τίμη της μεταβλητης userId
    // "pid": Επιστέφει όλα τα posts με id ίδιο με την τίμη της μεταβλητης postId
    public ArrayList<Post> getPosts(String queryParameters, int postId, int userId) {
        ArrayList<Post> posts = new ArrayList<>();

        // Τα Δέδομένα απο το database
        Cursor cursor = getPostsFromDatabase(queryParameters, postId, userId);

        // Έλενχος για το αν δεν επιστράφηκε τίποτα
        if (cursor == null)
            return null;

        // Μετατροπή των δεδομένων σε αντικείμενα Post τα οποία προστήθονται σε ArrayList
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

    // Επιστροφή των δεδομένων του χρήστη με id ίσο με την τιμη της μεταβλητης id
    // σε ArrayList<String>
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

    // Συνάρτηση που επιστρέφει ακατέργαστα τα δεδομένα (post) που ζητήθηκαν.
    // Αν δεν βρεθεί κανένα post στην αναζήτηση επιστρέφεται η τιμη null
    private Cursor getPostsFromDatabase(String queryParameters, int postId, int userId) {
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

    // Επιστρέφει τον χρήστη με κωδικο και email ίδιο με τις μεταβλητες email και password.
    // Αν δεν βρεθει κάποιος χρήστης επιστρέφει null
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