package com.workoutplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.workoutplanner.existingExercises.ExistingExerciseListFragment;
import com.workoutplanner.model.Exercise;


public class CreateNewExerciseActivity extends AppCompatActivity implements ExistingExerciseListFragment.OnListFragmentInteractionListener {
    Button btnAddNewExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_exercise);

        btnAddNewExercise = findViewById(R.id.btnAddNewExercise);

        btnAddNewExercise.setOnClickListener(backToCreateWorkout);
    }

    private View.OnClickListener backToCreateWorkout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CreateWorkoutActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onListFragmentInteraction(Exercise item) {

    }
}
