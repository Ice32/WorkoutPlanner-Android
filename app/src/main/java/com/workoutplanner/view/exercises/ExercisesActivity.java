package com.workoutplanner.view.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.workoutplanner.R;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.view.common.BaseNavigationActivity;

public class ExercisesActivity extends BaseNavigationActivity implements ExistingExerciseListFragment.OnListFragmentInteractionListener {
    FloatingActionButton btnAddExercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_container);

        btnAddExercise = findViewById(R.id.btnAddExercise);

        btnAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateNewExerciseActivity.class);
            startActivity(intent);
        });

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
    public void onListFragmentInteraction(Exercise item) {

    }
}
