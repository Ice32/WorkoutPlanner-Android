package com.workoutplanner.view.workouts;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.workoutplanner.R;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.WorkoutsService;
import com.workoutplanner.util.FragmentUtils;
import com.workoutplanner.view.common.BaseActivity;
import com.workoutplanner.view.exercises.SelectableExercisesFragment;

import java.util.ArrayList;
import java.util.List;

public class EditWorkoutActivity extends BaseActivity implements SelectableExercisesFragment.OnListFragmentInteractionListener {
    private List<Exercise> selectedExercises = new ArrayList<>();
    private EditText txtWorkoutName;
    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_edit_workout_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtWorkoutName = findViewById(R.id.txtWorkoutName);
        final TextInputLayout workoutNameWrapper = findViewById(R.id.workoutNameWrapper);
        workoutNameWrapper.setHint("Name");
        showActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            workout = (Workout) extras.getSerializable("selected_workout");
            txtWorkoutName.setText(workout.name);
        }
        FragmentUtils.openAsReplace(this, R.id.editWorkoutActivityFragmentPlaceholder, SelectableExercisesFragment.newInstance(workout));
        loadSelectedExercises();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled (true);
    }

    private void saveWorkout() {
        if (!validateFieldsForWorkoutSave()) {
            return;
        }

        workout.name = txtWorkoutName.getText().toString();;
        workout.exercises = selectedExercises;
        new WorkoutsService().saveWorkout(workout, this::finish);
    }

    boolean validateFieldsForWorkoutSave() {
        return assertViewValueNotEmpty(txtWorkoutName);
    }

    boolean assertViewValueNotEmpty(EditText view) {
        if (view.getText().toString().equals("")) {
            view.setError("Required field");
            view.requestFocus();
            return  false;
        }
        return true;
    }

    private void loadSelectedExercises() {
        selectedExercises.addAll(workout.exercises);
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
