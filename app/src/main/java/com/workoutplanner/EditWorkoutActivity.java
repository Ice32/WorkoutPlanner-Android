package com.workoutplanner;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.workoutplanner.existingExercises.SelectableExerciseFragment;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.Workout;

public class EditWorkoutActivity extends AppCompatActivity implements SelectableExerciseFragment.OnListFragmentInteractionListener {

//    private WorkoutsAPI workoutsAPI = new MockWorkoutsAPI();

    EditText txtWorkoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
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

        Workout w = new Workout(name);

//        workoutsAPI.addWorkout(w);

        finish();
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
    public void onListFragmentInteraction(Exercise item) {

    }
}
