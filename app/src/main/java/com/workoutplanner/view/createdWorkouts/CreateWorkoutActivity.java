package com.workoutplanner.view.createdWorkouts;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.workoutplanner.R;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.WorkoutsService;
import com.workoutplanner.view.exercises.SelectableExerciseFragment;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutActivity extends AppCompatActivity implements SelectableExerciseFragment.OnListFragmentInteractionListener {
    private List<Exercise> selectedExercises = new ArrayList<>();

    EditText txtWorkoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtWorkoutName = findViewById(R.id.txtWorkoutName);
        showActionBar();
        final TextInputLayout workoutNameWrapper = findViewById(R.id.workoutNameWrapper);
        workoutNameWrapper.setHint("Name");
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled (true);
    }

    private void saveWorkout() {
        String name = txtWorkoutName.getText().toString();

        Workout w = new Workout(name, selectedExercises);
        new WorkoutsService().saveWorkout(w, this::finish);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_workout, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btnCreateWorkoutSave) {
            saveWorkout();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Exercise item, boolean isChecked) {
        if (isChecked) {
            selectedExercises.add(item);
        } else {
            selectedExercises.remove(item);
        }
    }
}
