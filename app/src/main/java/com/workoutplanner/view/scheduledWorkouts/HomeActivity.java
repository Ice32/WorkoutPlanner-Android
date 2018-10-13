package com.workoutplanner.view.scheduledWorkouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.workoutplanner.view.createdWorkouts.CreatedWorkoutsActivity;
import com.workoutplanner.view.exercises.ExercisesActivity;
import com.workoutplanner.view.loginRegistration.LoginActivity;
import com.workoutplanner.R;
import com.workoutplanner.view.statistics.WorkoutStatisticsActivity;
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.service.AuthenticationService;

import java.util.logging.Logger;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ScheduledWorkoutsListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectWorkoutActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;
        if (id == R.id.nav_history) {
            intent = new Intent(getApplicationContext(), WorkoutStatisticsActivity.class);
        } else if (id == R.id.created_workouts) {
            intent = new Intent(getApplicationContext(), CreatedWorkoutsActivity.class);
        } else if (id == R.id.created_exercises) {
            intent = new Intent(getApplicationContext(), ExercisesActivity.class);
        } else if (id == R.id.nav_logout) {
            new AuthenticationService(this).logout();
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onListFragmentInteraction(ScheduledWorkout item) {
        Logger.getAnonymousLogger().info("Clicked");
    }

    @Override
    public void onButtonClick(ScheduledWorkout item) {
//        Intent intent = new Intent(getApplicationContext(), SelectWorkoutActivity.class);
//        startActivity(intent);
    }

}
