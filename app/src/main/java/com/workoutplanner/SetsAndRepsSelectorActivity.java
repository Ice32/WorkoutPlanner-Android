package com.workoutplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetsAndRepsSelectorActivity extends AppCompatActivity {
    Button btnSetSetsAndReps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_and_reps_selector);

        btnSetSetsAndReps = findViewById(R.id.btnSetSetsAndReps);

        btnSetSetsAndReps.setOnClickListener(backToCreateWorkout);
    }
    private View.OnClickListener backToCreateWorkout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CreateWorkoutActivity.class);
            startActivity(intent);
        }
    };
}
