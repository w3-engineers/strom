package com.w3engineers.ext.strom.util.helper.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import timber.log.Timber;

/**
 * ============================================================================
 * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <br>----------------------------------------------------------------------------
 * <br>Created by: Ahmed Mohmmad Ullah (Azim) on [2018-09-17 at 4:04 PM].
 * <br>Email: azim@w3engineers.com
 * <br>----------------------------------------------------------------------------
 * <br>Project: android-framework - Copy.
 * <br>Code Responsibility: <Purpose of code>
 * <br>----------------------------------------------------------------------------
 * <br>Edited by :
 * <br>1. <First Editor> on [2018-09-17 at 4:04 PM].
 * <br>2. <Second Editor>
 * <br>----------------------------------------------------------------------------
 * <br>Reviewed by :
 * <br>1. <First Reviewer> on [2018-09-17 at 4:04 PM].
 * <br>2. <Second Reviewer>
 * <br>============================================================================
 **/

/**
 * This class was picked from BaseProject with minor modification.
 */
public class SharedPref {

    private static SharedPref sSharedPref;

    private SharedPreferences preferences;
    private SharedPref(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static synchronized SharedPref getSharedPref(@NonNull Context context) {

        Timber.d(Thread.currentThread().getName());
        if(sSharedPref == null) {
            Timber.d(Thread.currentThread().getName());

            synchronized (SharedPref.class) {
                Timber.d(Thread.currentThread().getName());
                if (sSharedPref == null) {
                    Timber.d(Thread.currentThread().getName());
                    sSharedPref = new SharedPref(context);
                }
            }

        }
        return sSharedPref;
    }

    public boolean write(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key, value);

        return editor.commit();
    }

    public boolean write(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(key, value);

        return editor.commit();
    }

    public boolean write(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(key, value);

        return editor.commit();
    }

    public boolean write(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(key, value);

        return editor.commit();
    }

    public String read(String key) {
        return preferences.getString(key, "");
    }

    public long readLong(String key) {
        return preferences.getLong(key, 0);
    }

    public int readInt(String key) {
        return preferences.getInt(key, 0);
    }
    public boolean readBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean readBooleanDefaultTrue(String key){
        return preferences.getBoolean(key, true);
    }

    public boolean contains(String key) {
        return preferences.contains(key);
    }

}
