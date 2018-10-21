package com.workoutplanner.view.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.workoutplanner.R;
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.service.StatisticsService;
import com.workoutplanner.view.common.BaseNavigationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkoutStatisticsActivity extends BaseNavigationActivity implements DoneWorkoutsListFragment.OnListFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_workouts_statistics_container);

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

        this.loadData();
        Button btnOpenDoneWorkouts = findViewById(R.id.btnViewDoneWorkouts);
        btnOpenDoneWorkouts.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), WorkoutHistoryActivity.class)));
    }

    private void loadData() {
        new StatisticsService().getStatistics(this::drawStatisticsGraph);
    }

    @Override
    public void onListFragmentInteraction(ScheduledWorkout item) {

    }

    private void drawStatisticsGraph(Map<Long, Integer> statistics) {
        LineChart chart = findViewById(R.id.lineChart);

        List<Entry> numWorkoutsEntries = new ArrayList<Entry>();
        for(Map.Entry<Long, Integer> entry : statistics.entrySet()) {
            Long key = entry.getKey();
            Integer value = entry.getValue();
            numWorkoutsEntries.add(new Entry(key, value));
        }

        LineDataSet workoutsDataSet = new LineDataSet(numWorkoutsEntries, "Number of workouts");
        workoutsDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(workoutsDataSet);
        chart.setData(lineData);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.invalidate();
    }
}
