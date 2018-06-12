package com.workoutplanner;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.model.Workout;

import java.util.logging.Logger;

public class CreatedWorkoutsActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        WorkoutsListFragment.OnListFragmentInteractionListener,
        ScheduledWorkoutsListFragment.OnListFragmentInteractionListener {

    FloatingActionButton btnCreateWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.created_workouts_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCreateWorkout = findViewById(R.id.btnCreateWorkout);


        btnCreateWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateWorkoutActivity.class);
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
    public void onListFragmentInteraction(Workout item) {
        Logger.getAnonymousLogger().info("Clicked");
    }

    @Override
    public void onScheduleButtonClick(Workout item) {
        Intent intent = new Intent(getApplicationContext(), ScheduleWorkoutActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;
        if (id == R.id.nav_history) {
            intent = new Intent(getApplicationContext(), WorkoutHistoryActivity.class);
        } else if (id == R.id.nav_scheduled) {
            intent = new Intent(getApplicationContext(), HomeActivity.class);
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

    }

    @Override
    public void onButtonClick(ScheduledWorkout item) {

    }
}
