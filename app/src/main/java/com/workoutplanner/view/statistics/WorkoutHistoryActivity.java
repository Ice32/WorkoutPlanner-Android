package com.workoutplanner.view.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.workoutplanner.view.createdWorkouts.CreatedWorkoutsActivity;
import com.workoutplanner.view.exercises.ExercisesActivity;
import com.workoutplanner.R;
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.view.scheduledWorkouts.HomeActivity;

public class WorkoutHistoryActivity extends AppCompatActivity implements DoneWorkoutsListFragment.OnListFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.done_workouts_container);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public void onListFragmentInteraction(ScheduledWorkout item) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;
        if (id == R.id.created_workouts) {
            intent = new Intent(getApplicationContext(), CreatedWorkoutsActivity.class);
        } else if (id == R.id.nav_scheduled) {
            intent = new Intent(getApplicationContext(), HomeActivity.class);
        } else if (id == R.id.created_exercises) {
            intent = new Intent(getApplicationContext(), ExercisesActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
