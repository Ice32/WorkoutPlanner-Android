package com.workoutplanner;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    EditText txtWorkoutName;
    Button btnSaveWorkout;

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

        Workout w = new Workout(name);

        workoutsAPI.addWorkout(w);

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

}
