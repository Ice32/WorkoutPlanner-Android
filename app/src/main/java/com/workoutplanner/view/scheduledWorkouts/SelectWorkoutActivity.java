package com.workoutplanner.view.scheduledWorkouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.workoutplanner.R;
import com.workoutplanner.model.Workout;
import com.workoutplanner.view.common.BaseNavigationActivity;
import com.workoutplanner.view.common.WorkoutsListFragment;

public class SelectWorkoutActivity extends BaseNavigationActivity implements
        WorkoutsListFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduledworkouts_select_workout_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onListFragmentInteraction(Workout item) {
        Intent intent = new Intent(getApplicationContext(), ScheduleWorkoutActivity.class);
        intent.putExtra("selected_workout", item);
        startActivity(intent);
    }

}
