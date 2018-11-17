package com.workoutplanner.view.loginRegistration;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseLoginRegistrationActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private boolean receiverRegistered = false;
    protected final String loginAction = "com.package.ACTION_LOGIN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerLoginReceiver();
    }

    private void registerLoginReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(loginAction);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                unregisterReceiverIfNecessary();
                finish();
            }
        };
        registerReceiver(broadcastReceiver, intentFilter);
        receiverRegistered = true;
    }

    private void unregisterReceiverIfNecessary() {
        if (receiverRegistered) {
            unregisterReceiver(broadcastReceiver);
            receiverRegistered = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiverIfNecessary();
    }
}
