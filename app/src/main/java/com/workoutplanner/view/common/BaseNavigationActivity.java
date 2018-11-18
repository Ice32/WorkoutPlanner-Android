package com.workoutplanner.view.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.workoutplanner.R;
import com.workoutplanner.service.AuthenticationService;
import com.workoutplanner.view.exercises.ExercisesActivity;
import com.workoutplanner.view.loginRegistration.LoginActivity;
import com.workoutplanner.view.scheduledWorkouts.HomeActivity;
import com.workoutplanner.view.statistics.WorkoutStatisticsActivity;
import com.workoutplanner.view.workouts.CreatedWorkoutsActivity;

public abstract class BaseNavigationActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BroadcastReceiver broadcastReceiver;
    private boolean receiverRegistered;
    private final String logoutAction = "com.package.ACTION_LOGOUT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerLogoutReceiver();
    }

    private void registerLogoutReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(logoutAction);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class activityToOpen = null;
        if (id == R.id.nav_history) {
            activityToOpen = WorkoutStatisticsActivity.class;
        }  else if (id == R.id.nav_scheduled) {
            activityToOpen = HomeActivity.class;
        } else if (id == R.id.created_workouts) {
            activityToOpen = CreatedWorkoutsActivity.class;
        } else if (id == R.id.created_exercises) {
            activityToOpen = ExercisesActivity.class;
        } else if (id == R.id.nav_logout) {
            activityToOpen = LoginActivity.class;
            broadcastLogout();
            new AuthenticationService().logout();
        }
        if (activityToOpen != null && activityToOpen != this.getClass()) {
            Intent intent = new Intent(getApplicationContext(), activityToOpen);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void broadcastLogout() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(logoutAction);
        sendBroadcast(broadcastIntent);
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
