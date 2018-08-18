package com.workoutplanner;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.workoutplanner.history.DoneWorkoutsListFragment;
import com.workoutplanner.model.ScheduledWorkout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutStatisticsActivity extends AppCompatActivity implements DoneWorkoutsListFragment.OnListFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_statistics_container);

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

        LineChart chart = (LineChart) findViewById(R.id.lineChart);

        List<Entry> numWorkoutsEntries = new ArrayList<Entry>();
        numWorkoutsEntries.add(new Entry(1, 5));
        numWorkoutsEntries.add(new Entry(2, 8));
        numWorkoutsEntries.add(new Entry(3, 6));
        numWorkoutsEntries.add(new Entry(4, 3));

        LineDataSet workoutsDataSet = new LineDataSet(numWorkoutsEntries, "Number of workouts");
        workoutsDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

//        List<Entry> numExercisesEntries = new ArrayList<Entry>();
//        numExercisesEntries.add(new Entry(1, 11));
//        numExercisesEntries.add(new Entry(2, 16));
//        numExercisesEntries.add(new Entry(3, 13));
//        LineDataSet exercisesDataSet = new LineDataSet(numExercisesEntries, "Number exercises");

        LineData lineData = new LineData(workoutsDataSet/*, exercisesDataSet*/);
        chart.setData(lineData);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.invalidate();

        Button btnOpenDoneWorkouts = findViewById(R.id.btnViewDoneWorkouts);
        btnOpenDoneWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkoutHistoryActivity.class);
                startActivity(intent);
            }
        });
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
