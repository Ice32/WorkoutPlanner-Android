package com.workoutplanner.api.interfaces;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatisticsAPI {
    @GET("/statistics")
    Call<Map<Long, Integer>> getStatistics();
}
