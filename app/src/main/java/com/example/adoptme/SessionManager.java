package com.example.adoptme;

import android.content.Context;
import android.content.SharedPreferences;

// Κλάση που κρατάει τον χρήστη συνδεδεμένο με το Account του.
public class SessionManager {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final String SESSION_KEY = "session_user";
    private final String SHARED_PREF_NAME = "session";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user) {
        editor.putInt(SESSION_KEY, user.getId()).commit();
    }

    public int getSessionId() {
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void deleteSession() {
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
