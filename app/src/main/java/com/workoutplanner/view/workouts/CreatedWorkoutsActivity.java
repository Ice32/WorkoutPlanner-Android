package com.workoutplanner.view.workouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.workoutplanner.R;
import com.workoutplanner.model.Workout;
import com.workoutplanner.view.common.BaseNavigationActivity;
import com.workoutplanner.view.common.WorkoutsListFragment;

public class CreatedWorkoutsActivity extends BaseNavigationActivity implements
        WorkoutsListFragment.OnListFragmentInteractionListener {

    FloatingActionButton btnCreateWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_created_workouts_container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCreateWorkout = findViewById(R.id.btnCreateWorkout);


        btnCreateWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateWorkoutActivity.class);
            startActivity(intent);
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
        Intent intent = new Intent(getApplicationContext(), EditWorkoutActivity.class);
        startActivity(intent);
    }
}
