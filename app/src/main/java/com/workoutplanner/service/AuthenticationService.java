package com.workoutplanner.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.workoutplanner.MyApplication;
import com.workoutplanner.api.ApiErrors;
import com.workoutplanner.R;
import com.workoutplanner.api.ApiResponse;
import com.workoutplanner.api.LoginSubmissionData;
import com.workoutplanner.api.interfaces.UsersAPI;
import com.workoutplanner.model.User;

import java.io.IOException;

import okhttp3.Headers;
import retrofit2.Call;
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
    public boolean login(LoginSubmissionData loginData) {
        Call<Void> loginRequest = usersAPI.loginUser(loginData);
        try {
            Response<Void> response = loginRequest.execute();
            if(response.isSuccessful()) {
                Headers headers = response.headers();
                this.storeTokens(headers.get("Authorization"), headers.get("RefreshToken"));
                return true;
            } else {
                System.out.println(response.errorBody());
                return false;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return false;
        }
    }
    public String getAuthToken() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(JWT_TOKEN_KEY, null);
    }
    public String registerUser(User user) {
        Call<ApiResponse<Void>> registrationRequest = usersAPI.registerUser(user);
        try {
            Response<ApiResponse<Void>> response = registrationRequest.execute();
            if(response.isSuccessful()) {
                Call<Void> loginRequest = usersAPI.loginUser(new LoginSubmissionData(user.email, user.password));
                Response<Void> loginResponse = loginRequest.execute();
                this.storeTokens(
                        loginResponse.headers().get("Authorization"),
                        response.headers().get("RefreshToken")
                );
                return null;
            } else {
                System.out.println(response.errorBody());
                Gson gson = new Gson();
                String responseDataString = response.errorBody().string();
                ApiResponse<Void> responseData = gson.fromJson(responseDataString, ApiResponse.class);
                if (responseData.getError().equals(ApiErrors.DUPLICATE_EMAIL)) {
                    return "Email already in use";
                }
                return "Unknown error";
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
            return "Unknown error";
        }
    }
    public void logout() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(JWT_TOKEN_KEY);
        editor.remove(REFRESH_TOKEN_KEY);
        editor.commit();
    }
}
