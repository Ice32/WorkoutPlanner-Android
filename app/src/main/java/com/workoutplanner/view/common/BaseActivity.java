package com.workoutplanner.view.common;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.workoutplanner.R;
import com.workoutplanner.service.ErrorInterceptor;

public abstract class BaseActivity extends AppCompatActivity {
    BroadcastReceiver messageReceiver;

    private boolean isPaused = false;
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
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;

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
        if (isPaused) {
            return;
        }
        int stringId = getErrorStringId(errorCode);
        View view = findViewById(android.R.id.content);

        InputMethodManager imm = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


        Snackbar snackbar = Snackbar.make(view, stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    protected boolean assertViewValueNotEmpty(EditText view) {
        if (view.getText().toString().equals("")) {
            view.setError(getString(R.string.error_field_required));
            view.requestFocus();
            return  false;
        }
        return true;
    }
}
