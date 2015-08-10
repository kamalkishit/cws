package com.contentws.cws;

import android.content.SharedPreferences;

/**
 * Created by Kamal on 7/20/15.
 */
public class SharedPreferencesStorage {
    private static SharedPreferencesStorage sharedPreferencesStorage = null;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesStorage() {
        sharedPreferences = ApplicationState.getAppContext().getSharedPreferences(Config.CWS_SHARED_PREFERENCES, 0);
    }

    public static SharedPreferencesStorage getInstance() {
        if (sharedPreferencesStorage == null) {
            sharedPreferencesStorage = new SharedPreferencesStorage();
        }

        return sharedPreferencesStorage;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
