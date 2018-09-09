package com.workoutplanner.api.interfaces;

import com.workoutplanner.model.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ExercisesAPI {
    @GET("/exercises")
    Call<List<Exercise>> getAllCreatedExercises();

    @POST("/exercises")
    Call<Exercise> createExercise(@Body Exercise exercise);
}
