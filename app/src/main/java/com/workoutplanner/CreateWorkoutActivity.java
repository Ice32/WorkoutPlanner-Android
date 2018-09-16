package com.workoutplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.existingExercises.SelectableExerciseFragment;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.JwtTokenProvider;
import com.workoutplanner.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateWorkoutActivity extends AppCompatActivity implements SelectableExerciseFragment.OnListFragmentInteractionListener {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private WorkoutsAPI workoutsAPI;
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

        workoutsAPI = new ServiceGenerator(new JwtTokenProvider(this)).createService(WorkoutsAPI.class);
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled (true);
    }

    private void saveWorkout() {
        String name = txtWorkoutName.getText().toString();

        Workout w = new Workout(name, selectedExercises);

        Call<Workout> workoutRequest = workoutsAPI.createWorkout(w);
        workoutRequest.enqueue(new Callback<Workout>() {
            @Override
            public void onResponse(@NonNull Call<Workout> call, @NonNull Response<Workout> response) {
                if(!response.isSuccessful()) {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
                finish();
            }
        });

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
