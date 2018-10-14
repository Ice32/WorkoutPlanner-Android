package com.workoutplanner.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.workoutplanner.MyApplication;
import com.workoutplanner.util.ValueConsumer;
import com.workoutplanner.api.interfaces.WorkoutsAPI;
import com.workoutplanner.model.Workout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutsService {
    private final String LOG_TAG = WorkoutsService.class.getSimpleName();
    private Context context;
    private WorkoutsAPI workoutsAPI;
    public WorkoutsService() {
        context = MyApplication.getAppContext();
        workoutsAPI = new ServiceGenerator(new JwtTokenProvider(context)).createService(WorkoutsAPI.class);
    }
    public void saveWorkout(Workout w, Runnable callback) {
        Call<Workout> workoutRequest = workoutsAPI.createWorkout(w);
        workoutRequest.enqueue(new Callback<Workout>() {
            @Override
            public void onResponse(@NonNull Call<Workout> call, @NonNull Response<Workout> response) {
                if(!response.isSuccessful()) {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
                callback.run();
            }

            @Override
            public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
                callback.run();
            }
        });
    }
    public void getCreatedWorkouts(ValueConsumer<List<Workout>> consumer) {
        Call<List<Workout>> workoutsRequest = workoutsAPI.getCreatedWorkouts();
        workoutsRequest.enqueue(new Callback<List<Workout>>() {
            @Override
            public void onResponse(@NonNull Call<List<Workout>> call, @NonNull Response<List<Workout>> response) {
                if(response.isSuccessful()) {
                    List<Workout> workouts = response.body();
                    consumer.consume(workouts);
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Workout>> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
