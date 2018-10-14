package com.workoutplanner.view.exercises;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.workoutplanner.R;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.service.ExercisesService;


public class CreateNewExerciseActivity extends AppCompatActivity{
    private EditText txtExerciseName;
    private EditText txtNumSets;
    private EditText txtNumReps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_create_new_activity);

        txtExerciseName = findViewById(R.id.txtExerciseName);
        txtNumSets = findViewById(R.id.txtNumSets);
        txtNumReps = findViewById(R.id.txtNumReps);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnAddNewExerciseSave) {
            saveExercise();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveExercise() {
        if (!validateFieldsForExerciseSave()) {
            return;
        }
        String name = txtExerciseName.getText().toString();
        Integer sets = Integer.parseInt(txtNumSets.getText().toString());
        Integer reps = Integer.parseInt(txtNumReps.getText().toString());

        Exercise exercise = new Exercise(name, sets, reps);
        new ExercisesService().saveExercise(exercise, this::finish);
    }
    boolean validateFieldsForExerciseSave() {
        return assertViewValueNotEmpty(txtNumSets)
                && assertViewValueNotEmpty(txtNumReps)
                && assertViewValueNotEmpty(txtExerciseName);
    }
    boolean assertViewValueNotEmpty(EditText view) {
       if (view.getText().toString().equals("")) {
           view.setError("Required field");
           view.requestFocus();
           return  false;
       }
       return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new_exercise, menu);

        return true;
    }
}
