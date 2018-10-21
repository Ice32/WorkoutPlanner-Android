package com.workoutplanner.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.workoutplanner.MyApplication;
import com.workoutplanner.api.interfaces.StatisticsAPI;
import com.workoutplanner.util.ValueConsumer;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsService {
    private final String LOG_TAG = StatisticsService.class.getSimpleName();
    private Context context;
    private StatisticsAPI statisticsAPI;

    public StatisticsService() {
        context = MyApplication.getAppContext();
        statisticsAPI = new ServiceGenerator(new JwtTokenProvider(context)).createService(StatisticsAPI.class);
    }

    public void getStatistics(ValueConsumer<Map<Long, Integer>> consumer) {
        Call<Map<Long, Integer>> workoutsRequest = statisticsAPI.getStatistics();
        workoutsRequest.enqueue(new Callback<Map<Long, Integer>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Long, Integer>> call, @NonNull Response<Map<Long, Integer>> response) {
                if(response.isSuccessful()) {
                    Map<Long, Integer> workouts = response.body();
                    consumer.consume(workouts);
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<Long, Integer>> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
