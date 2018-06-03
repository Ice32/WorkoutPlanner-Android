package com.workoutplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.workoutplanner.api.implementation.MockWorkoutsAPI;
import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutActivity extends AppCompatActivity {

    private WorkoutsAPI workoutsAPI = new MockWorkoutsAPI();
    private List<Exercise> exercises = new ArrayList<>();

    EditText txtWorkoutName;
    Button btnSaveWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        btnSaveWorkout = findViewById(R.id.btnCreateWorkoutSave);
        txtWorkoutName = findViewById(R.id.txtWorkoutName);

        btnSaveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
            }
        });
    }

    private void saveWorkout() {
        String name = txtWorkoutName.getText().toString();

        Workout w = new Workout(name);

        workoutsAPI.addWorkout(w);

        finish();
    }
}
