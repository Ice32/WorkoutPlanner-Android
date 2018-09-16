package com.workoutplanner.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.workoutplanner.R;

public class JwtTokenProvider {
    private Context context;

    public JwtTokenProvider(Context context) {
        this.context = context;
    }
    public String get() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(context.getString(R.string.jwt_token), "");
    }
}
