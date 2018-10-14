package com.workoutplanner.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.workoutplanner.MyApplication;
import com.workoutplanner.util.ValueConsumer;
import com.workoutplanner.api.interfaces.ExercisesAPI;
import com.workoutplanner.model.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisesService {
    private static final String LOG_TAG = ExercisesService.class.getSimpleName();
    private Context context;
    private ExercisesAPI exercisesAPI;

    public ExercisesService() {
        context = MyApplication.getAppContext();
        exercisesAPI = new ServiceGenerator(new JwtTokenProvider(context)).createService(ExercisesAPI.class);
    }
    public void saveExercise(Exercise exercise, final Runnable callback) {
        Call<Exercise> exerciseRequest = exercisesAPI.createExercise(exercise);
        exerciseRequest.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(@NonNull Call<Exercise> call, @NonNull Response<Exercise> response) {
                if(!response.isSuccessful()) {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
                callback.run();
            }

            @Override
            public void onFailure(@NonNull Call<Exercise> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
    public void findExercises(ValueConsumer<List<Exercise>> callback) {
        Call<List<Exercise>> exercisesRequest = exercisesAPI.getAllCreatedExercises();
        exercisesRequest.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(@NonNull Call<List<Exercise>> call, @NonNull Response<List<Exercise>> response) {
                if(response.isSuccessful()) {
                    callback.consume(response.body());
                } else {
                    if (response.errorBody() != null) {
                        Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Exercise>> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
                callback.consume(null);
            }
        });
    }
}
