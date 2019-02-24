package com.workoutplanner.view.scheduledWorkouts;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.workoutplanner.R;
import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.model.Workout;
import com.workoutplanner.service.ScheduledWorkoutsService;
import com.workoutplanner.view.common.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ScheduleWorkoutActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView txtExactDate;
    TextView txtExactTime;
    Workout selectedWorkout;
    Calendar selectedDate = Calendar.getInstance();
    TextView selectedWorkoutToSchedule;
    Button btnScheduleWorkoutSave;
    private boolean saveInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduledworkouts_schedule_workout_activity);

        Spinner spinnerWorkoutDay = findViewById(R.id.spinnerWorkoutDay);
        spinnerWorkoutDay.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getWorkoutDayDropdownItems());

        txtExactDate = findViewById(R.id.txtExactDate);
        txtExactTime = findViewById(R.id.txtExactTime);
        btnScheduleWorkoutSave = findViewById(R.id.btnScheduleWorkoutSave);
        selectedWorkoutToSchedule = findViewById(R.id.selectedWorkoutToSchedule);

        initDateTimePickers();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedWorkout = (Workout) extras.getSerializable("selected_workout");
            selectedWorkoutToSchedule.setText(selectedWorkout.name);
        }
        spinnerWorkoutDay.setAdapter(adapter);
    }

    private void initDateTimePickers() {
        updateDateFieldValue();
        txtExactDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            ScheduleWorkoutActivity.this,
                            ScheduleWorkoutActivity.this,
                            selectedDate.get(Calendar.YEAR),
                            selectedDate.get(Calendar.MONTH),
                            selectedDate.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        updateTimeFieldValue();
        txtExactTime.setOnClickListener(v -> {
            int hour = selectedDate.get(Calendar.HOUR_OF_DAY);
            int minute = selectedDate.get(Calendar.MINUTE);
            new TimePickerDialog(this, this, hour, minute,
                    DateFormat.is24HourFormat(this)).show();
        });
    }

    private void updateDateFieldValue() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM, yyy", getResources().getConfiguration().locale);
        String dateText = dateFormat.format(selectedDate.getTime());
        txtExactDate.setText(dateText);
    }

    private void updateTimeFieldValue() {
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", getResources().getConfiguration().locale);
        String timeText = timeFormat.format(selectedDate.getTime());
        txtExactTime.setText(timeText);
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
            if (!saveInProgress) {
                saveInProgress = true;
                scheduleWorkout();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void scheduleWorkout() {
        ScheduledWorkout scheduledWorkout = new ScheduledWorkout(
                selectedWorkout,
                selectedDate.getTime()
        );
        if (selectedDate.before(Calendar.getInstance())) {
            Snackbar.make(findViewById(R.id.scheduled_workouts_constraint_layout), "Please select a future date", Snackbar.LENGTH_SHORT).show();
            saveInProgress = false;
            return;
        }
        new ScheduledWorkoutsService().scheduleWorkout(scheduledWorkout, () -> startActivity(new Intent(getApplicationContext(), HomeActivity.class)));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedDate.set(year, month, dayOfMonth);
        updateDateFieldValue();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selectedDate.set(Calendar.MINUTE, minute);
        updateTimeFieldValue();
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
