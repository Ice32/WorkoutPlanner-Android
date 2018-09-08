package com.workoutplanner.api.interfaces;

import com.workoutplanner.model.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExercisesAPI {
    @GET("/exercises")
    Call<List<Exercise>> getAllCreatedExercises();
}
