package com.workoutplanner.view.scheduledWorkouts;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.workoutplanner.R;

import java.util.Arrays;
import java.util.List;

public class ScheduleWorkoutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView txtExactDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduledworkouts_activity_schedule_workout);

        Spinner spinnerWorkoutDay = findViewById(R.id.spinnerWorkoutDay);
        spinnerWorkoutDay.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getWorkoutDayDropdownItems());

        txtExactDate = findViewById(R.id.txtExactDate);

        txtExactDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(ScheduleWorkoutActivity.this, ScheduleWorkoutActivity.this, 2018, 6, 4);
            datePickerDialog.show();
        });

        spinnerWorkoutDay.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule_workout, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btnScheduleWorkoutSave) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    private List<String> getWorkoutDayDropdownItems() {
        String[] days = {
                "every Monday",
                "every Tuesday",
        };

        return Arrays.asList(days);
    }

    private View.OnClickListener backToHome = v -> finish();
}
