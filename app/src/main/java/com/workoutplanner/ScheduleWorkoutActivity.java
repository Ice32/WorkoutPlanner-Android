package com.workoutplanner;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ScheduleWorkoutActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button btnFinishScheduleExact;
    Button btnFinishScheduleRepeating;
    TextView txtExactDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_workout);

        Spinner spinnerWorkoutDay = findViewById(R.id.spinnerWorkoutDay);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getWorkoutDayDropdownItems());

        btnFinishScheduleExact = findViewById(R.id.btnFinishScheduleExact);
        btnFinishScheduleRepeating = findViewById(R.id.btnFinishScheduleRepeating);
        txtExactDate = findViewById(R.id.txtExactDate);

        btnFinishScheduleExact.setOnClickListener(backToHome);
        btnFinishScheduleRepeating.setOnClickListener(backToHome);
        txtExactDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(ScheduleWorkoutActivity.this, ScheduleWorkoutActivity.this, 2018, 6, 4);
                datePickerDialog.show();
            }
        });

        spinnerWorkoutDay.setAdapter(adapter);
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

    private View.OnClickListener backToHome = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
