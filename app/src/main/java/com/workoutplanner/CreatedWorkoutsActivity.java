package com.workoutplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.workoutplanner.model.Workout;

import java.util.logging.Logger;

public class CreatedWorkoutsActivity extends AppCompatActivity implements
        WorkoutsListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_workouts);
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
}
