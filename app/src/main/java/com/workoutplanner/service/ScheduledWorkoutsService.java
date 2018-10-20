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
    private Callback<List<ScheduledWorkout>> getScheduledWorkoutsRetrievalCallback(ValueConsumer<List<ScheduledWorkout>> consumer) {
        return new Callback<List<ScheduledWorkout>>() {
            @Override
            public void onResponse(@NonNull Call<List<ScheduledWorkout>> call, @NonNull Response<List<ScheduledWorkout>> response) {
                if(response.isSuccessful()) {
                    List<ScheduledWorkout> workouts = response.body();
                    consumer.consume(workouts);
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ScheduledWorkout>> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        };
    }

    public void getDoneScheduledWorkouts(ValueConsumer<List<ScheduledWorkout>> consumer) {
        Call<List<ScheduledWorkout>> workoutsRequest = workoutsAPI.getDoneScheduledWorkouts();
        workoutsRequest.enqueue(getScheduledWorkoutsRetrievalCallback(consumer));
    }

    public void getUnfinishedScheduledWorkouts(ValueConsumer<List<ScheduledWorkout>> consumer) {
        Call<List<ScheduledWorkout>> workoutsRequest = workoutsAPI.getUnfinishedScheduledWorkouts();
        workoutsRequest.enqueue(getScheduledWorkoutsRetrievalCallback(consumer));
    }

    public void scheduleWorkout(ScheduledWorkout scheduledWorkout, Runnable callback) {
        Call<Void> workoutsRequest = workoutsAPI.scheduleWorkout(scheduledWorkout);

        workoutsRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) {
                    callback.run();
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
    public void finishWorkout(ScheduledWorkout scheduledWorkout, Runnable callback) {
        Call<Void> workoutsRequest = workoutsAPI.finishWorkout(scheduledWorkout.id);

        workoutsRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()) {
                    callback.run();
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
