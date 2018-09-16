package com.workoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.workoutplanner.model.Workout;

public class SelectWorkoutActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        WorkoutsListFragment.OnListFragmentInteractionListener {

    FloatingActionButton btnCreateWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onListFragmentInteraction(Workout item) {
        Intent intent = new Intent(getApplicationContext(), ScheduleWorkoutActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;
        if (id == R.id.nav_history) {
            intent = new Intent(getApplicationContext(), WorkoutStatisticsActivity.class);
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
