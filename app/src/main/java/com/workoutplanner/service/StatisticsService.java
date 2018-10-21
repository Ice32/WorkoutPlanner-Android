package com.workoutplanner.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.workoutplanner.MyApplication;
import com.workoutplanner.api.interfaces.StatisticsAPI;
import com.workoutplanner.model.WeekStatistics;
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

    public void getStatistics(ValueConsumer<Map<Long, WeekStatistics>> consumer) {
        Call<Map<Long, WeekStatistics>> workoutsRequest = statisticsAPI.getStatistics();
        workoutsRequest.enqueue(new Callback<Map<Long, WeekStatistics>>() {
            @Override
            public void onResponse(@NonNull Call<Map<Long, WeekStatistics>> call, @NonNull Response<Map<Long, WeekStatistics>> response) {
                if(response.isSuccessful()) {
                    Map<Long, WeekStatistics> workouts = response.body();
                    consumer.consume(workouts);
                } else {
                    Log.e(LOG_TAG, String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<Long, WeekStatistics>> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
