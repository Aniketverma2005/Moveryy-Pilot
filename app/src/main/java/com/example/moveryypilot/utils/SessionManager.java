package com.example.moveryypilot.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "moveryy_session";
    private static final String KEY_ACCESS_TOKEN  = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_DRIVER_ID     = "driver_id";
    private static final String KEY_EMAIL         = "email";
    private static final String KEY_FULL_NAME     = "full_name";
    private static final String KEY_STATUS        = "status";
    private static final String KEY_PROFILE_DONE  = "profile_complete";
    private static final String KEY_IS_LOGGED_IN  = "is_logged_in";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs  = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveSession(String accessToken, String refreshToken,
                            int driverId, String email,
                            String fullName, String status,
                            boolean isProfileComplete) {
        editor.putString(KEY_ACCESS_TOKEN,  accessToken);
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.putInt(KEY_DRIVER_ID,        driverId);
        editor.putString(KEY_EMAIL,         email);
        editor.putString(KEY_FULL_NAME,     fullName != null ? fullName : "");
        editor.putString(KEY_STATUS,        status);
        editor.putBoolean(KEY_PROFILE_DONE, isProfileComplete);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public boolean isLoggedIn()        { return prefs.getBoolean(KEY_IS_LOGGED_IN, false); }
    public String  getAccessToken()    { return prefs.getString(KEY_ACCESS_TOKEN, ""); }
    public String  getRefreshToken()   { return prefs.getString(KEY_REFRESH_TOKEN, ""); }
    public int     getDriverId()       { return prefs.getInt(KEY_DRIVER_ID, -1); }
    public String  getEmail()          { return prefs.getString(KEY_EMAIL, ""); }
    public String  getFullName()       { return prefs.getString(KEY_FULL_NAME, ""); }
    public String  getStatus()         { return prefs.getString(KEY_STATUS, ""); }
    public boolean isProfileComplete() { return prefs.getBoolean(KEY_PROFILE_DONE, false); }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
