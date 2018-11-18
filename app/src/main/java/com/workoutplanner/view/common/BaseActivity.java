package com.workoutplanner.view.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.workoutplanner.R;
import com.workoutplanner.service.ErrorInterceptor;

public abstract class BaseActivity extends AppCompatActivity {
    BroadcastReceiver messageReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                displayNetworkErrorSnackbar(intent.getExtras().getInt(ErrorInterceptor.errorCode, 500));
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter(ErrorInterceptor.errorString));
    }

    private int getErrorStringId(int errorCode) {
        switch (errorCode) {
            case(502):
            case(404):
                return R.string.error_api_not_available;
            default:
                return R.string.error_unknown_error;
        }
    }

    private void displayNetworkErrorSnackbar(int errorCode) {
        int stringId = getErrorStringId(errorCode);
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
