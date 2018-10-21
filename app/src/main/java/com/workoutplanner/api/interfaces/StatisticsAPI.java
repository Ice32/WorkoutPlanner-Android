package com.workoutplanner.api.interfaces;

import com.workoutplanner.model.WeekStatistics;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatisticsAPI {
    @GET("/statistics")
    Call<Map<Long, WeekStatistics>> getStatistics();
}
