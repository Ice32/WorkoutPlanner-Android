package com.workoutplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.workoutplanner.history.DoneWorkoutsListFragment;
import com.workoutplanner.model.ScheduledWorkout;

public class WorkoutHistoryActivity extends AppCompatActivity implements DoneWorkoutsListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);
    }

    @Override
    public void onListFragmentInteraction(ScheduledWorkout item) {

    }
}
