package com.workoutplanner;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.workoutplanner.existingExercises.ExistingExerciseListFragment;
import com.workoutplanner.model.Exercise;

public class AddExistingExerciseActivity extends AppCompatActivity implements ExistingExerciseListFragment.OnListFragmentInteractionListener {

    FloatingActionButton createNewExercise;
    LinearLayout existingExercisesFragmentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_existing_exercise);

        createNewExercise = findViewById(R.id.createNewExercise);
        existingExercisesFragmentItem = findViewById(R.id.existingExercisesFragmentItem);

        createNewExercise.setOnClickListener(createNewExerciseHandler);

    }

    private View.OnClickListener createNewExerciseHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CreateNewExerciseActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onListFragmentInteraction(Exercise item) {
        Intent intent = new Intent(getApplicationContext(), SetsAndRepsSelectorActivity.class);
        startActivity(intent);
    }
}
