package com.workoutplanner.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.workoutplanner.MyApplication;
import com.workoutplanner.util.ValueConsumer;
import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.ScheduledWorkout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduledWorkoutsService {
    private static final String LOG_TAG = ScheduledWorkoutsService.class.getSimpleName();
    private Context context;
    private WorkoutsAPI workoutsAPI;

    public ScheduledWorkoutsService() {
        context = MyApplication.getAppContext();
        workoutsAPI = new ServiceGenerator(new JwtTokenProvider(context)).createService(WorkoutsAPI.class);
    }

    public void getSheduledWorkouts(ValueConsumer<List<ScheduledWorkout>> callback) {
        Call<List<ScheduledWorkout>> workoutsRequest = workoutsAPI.getScheduledWorkouts();
        workoutsRequest.enqueue(new Callback<List<ScheduledWorkout>>() {
            @Override
            public void onResponse(@NonNull Call<List<ScheduledWorkout>> call, @NonNull Response<List<ScheduledWorkout>> response) {
                if(response.isSuccessful()) {
                    List<ScheduledWorkout> workouts = response.body();
                    callback.consume(workouts);
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ScheduledWorkout>> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public void getDoneScheduledWorkouts(ValueConsumer<List<ScheduledWorkout>> consumer) {
        Call<List<ScheduledWorkout>> workoutsRequest = workoutsAPI.getDoneScheduledWorkouts();
        workoutsRequest.enqueue(new Callback<List<ScheduledWorkout>>() {
            @Override
            public void onResponse(@NonNull Call<List<ScheduledWorkout>> call, @NonNull Response<List<ScheduledWorkout>> response) {
                if(response.isSuccessful()) {
                    List<ScheduledWorkout> workouts = response.body();
                    consumer.consume(workouts);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ScheduledWorkout>> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
