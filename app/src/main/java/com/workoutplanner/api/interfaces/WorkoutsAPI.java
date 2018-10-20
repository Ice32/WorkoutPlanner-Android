package com.workoutplanner.api.interfaces;

import com.workoutplanner.model.ScheduledWorkout;
import com.workoutplanner.model.Workout;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WorkoutsAPI {
    @GET("/workouts")
    Call<List<Workout>> getCreatedWorkouts();

    @POST("/workouts")
    Call<Workout> createWorkout(@Body Workout workout);

    @GET("/scheduled_workouts")
    Call<List<ScheduledWorkout>> getScheduledWorkouts();

    @POST("/scheduled_workouts")
    Call<Void> scheduleWorkout(@Body ScheduledWorkout scheduledWorkout);

    @POST("/scheduled_workouts/{id}/finish")
    Call<Void> finishWorkout(@Path("id") Long id);

    @GET("/scheduled_workouts/done")
    Call<List<ScheduledWorkout>> getDoneScheduledWorkouts();

    @GET("/scheduled_workouts/unfinished")
    Call<List<ScheduledWorkout>> getUnfinishedScheduledWorkouts();
}
