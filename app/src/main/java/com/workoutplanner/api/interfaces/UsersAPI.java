package com.workoutplanner.api.interfaces;

import com.workoutplanner.api.ApiResponse;
import com.workoutplanner.api.LoginSubmissionData;
import com.workoutplanner.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsersAPI {
    @POST("/users/registration")
    Call<ApiResponse<Void>> registerUser(@Body User user);

    @POST("/users/login")
    Call<Void> loginUser(@Body LoginSubmissionData user);
}
