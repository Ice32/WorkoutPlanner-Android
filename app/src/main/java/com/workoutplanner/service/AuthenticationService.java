package com.workoutplanner.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.workoutplanner.MyApplication;
import com.workoutplanner.R;
import com.workoutplanner.api.ApiErrors;
import com.workoutplanner.api.ApiResponse;
import com.workoutplanner.api.LoginSubmissionData;
import com.workoutplanner.api.interfaces.UsersAPI;
import com.workoutplanner.model.User;
import com.workoutplanner.util.ValueConsumer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {
    private static final String LOG_TAG = AuthenticationService.class.getSimpleName();
    private Context context;
    private UsersAPI usersAPI;
    private String JWT_TOKEN_KEY;
    private String REFRESH_TOKEN_KEY;

    public AuthenticationService() {
        this.context = MyApplication.getAppContext();
        usersAPI = new ServiceGenerator(new JwtTokenProvider(context)).createService(UsersAPI.class);
        JWT_TOKEN_KEY = context.getString(R.string.jwt_token);
        REFRESH_TOKEN_KEY = context.getString(R.string.refresh_token);
    }

    private void storeTokens(String jwtToken, String refreshToken) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(JWT_TOKEN_KEY, jwtToken);
        editor.putString(REFRESH_TOKEN_KEY, refreshToken);
        editor.commit();
    }
    public void login(LoginSubmissionData loginData, ValueConsumer<Integer> callback) {
        Call<Void> loginRequest = usersAPI.loginUser(loginData);
        AuthenticationService authenticationService = this;
        loginRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if(response.isSuccessful()) {
                    Headers headers = response.headers();
                    authenticationService.storeTokens(headers.get("Authorization"), headers.get("RefreshToken"));
                    callback.consume(200);
                } else {
                    callback.consume(401);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                callback.consume(500);
            }
        });
    }
    public String getAuthToken() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(JWT_TOKEN_KEY, null);
    }
    public void registerUser(User user, ValueConsumer<String> callback) {
        Call<ApiResponse<Void>> registrationRequest = usersAPI.registerUser(user);
        AuthenticationService authenticationService = this;
        registrationRequest.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if(response.isSuccessful()) {
                    Call<Void> loginRequest = usersAPI.loginUser(new LoginSubmissionData(user.email, user.password));
                    loginRequest.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> loginResponse) {
                            authenticationService.storeTokens(
                                    loginResponse.headers().get("Authorization"),
                                    response.headers().get("RefreshToken")
                            );
                            callback.consume(null);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e(LOG_TAG, t.getMessage());
                            callback.consume("Unknown error");
                        }
                    });
                } else {
                    System.out.println(response.errorBody());
                    Gson gson = new Gson();
                    String responseDataString;
                    try {
                        responseDataString = response.errorBody().string();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, e.getMessage());
                        callback.consume("Unknown error");
                        return;
                    }
                    ApiResponse<Void> responseData = gson.fromJson(responseDataString, ApiResponse.class);
                    if (responseData.getError().equals(ApiErrors.DUPLICATE_EMAIL)) {
                        callback.consume("Email already in use");
                        return;
                    }
                    callback.consume("Unknown error");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
                callback.consume("Unknown error");
            }
        });
    }
    public void logout() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(JWT_TOKEN_KEY);
        editor.remove(REFRESH_TOKEN_KEY);
        editor.commit();
    }
}
