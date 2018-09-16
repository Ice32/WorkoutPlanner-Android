package com.workoutplanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.workoutplanner.api.interfaces.ExercisesAPI;
import com.workoutplanner.model.Exercise;
import com.workoutplanner.service.JwtTokenProvider;
import com.workoutplanner.service.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateNewExerciseActivity extends AppCompatActivity{
    private final String LOG_TAG = this.getClass().getSimpleName();
    private ExercisesAPI exercisesAPI;

    private EditText txtExerciseName;
    private EditText txtNumSets;
    private EditText txtNumReps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_exercise);

        txtExerciseName = findViewById(R.id.txtExerciseName);
        txtNumSets = findViewById(R.id.txtNumSets);
        txtNumReps = findViewById(R.id.txtNumReps);

        exercisesAPI = new ServiceGenerator(new JwtTokenProvider(this)).createService(ExercisesAPI.class);
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
        String name = txtExerciseName.getText().toString();
        Integer sets = Integer.parseInt(txtNumSets.getText().toString());
        Integer reps = Integer.parseInt(txtNumReps.getText().toString());

        Exercise exercise = new Exercise(name, sets, reps);

        Call<Exercise> exerciseRequest = exercisesAPI.createExercise(exercise);
        exerciseRequest.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(@NonNull Call<Exercise> call, @NonNull Response<Exercise> response) {
                if(!response.isSuccessful()) {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Exercise> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new_exercise, menu);

        return true;
    }
}
