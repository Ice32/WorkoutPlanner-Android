package com.workoutplanner;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.workoutplanner.model.Exercise;
import com.workoutplanner.service.implementation.WorkoutsServiceImpl;
import com.workoutplanner.service.interfaces.WorkoutsService;

import java.util.ArrayList;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {
    private WorkoutsService workoutsService = new WorkoutsServiceImpl();
    private List<Exercise> allCreatedExercises = workoutsService.getAllCreatedExercises();

    Button btnAddNewExercise;
    Button btnAddExistingExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        btnAddNewExercise = findViewById(R.id.btnAddNewExercise);
        btnAddExistingExercise = findViewById(R.id.btnAddExistingExercise);

        btnAddNewExercise.setOnClickListener(backToCreateWorkout);
        btnAddExistingExercise.setOnClickListener(backToCreateWorkout);

        final TextInputLayout exerciseNameWrapper = findViewById(R.id.exerciseNameWrapper);
        final TextInputLayout setsWrapper = findViewById(R.id.setsWrapper);
        final TextInputLayout repsWrapper = findViewById(R.id.repsWrapper);
        exerciseNameWrapper.setHint("Name");
        setsWrapper.setHint("Sets");
        repsWrapper.setHint("Reps");

        Spinner spinnerExercises = findViewById(R.id.spinnerExercises);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getExercisesDropdownItems());

        spinnerExercises.setAdapter(adapter);

        spinnerExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private List<String> getExercisesDropdownItems() {
        List<String> exerciseNames = new ArrayList<>();

        for(Exercise e : allCreatedExercises) {
            exerciseNames.add(e.name);
        }

        return exerciseNames;
    }

    private View.OnClickListener backToCreateWorkout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
